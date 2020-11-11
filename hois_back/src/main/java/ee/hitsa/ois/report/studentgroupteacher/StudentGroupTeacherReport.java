package ee.hitsa.ois.report.studentgroupteacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleTypeDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentProgressDto;

public class StudentGroupTeacherReport {

    public static final String TEMPLATE_NAME = "student.group.teacher.xhtml";
    private static final int TABLE_SIZE = 8;
    private static final int ABSENCE_COLUMNS = 6;
    private static final int PROGRESS_COLUMNS = 3;

    private List<StudentGroupTeacherReportTable> tables;
    private List<StudentGroupTeacherReportJournal> journals;
    private BigDecimal averageGrade;
    private Boolean showAverageGrade;
    private Boolean showWeightedAverageGrade;
    private Boolean showStudentProgress;
    private Boolean showTotalColumns;

    public StudentGroupTeacherReport(StudentGroupTeacherCommand criteria, StudentGroupTeacherDto dto,
            ClassifierCache classifierCache, Map<Long, GradingSchemaRow> gradingSchemaRowMap) {
        this(criteria, dto, classifierCache, gradingSchemaRowMap, Language.ET);
    }

    public StudentGroupTeacherReport(StudentGroupTeacherCommand criteria, StudentGroupTeacherDto dto,
            ClassifierCache classifierCache, Map<Long, GradingSchemaRow> gradingSchemaRowMap, Language lang) {
        this.averageGrade = dto.getAverageGrade();
        this.showAverageGrade = criteria.getAverageGrade();
        this.showWeightedAverageGrade = criteria.getWeightedAverageGrade();
        this.showStudentProgress = dto.getShowStudentProgress();
        this.showTotalColumns = Boolean.FALSE.equals(dto.getShowStudentProgress())
                && Boolean.FALSE.equals(dto.getShowModuleResultTable());

        List<String> resultColumns = StreamUtil
                .toMappedList(rc -> ReportUtil.resultColumnAsString(rc, Boolean.FALSE, lang), dto.getResultColumns());

        Map<Long, List<String>> studentResultColumns = new HashMap<>();
        for (StudentDto student : dto.getStudents()) {
            List<String> studentColumns = StreamUtil.toMappedList(rc -> ReportUtil
                    .studentResultColumnAsString(criteria.getAbsencesPerJournals(), rc, classifierCache,
                            gradingSchemaRowMap, lang), student.getResultColumns());
            studentResultColumns.put(student.getId(), studentColumns);
        }

        setTables(dto, resultColumns, studentResultColumns);

        List<StudentGroupTeacherReportJournal> reportJournals = new ArrayList<>();
        for (ModuleTypeDto t : dto.getModuleTypes()) {
            String type = ReportUtil.classifierName(t.getCode(), MainClassCode.KUTSEMOODUL.name(), classifierCache,
                    lang);
            for (ModuleDto m : t.getModules()) {
                String module = TranslateUtil.name(m, lang);
                for (AutocompleteResult j : m.getJournals()) {
                    StudentGroupTeacherReportJournal journal = new StudentGroupTeacherReportJournal();
                    journal.setModuleType(type);
                    journal.setModule(module);
                    journal.setName(TranslateUtil.name(j, lang));
                    reportJournals.add(journal);
                }
            }
        }
        this.journals = reportJournals;
    }

    private void setTables(StudentGroupTeacherDto dto, List<String> resultColumns,
            Map<Long, List<String>> studentResultColumns) {
        int totalColumns = 0;
        if (this.showTotalColumns) {
            totalColumns = ABSENCE_COLUMNS + (Boolean.TRUE.equals(this.getShowAverageGrade()) ? 1 : 0)
                    + (Boolean.TRUE.equals(this.getShowWeightedAverageGrade()) ? 1 : 0);
        }
        int columnCount = resultColumns.size();
        int tableCount = columnCount / TABLE_SIZE;
        if (columnCount % TABLE_SIZE != 0) {
            tableCount++;
        }
        boolean absencesAdded = false;

        tables = new ArrayList<>();
        for (int i = 0; i < tableCount; i++) {
            StudentGroupTeacherReportTable table = new StudentGroupTeacherReportTable();
            System.out.println(i * TABLE_SIZE + " - " + Math.min((i + 1) * TABLE_SIZE, resultColumns.size()));
            List<String> tableResultColumns = resultColumns.subList(i * TABLE_SIZE,
                    Math.min((i + 1) * TABLE_SIZE, resultColumns.size()));

            List<Map<String, Object>> tableStudents = new ArrayList<>();
            for (StudentDto student : dto.getStudents()) {
                Map<String, Object> tableStudent = new HashMap<>();

                List<String> results = studentResultColumns.get(student.getId());
                List<String> tableResults = results.subList(i * TABLE_SIZE,
                        Math.min((i + 1) * TABLE_SIZE, results.size()));

                tableStudent.put("fullname", student.getFullname());
                tableStudent.put("status", student.getStatus());
                tableStudent.put("isIndividualCurriculum", student.getIsIndividualCurriculum());
                tableStudent.put("resultColumns", tableResults);

                if (this.showStudentProgress && i == 0) {
                    tableStudent.put("progress", getStudentProgress(student));
                }

                if (this.showTotalColumns && i == tableCount - 1 && tableResults.size() + totalColumns <= TABLE_SIZE) {
                    absencesAdded = true;
                    setStudentAbsenceColumns(tableStudent, student);
                }
                tableStudents.add(tableStudent);
            }
            table.setResultColumns(tableResultColumns);
            table.setStudents(tableStudents);
            tables.add(table);
        }

        if (this.showTotalColumns && !absencesAdded) {
            StudentGroupTeacherReportTable table = new StudentGroupTeacherReportTable();
            List<Map<String, Object>> tableStudents = new ArrayList<>();
            for (StudentDto student : dto.getStudents()) {
                Map<String, Object> tableStudent = new HashMap<>();
                tableStudent.put("fullname", student.getFullname());
                setStudentAbsenceColumns(tableStudent, student);
                tableStudents.add(tableStudent);
            }
            table.setStudents(tableStudents);
            tables.add(table);
        }
    }

    private static Map<String, Object> getStudentProgress(StudentDto student) {
        Map<String, Object> progress = new HashMap<>();
        if (student.getProgress() != null) {
            progress.put("isCurriculumFulfilled", student.getProgress().getIsCurriculumFulfilled());
            progress.put("isCumLaude", student.getProgress().getIsCumLaude());
            progress.put("weightedAverageGrade", student.getProgress().getWeightedAverageGrade());
        }
        return progress;
    }

    private static void setStudentAbsenceColumns(Map<String, Object> tableStudent, StudentDto student) {
        tableStudent.put("journalEntryLessons", student.getJournalEntryLessons());
        tableStudent.put("totalAbsences", student.getTotalAbsences());
        tableStudent.put("lessonAbsencePercentage", student.getLessonAbsencePercentage());
        tableStudent.put("withoutReasonAbsences", student.getWithoutReasonAbsences());
        tableStudent.put("withoutReasonAbsencesPercentage", student.getWithoutReasonAbsencesPercentage());
        tableStudent.put("withReasonAbsences", student.getWithReasonAbsences());
        tableStudent.put("beingLate", student.getBeingLate());
        tableStudent.put("averageGrade", student.getAverageGrade());
        tableStudent.put("weightedAverageGrade", student.getWeightedAverageGrade());
    }

    public List<StudentGroupTeacherReportTable> getTables() {
        return tables;
    }

    public void setTables(List<StudentGroupTeacherReportTable> tables) {
        this.tables = tables;
    }

    public List<StudentGroupTeacherReportJournal> getJournals() {
        return journals;
    }

    public void setJournals(List<StudentGroupTeacherReportJournal> journals) {
        this.journals = journals;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Boolean getShowAverageGrade() {
        return showAverageGrade;
    }

    public void setShowAverageGrade(Boolean showAverageGrade) {
        this.showAverageGrade = showAverageGrade;
    }

    public Boolean getShowWeightedAverageGrade() {
        return showWeightedAverageGrade;
    }

    public void setShowWeightedAverageGrade(Boolean showWeightedAverageGrade) {
        this.showWeightedAverageGrade = showWeightedAverageGrade;
    }

    public Boolean getShowStudentProgress() {
        return showStudentProgress;
    }

    public void setShowStudentProgress(Boolean showStudentProgress) {
        this.showStudentProgress = showStudentProgress;
    }

    public Boolean getShowTotalColumns() {
        return showTotalColumns;
    }

    public void setShowTotalColumns(Boolean showTotalColumns) {
        this.showTotalColumns = showTotalColumns;
    }
}
