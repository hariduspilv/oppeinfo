package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.jxls.common.AreaListener;
import org.jxls.common.CellRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.report.studyyearschedule.ReportDepartment;
import ee.hitsa.ois.report.studyyearschedule.ReportStudentGroup;
import ee.hitsa.ois.report.studyyearschedule.ReportStudyPeriod;
import ee.hitsa.ois.report.studyyearschedule.ReportTable;
import ee.hitsa.ois.report.studyyearschedule.StudyYearScheduleReport;
import ee.hitsa.ois.repository.StudyYearScheduleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleCommand;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleForm;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.StudyPeriodEventDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;
import ee.hitsa.ois.web.dto.report.WeekStartAndEndDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.xls.AbstractColorAreaListener;

@Service
@Transactional
public class StudyYearScheduleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearScheduleRepository studyYearScheduleRepository;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private PdfService pdfService;
    
    private static final int PDF_MAX_WEEKS = 16;

    public Set<StudyYearScheduleDto> getSet(HoisUserDetails user, StudyYearScheduleDtoContainer schedulesCmd) {
        JpaQueryBuilder<StudyYearSchedule> qb = new JpaQueryBuilder<>(StudyYearSchedule.class, "sys");

        qb.requiredCriteria("sys.school.id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("sys.studyPeriod.id in (:studyPeriodIds)", "studyPeriodIds", schedulesCmd.getStudyPeriods());
        if (Boolean.TRUE.equals(schedulesCmd.getShowMine())) {
            Long studentId = user.getStudentId();
            if (studentId != null) {
                Student student = em.getReference(Student.class, studentId);
                StudentGroup studentGroup = student.getStudentGroup();
                if (studentGroup != null) {
                    qb.requiredCriteria("sys.studentGroup.id = :studentGroupId", "studentGroupId", EntityUtil.getId(studentGroup));
                }
            }
        } else {
            qb.optionalCriteria("sys.studentGroup.id in (:studentGroupIds)", "studentGroupIds", schedulesCmd.getStudentGroups());
        }

        return StreamUtil.toMappedSet(StudyYearScheduleDto::of, qb.select(em).getResultList());
    }
    
    public void update(Long schoolId, StudyYearScheduleCommand scheduleCmd, StudyYearSchedule oldSchedule) {
        StudyYearScheduleDto dto = scheduleCmd.getStudyYearSchedule();
        AssertionFailedException.throwIf(!CollectionUtils.isEmpty(scheduleCmd.getStudentGroups()) &&
                !scheduleCmd.getStudentGroups().contains(dto.getStudentGroup()),
                "Update command does not contain dto's studentGroup!");
        AssertionFailedException.throwIf(!scheduleCmd.getStudyPeriods().contains(dto.getStudyPeriod()),
                "Update command does not contain dto's studyPeriod!");
        oldSchedule = bindFieldsFromDto(dto, em.getReference(School.class, schoolId), oldSchedule);
        studyYearScheduleRepository.save(oldSchedule);
    }
    
    public void delete(StudyYearSchedule schedule) {
        studyYearScheduleRepository.delete(schedule);
    }

    private StudyYearSchedule bindFieldsFromDto(StudyYearScheduleDto dto, School school, StudyYearSchedule schedule) {
        if (schedule == null) {
            schedule = new StudyYearSchedule();
        }
        schedule.setSchool(school);

        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, dto.getStudyPeriod());
        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(school.getId()),
        "Wrong studyPeriod's school!");
        schedule.setStudyPeriod(studyPeriod);

        StudentGroup sg = em.getReference(StudentGroup.class, dto.getStudentGroup());
        AssertionFailedException.throwIf(!EntityUtil.getId(sg.getSchool()).equals(school.getId()),
        "Wrong studentGroups's school!");
        schedule.setStudentGroup(sg);

        StudyYearScheduleLegend legend = em.getReference(StudyYearScheduleLegend.class, dto.getStudyYearScheduleLegend());
        AssertionFailedException.throwIf(!EntityUtil.getId(legend.getSchool()).equals(school.getId()),
        "Wrong legend's school!");
        schedule.setStudyYearScheduleLegend(legend);

        schedule.setWeekNr(dto.getWeekNr());
        schedule.setAddInfo(dto.getAddInfo());
        return schedule;
    }

    public List<StudentGroupSearchDto> getStudentGroups(HoisUserDetails user, Boolean showMine) {
        return getStudentGroups(user, showMine, null, null);
    }

    public List<StudentGroupSearchDto> getStudentGroups(HoisUserDetails user, Boolean showMine, LocalDate from,
            LocalDate thru) {
        List<StudentGroup> data;
        if (Boolean.TRUE.equals(showMine) && (user.isStudent() || user.isRepresentative())) {
            data = em.createQuery("select s.studentGroup from Student s where s.id = ?1", StudentGroup.class)
                    .setParameter(1, user.getStudentId()).getResultList();
        } else {
            JpaQueryBuilder<StudentGroup> qb = new JpaQueryBuilder<>(StudentGroup.class, "sg").sort("code");
            qb.requiredCriteria("sg.school.id = :schoolId", "schoolId", user.getSchoolId());
            if (Boolean.TRUE.equals(showMine) && user.isLeadingTeacher()) {
                qb.requiredCriteria("sg.curriculum.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
            }
            qb.optionalCriteria("(sg.validFrom is null or sg.validFrom <= :thru)", "thru", thru);
            qb.optionalCriteria("(sg.validThru is null or sg.validThru >= :from)", "from", from);
            data = qb.select(em).getResultList();
        }

        return StreamUtil.toMappedList(sg -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(sg.getId());
            dto.setCode(sg.getCode());
            dto.setValidFrom(sg.getValidFrom());
            dto.setValidThru(sg.getValidThru());
            dto.setSchoolDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()),
                    sg.getCurriculum().getDepartments()));
            dto.setCanEdit(Boolean.valueOf(UserUtil.isSchoolAdminOrLeadingTeacher(user, sg)));
            return dto;
        }, data.stream().filter(sg -> sg.getCurriculum() != null && !sg.getCurriculum().getDepartments().isEmpty()));
    }

    public List<StudyYearDto> getStudyYearsWithStudyPeriods(Long schoolId) {
        List<StudyYear> data = em.createQuery("select sy from StudyYear sy where sy.school.id = ?1"
                + " order by sy.endDate", StudyYear.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(sy -> {
            StudyYearDto dto = StudyYearDto.of(sy);
            dto.getStudyPeriodEvents().clear();
            return dto;
        }, data);
    }
    
    public Map<Long, List<StudyPeriodEventDto>> getStudyYearPeriodEventsMappedToPeriod(StudyYear studyYear) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_period_event spe");
        qb.requiredCriteria("spe.study_year_id = :studyYearId", "studyYearId", EntityUtil.getId(studyYear));
        qb.requiredCriteria("spe.event_type_code = :eventTypeCode", "eventTypeCode", StudyPeriodEventType.SYNDMUS_VAHA.name());
        List<?> data = qb.select("spe.study_period_id, spe.start, spe.end", em).getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0) == null ? Long.valueOf(-1L) : resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    StudyPeriodEventDto dto = new StudyPeriodEventDto();
                    dto.setStart(resultAsLocalDateTime(r, 1));
                    dto.setEnd(resultAsLocalDateTime(r, 2));
                    return dto;
                }, Collectors.toList())));
    }

    public List<StudyPeriodWithWeeksDto> getStudyYearPeriods(StudyYear studyYear) {
        Map<Long, List<StudyPeriodEventDto>> studyYearPeriodEvents = getStudyYearPeriodEventsMappedToPeriod(studyYear);
        List<StudyPeriodWithWeeksDto> studyPeriods = studyYear.getStudyPeriods().stream()
                .sorted(Comparator.comparing(StudyPeriod::getStartDate))
                .map(p -> {
                    StudyPeriodWithWeeksDto dto = new StudyPeriodWithWeeksDto(p);
                    List<StudyPeriodEventDto> vacations = new ArrayList<>();
                    List<StudyPeriodEventDto> vacationsWithoutPeriod = new ArrayList<>();
                    if (studyYearPeriodEvents.containsKey(EntityUtil.getId(p))) {
                        vacations = studyYearPeriodEvents.get(EntityUtil.getId(p));
                    }
                    if (studyYearPeriodEvents.containsKey(Long.valueOf(-1))) {
                        vacationsWithoutPeriod = studyYearPeriodEvents.get(Long.valueOf(-1));
                    }
                    dto.setVacations(Stream.concat(vacations.stream(), vacationsWithoutPeriod.stream())
                            .collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
        addExternalWeeksToPeriod(studyPeriods);
        return studyPeriods;
    }

    // add weeks so that study period weeks can go from 1 to last period's week
    public void addExternalWeeksToPeriod(List<StudyPeriodWithWeeksDto> studyPeriods) {
        for (int i = 0; i < studyPeriods.size(); i++) {
            StudyPeriodWithWeeksDto period = studyPeriods.get(i);
            StudyPeriodWithWeeksDto previousPeriod = i > 0 ? studyPeriods.get(i - 1) : null;

            short periodFirstWeek = period.getWeekNrs().get(0);
            short previousPeriodLastWeek = previousPeriod != null
                    ? previousPeriod.getWeekNrs().get(previousPeriod.getWeekNrs().size() - 1) : 0;
            for (int weekNr = periodFirstWeek - 1; weekNr > previousPeriodLastWeek; weekNr--) {
                Short newWeekNr = Short.valueOf((short) weekNr);
                period.getWeekNrs().add(0, newWeekNr);
                period.getExternalWeeks().add(newWeekNr);

                LocalDate nextWeekStart = period.getWeekBeginningDates().get(0);
                period.getWeekBeginningDates().add(0, nextWeekStart.minusDays(7));
            }
        }
    }

    private List<ReportStudyPeriod> getReportStudyPeriods(StudyYear studyYear) {
        List<StudyPeriodWithWeeksDto> studyPeriods = getStudyYearPeriods(studyYear);
        return StreamUtil.toMappedList(sp -> {
            ReportStudyPeriod studyPeriodData = new ReportStudyPeriod();
            studyPeriodData.setPeriod(sp);
            List<Short> weekNrs = sp.getWeekNrs();
            studyPeriodData.setWeeks(weekNrs);
            studyPeriodData.setStartWeek(weekNrs.get(0));
            List<LocalDate> weekBeginDates = sp.getWeekBeginningDates();
            for (LocalDate beginDate : weekBeginDates) {
                WeekStartAndEndDto dto = new WeekStartAndEndDto();
                dto.setStartDate(beginDate);
                dto.setEndDate(beginDate.plusDays(6));
                dto.setWeekNr(weekNrs.get(weekBeginDates.indexOf(beginDate)));
                if (sp.getVacations().stream().anyMatch(p -> p.getStart().isBefore(dto.getEndDate().atTime(LocalTime.MAX)) 
                        && (p.getEnd() == null || p.getEnd().isAfter(dto.getStartDate().atStartOfDay())))) {
                    dto.setVacation(Boolean.TRUE);
                }
                studyPeriodData.getWeekDates().add(dto);
            }
            studyPeriodData.setEndWeek(weekNrs.get(weekNrs.size() - 1));
            return studyPeriodData;
        }, studyPeriods);
    }

    private StudyYearScheduleReport getReport(HoisUserDetails user, StudyYearScheduleForm schedulesCmd) {
        School school = em.getReference(School.class, user.getSchoolId());
        StudyYear studyYear = em.getReference(StudyYear.class, schedulesCmd.getStudyYearId());
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        StudyYearScheduleReport report = new StudyYearScheduleReport();
        List<StudyYearScheduleLegendDto> legendList = StreamUtil.toMappedList(StudyYearScheduleLegendDto::of, 
                school.getStudyYearScheduleLegends());
        Map<Long, StudyYearScheduleLegendDto> legendMap = StreamUtil.toMap(StudyYearScheduleLegendDto::getId, legendList);
        report.setLegends(legendList);
        ReportTable table = new ReportTable();
        List<ReportStudyPeriod> studyPeriods = getReportStudyPeriods(studyYear);
        table.setStudyPeriods(studyPeriods);
        List<Short> weeks = new LinkedList<>();
        for (ReportStudyPeriod period : studyPeriods) {
            weeks.addAll(period.getWeeks());
        }
        table.setStudyYear(studyYear.getYear().getNameEt());
        table.setWeeks(weeks);
        List<SchoolDepartmentResult> departmentList = StreamUtil.toFilteredList(d -> schedulesCmd.getSchoolDepartments().contains(d.getId()),
                autocompleteService.schoolDepartments(user.getSchoolId()));
        List<StudentGroupSearchDto> studentGroups = getStudentGroups(user, schedulesCmd.getShowMine(),
                studyYear.getStartDate(), studyYear.getEndDate());
        StudyYearScheduleDtoContainer schedulesContainer = new StudyYearScheduleDtoContainer();
        schedulesContainer.setStudyPeriods(StreamUtil.toMappedSet(sp -> sp.getPeriod().getId(), studyPeriods));
        schedulesContainer.setStudentGroups(StreamUtil.toMappedSet(StudentGroupSearchDto::getId, studentGroups));
        schedulesContainer.setShowMine(schedulesCmd.getShowMine());
        Set<StudyYearScheduleDto> schedule = getSet(user, schedulesContainer);
        table.setDepartments(StreamUtil.toMappedList(department -> {
            ReportDepartment departmentData = new ReportDepartment();
            departmentData.setDepartment(department);
            departmentData.setStudentGroups(StreamUtil.toMappedList(studentGroup -> {
                ReportStudentGroup groupData = new ReportStudentGroup();
                groupData.setGroup(studentGroup);
                groupData.setSchedule(StreamUtil.toMappedList(
                        week ->  {
                            StudyYearScheduleLegendDto legend = schedule.stream().filter(s -> s.getStudentGroup().equals(studentGroup.getId())
                                    && s.getWeekNr().intValue() == week.intValue())
                            .map(s -> {
                                StudyYearScheduleLegendDto dto = StudyYearScheduleLegendDto.of(legendMap.get(s.getStudyYearScheduleLegend()));
                                dto.setAddInfo(s.getAddInfo());
                                return dto;
                            })
                            .findAny().orElse(null);
                            if (legend == null) {
                                // set vacation
                                if (studyPeriods.stream().flatMap(p -> p.getWeekDates().stream())
                                        .anyMatch(p -> p.getWeekNr().intValue() == week.intValue() && Boolean.TRUE.equals(p.getVacation()))) {
                                    StudyYearScheduleLegendDto dto = new StudyYearScheduleLegendDto();
                                    //gray
                                    dto.setColor("#EEEEEE");
                                    return dto;
                                }
                            }
                            return legend;
                        }, weeks));
                return groupData;
            }, studentGroups.stream().filter(sg -> sg.getSchoolDepartments().contains(department.getId()))));
            return departmentData;
        }, departmentList));
        report.setTables(Collections.singletonList(table));
        return report;
    }
    
    public byte[] studyYearScheduleAsExcel(HoisUserDetails user, StudyYearScheduleForm schedulesCmd) {
        StudyYearScheduleReport report = getReport(user, schedulesCmd);
        ReportTable table = report.getTables().get(0);
        Map<String, Object> data = new HashMap<>();
        data.put("legends", report.getLegends());
        table.getStudyPeriods().forEach(p -> {
            if (!p.getWeeks().isEmpty()) {
                p.getWeeks().remove(0);
            }
        });
        data.put("studyYear", table.getStudyYear());
        data.put("studyPeriods", table.getStudyPeriods());
        data.put("weeks", table.getWeeks());
        Collections.sort(table.getDepartments(), Comparator.comparing(p -> p.getDepartment().getNameEt(), String.CASE_INSENSITIVE_ORDER));
        data.put("departments", table.getDepartments());
        List<SimpleEntry<String, AreaListener>> listeners = new ArrayList<>();
        AbstractColorAreaListener<WeekStartAndEndDto> vacationColorListener = new AbstractColorAreaListener<WeekStartAndEndDto>("weekDate") {
            
            private Font blackFont;

            @Override
            protected Color getColor(WeekStartAndEndDto dto) {
                return Boolean.TRUE.equals(dto.getVacation()) ? Color.decode("#EEEEEE") : Color.WHITE;
            }

            @Override
            protected Font getFont(WeekStartAndEndDto dto) {
                if (blackFont == null) {
                    blackFont = getWorkbook().createFont();
                    blackFont.setColor(IndexedColors.BLACK.getIndex());
                    blackFont.setFontHeightInPoints((short) 8);
                }
                return blackFont;
            }

            @Override
            protected boolean styleLocked(CellRef srcCell) {
                return false;
            }

            @Override
            protected String getComment(WeekStartAndEndDto object) {
                return null;
            }
        };
        listeners.add(new SimpleEntry<>("Sheet1!B9:B9", vacationColorListener));
        listeners.add(new SimpleEntry<>("Sheet1!B10:B10", vacationColorListener));
        listeners.add(new SimpleEntry<>("Sheet1!B11:B11", vacationColorListener));
        listeners.add(new SimpleEntry<>("Sheet1!B13:B13", getAbstractColorAreaListener("schedule")));
        listeners.add(new SimpleEntry<>("Sheet1!A4:D4", getAbstractColorAreaListener("item")));
        return xlsService.generate("studyyearschedule.xlsx", data, listeners);
    }

    private static AbstractColorAreaListener<StudyYearScheduleLegendDto> getAbstractColorAreaListener(String excelVariable) {
        return new AbstractColorAreaListener<StudyYearScheduleLegendDto>(excelVariable) {
            
            private Font blackFont;
            private Font whiteFont;

            @Override
            protected Color getColor(StudyYearScheduleLegendDto dto) {
                if (dto == null) {
                    return Color.WHITE;
                }
                return Color.decode(dto.getColor());
            }

            @Override
            protected Font getFont(StudyYearScheduleLegendDto dto) {
                if (blackFont == null) {
                    blackFont = getWorkbook().createFont();
                    blackFont.setColor(IndexedColors.BLACK.getIndex());
                    blackFont.setFontHeightInPoints((short) 8);
                }
                if (whiteFont == null) {
                    whiteFont = getWorkbook().createFont();
                    whiteFont.setColor(IndexedColors.WHITE.getIndex());
                    whiteFont.setFontHeightInPoints((short) 8);
                }
                if (dto != null && Boolean.TRUE.equals(dto.getBrightText())) {
                    return whiteFont;
                }
                return blackFont;
            }

            @Override
            protected boolean styleLocked(CellRef srcCell) {
                if ("item".equals(excelVariable)) {
                    return srcCell.getCol() != 3;
                }
                return false;
            }

            @Override
            protected String getComment(StudyYearScheduleLegendDto object) {
                if (!"item".equals(excelVariable)) {
                    return object.getAddInfo();
                }
                return null;
            }
        };
    }

    /**
     * Splits table by PDF_MAX_WEEKS weeks for A4 PDF format
     * Weeks are in the ascending order inside of lists
     * 
     * @param user
     * @param schedulesCmd
     * @return bytes of file
     */
    public byte[] studyYearScheduleAsPdf(HoisUserDetails user, StudyYearScheduleForm schedulesCmd) {
        StudyYearScheduleReport report = getReport(user, schedulesCmd);
        ReportTable table = report.getTables().get(0);
        // Map periods by weeks. Fill empty weeks by next period
        Map<Integer, ReportStudyPeriod> studyPeriodsByWeek = new HashMap<>();
        table.getStudyPeriods().forEach(p -> {
            p.getWeeks().forEach(w -> {
                Integer it = Integer.valueOf(w.intValue());
                studyPeriodsByWeek.put(it, p);
                it = Integer.valueOf(it.intValue() - 1);
                while (it.intValue() > 0 && !studyPeriodsByWeek.containsKey(it)) {
                    studyPeriodsByWeek.put(it, p);
                    it = Integer.valueOf(it.intValue() - 1);
                }
            });
        });
        List<Short> weeks = table.getWeeks();
        LinkedList<ReportTable> tables = new LinkedList<>();
        for (int i = 0; i < weeks.size(); ) {
            int next = Math.min(i + PDF_MAX_WEEKS, weeks.size() - 1); // next index
            final int week = weeks.get(i).intValue(); // start week
            final int nextWeek = weeks.get(next).intValue(); // end week
            boolean nextRow = next % PDF_MAX_WEEKS == 0 && i != next; // is end week including or excluding
            if (weeks.isEmpty()) { // break when empty
                break;
            }
            ReportTable temp = new ReportTable();
            temp.setWeeks(weeks.subList(i, nextRow ? next : next + 1));
            // holds pointers of the real period and copied period (difference in weeks) in current table
            Map<ReportStudyPeriod, ReportStudyPeriod> iteratedPeriods = new HashMap<>();
            temp.setStudyPeriods(new ArrayList<>());
            for (int w = week; w < nextWeek || (!nextRow && w == nextWeek); w++) {
                if (studyPeriodsByWeek.containsKey(Integer.valueOf(w))) {
                    ReportStudyPeriod p = studyPeriodsByWeek.get(Integer.valueOf(w));
                    if (!iteratedPeriods.containsKey(p)) {
                        ReportStudyPeriod tempPeriod = new ReportStudyPeriod();
                        tempPeriod.setPeriod(p.getPeriod());
                        tempPeriod.setWeeks(new ArrayList<>());
                        iteratedPeriods.put(p, tempPeriod);
                        temp.getStudyPeriods().add(tempPeriod);
                    }
                    iteratedPeriods.get(p).getWeeks().add(Short.valueOf((short) w));
                }
            }
            temp.setDepartments(splitDepartments(table, i, nextRow ? next : next + 1));
            tables.add(temp);
            if (!nextRow) { // next row does not exist, should stop
                break;
            }
            i = next;
        }
        report.setTables(tables);
        return pdfService.generate("studyyearschedule.xhtml", report);
    }

    private static List<ReportDepartment> splitDepartments(ReportTable table, int fromIndex, int toIndex) {
        return StreamUtil.toMappedList(sourceDepartment -> {
            ReportDepartment splittedDepartment = new ReportDepartment();
            splittedDepartment.setDepartment(sourceDepartment.getDepartment());
            splittedDepartment.setStudentGroups(StreamUtil.toMappedList(studentGroup -> {
                ReportStudentGroup splittedGroup = new ReportStudentGroup();
                splittedGroup.setGroup(studentGroup.getGroup());
                splittedGroup.setSchedule(studentGroup.getSchedule().subList(fromIndex, toIndex));
                return splittedGroup;
            }, sourceDepartment.getStudentGroups()));
            return splittedDepartment;
        }, table.getDepartments());
    }
    
}
