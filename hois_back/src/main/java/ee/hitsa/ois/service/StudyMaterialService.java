package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.studymaterial.StudyMaterialConnect;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudyMaterialUserRights;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.commandobject.studymaterial.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialForm;
import ee.hitsa.ois.web.commandobject.studymaterial.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialConnectDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class StudyMaterialService {

    @Autowired
    private EntityManager em;

    private static final String SUBJECT_STUDY_PERIOD_FROM = "from subject_study_period ssp"
            + " inner join subject s on s.id = ssp.subject_id"
            + " inner join study_period sp on sp.id = ssp.study_period_id"
            + " left join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
            + " left join student_group sg on sg.id = sspsg.student_group_id";
    private static final String SUBJECT_STUDY_PERIOD_SELECT = "ssp.id as subject_study_period_id"
            + ", s.id as subject_id, s.name_et as subject_name_et, s.name_en as subject_name_en"
            + ", s.code as subject_code, s.credits as subject_credits"
            + ", sp.id as study_period_id, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en"
    + ", (select count(*) from study_material_connect where subject_study_period_id = ssp.id) as material_count, string_agg(sg.code, ', ' order by sg.code) as groups";

    private static final String JOURNAL_FROM = "from journal j"
            + " inner join study_year sy on sy.id = j.study_year_id"
            + " inner join classifier c on sy.year_code = c.code";
    private static final String JOURNAL_SELECT = "j.id as journal_id, j.name_et as journal_name"
            + ", j.study_year_id as study_year_id, c.name_et as study_year_name_et, c.name_en as study_year_name_en"
            + ", (select count(*) from study_material_connect where journal_id = j.id) as material_count";

    private static final String MATERIAL_FROM = "from study_material m"
            + " left join ois_file f on f.id = m.ois_file_id"
            + " join study_material_connect mc on mc.study_material_id = m.id"
            + " join teacher t on t.id = m.teacher_id"
            + " join person p on p.id = t.person_id";
    private static final String MATERIAL_SELECT = "m.id as material_id, m.name_et as material_name"
            + ", m.type_code, t.id, p.firstname, p.lastname"
            + ", m.is_public, m.is_visible_to_students, f.id as file_id, f.fname, f.ftype, m.url"
            + ", mc.id as connect_id, mc.version as connect_version"
            + ", (select count(*) from study_material_connect where study_material_id = m.id) as journal_count";
    
    private static final String MATERIAL_DELETE = "delete from study_material"
            + " where id in (select m.id from study_material m"
            + " left join study_material_connect mc on mc.study_material_id = m.id"
            + " where mc.id is null)";

    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods(HoisUserDetails user, 
            SubjectStudyPeriodSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_STUDY_PERIOD_FROM).sort(pageable).groupBy("ssp.id, s.id, sp.id");
        
        qb.requiredCriteria("s.school_id = :school_id", "school_id", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists (select cv.curriculum_id from curriculum_version_hmodule_subject cvhs "
                    + "join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id "
                    + "join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                    + "join user_curriculum uc on uc.curriculum_id = cv.curriculum_id "
                    + "where cvhs.subject_id = s.id and uc.user_id = :userId)", "userId", user.getUserId());
        }

        qb.optionalCriteria("ssp.subject_id = :subject_id", "subject_id", command.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :study_period_id", "study_period_id", command.getStudyPeriod());
        qb.optionalCriteria("ssp.id in (select subject_study_period_id from subject_study_period_teacher where teacher_id = :teacher_id)", 
                "teacher_id", command.getTeacher());
        qb.optionalCriteria("sg.id = :sgId", "sgId", command.getStudentGroup());
        
        return JpaQueryUtil.pagingResult(qb, SUBJECT_STUDY_PERIOD_SELECT, em, pageable).map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            String code = resultAsString(r, 4);
            BigDecimal credits = resultAsDecimal(r, 5);
            dto.setSubject(new AutocompleteResult(resultAsLong(r, 1), 
                    SubjectUtil.subjectName(code, resultAsString(r, 2), credits), 
                    SubjectUtil.subjectName(code, resultAsString(r, 3), credits)));
            dto.setStudyPeriod(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setMaterialCount(resultAsLong(r, 9));
            String groups = resultAsString(r, 10);
            if (groups == null) {
                dto.setStudentGroups(Collections.emptySet());
            } else {
                dto.setStudentGroups(Arrays.stream(groups.split(", ")).collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            return dto;
        });
    }

    public SubjectStudyPeriodSearchDto getSubjectStudyPeriod(HoisUserDetails user,
            SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodSearchDto dto = SubjectStudyPeriodSearchDto.of(subjectStudyPeriod);
        dto.setCanConnectStudyMaterials(
                Boolean.valueOf(StudyMaterialUserRights.canEditSubjectStudyPeriod(user, subjectStudyPeriod)));
        return dto;
    }

    public Page<JournalSearchDto> searchJournals(HoisUserDetails user, JournalSearchCommand command,
            Pageable pageable) {
        StringBuilder from = new StringBuilder(JOURNAL_FROM).append(" ");
        from.append("left join journal_omodule_theme jot on j.id=jot.journal_id ");
        from.append("left join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id ");
        from.append("left join lesson_plan lp on lpm.lesson_plan_id=lp.id ");
        from.append("left join student_group sg on lp.student_group_id=sg.id ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable)
                .groupBy("j.id, c.name_et, c.name_en");

        qb.requiredCriteria("j.school_id = :school_id", "school_id", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("sg.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.optionalCriteria("j.id = :journal_id", "journal_id", command.getJournal());
        qb.optionalCriteria("j.study_year_id = :study_year_id", "study_year_id", command.getStudyYear());
        qb.optionalCriteria("sg.id = :sgId", "sgId", command.getStudentGroup());
        qb.optionalCriteria("j.id in (select journal_id from journal_teacher where teacher_id = :teacher_id)", 
                "teacher_id", command.getTeacher());
        
        StringBuilder select = new StringBuilder(JOURNAL_SELECT);
        select.append(", string_agg(sg.code, ', ' order by sg.code) as groups");
        return JpaQueryUtil.pagingResult(qb, select.toString(), em, pageable).map(r -> {
            JournalSearchDto dto = new JournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setStudyYear(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4)));
            dto.setMaterialCount(resultAsLong(r, 5));
            String groups = resultAsString(r, 6);
            if (groups == null) {
                dto.setStudentGroups(Collections.emptySet());
            } else {
                dto.setStudentGroups(Arrays.stream(groups.split(", ")).collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            return dto;
        });
    }

    public JournalDto getJournal(HoisUserDetails user, Journal journal) {
        JournalDto dto = JournalDto.of(journal);
        dto.setCanConnectStudyMaterials(Boolean.valueOf(StudyMaterialUserRights.canEditJournal(user, journal)));
        return dto;
    }

    public List<StudyMaterialSearchDto> materials(HoisUserDetails user, Journal journal,
            SubjectStudyPeriod subjectStudyPeriod) {
        Boolean isPublic = null;
        Boolean isVisibleToStudents = null;

        if (user != null) {
            if (!((user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                    && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPEMATERJAL))) {
                if (user.isStudent() || user.isRepresentative()) {
                    isVisibleToStudents = Boolean.TRUE;
                } else {
                    isPublic = Boolean.TRUE;
                }
            }
        } else {
            isPublic = Boolean.TRUE;
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(MATERIAL_FROM).sort("m.name_et");
        qb.optionalCriteria("mc.subject_study_period_id = :subject_study_period_id", "subject_study_period_id",
                EntityUtil.getNullableId(subjectStudyPeriod));
        qb.optionalCriteria("mc.journal_id = :journal_id", "journal_id", EntityUtil.getNullableId(journal));
        qb.optionalCriteria("m.is_public = :is_public", "is_public", isPublic);
        qb.optionalCriteria("m.is_visible_to_students = :is_visible_to_students", "is_visible_to_students",
                isVisibleToStudents);

        List<?> data = qb.select(MATERIAL_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudyMaterialSearchDto dto = new StudyMaterialSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setTypeCode(resultAsString(r, 2));
            String teacherName = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 3), teacherName, teacherName));
            dto.setIsPublic(resultAsBoolean(r, 6));
            dto.setIsVisibleToStudents(resultAsBoolean(r, 7));

            OisFileViewDto oisFileDto = new OisFileViewDto();
            oisFileDto.setId(OisFileService.encryptAndDecodeId(resultAsLong(r, 8)));
            oisFileDto.setFname(resultAsString(r, 9));
            oisFileDto.setFtype(resultAsString(r, 10));
            dto.setOisFile(oisFileDto);

            dto.setUrl(resultAsString(r, 11));

            StudyMaterialConnectDto connectDto = new StudyMaterialConnectDto();
            connectDto.setId(resultAsLong(r, 12));
            connectDto.setVersion(resultAsLong(r, 13));
            dto.setConnect(connectDto);

            dto.setJournalCount(resultAsLong(r, 14));
            dto.setCanEdit(Boolean.valueOf(user != null && (user.isSchoolAdmin() || user.isLeadingTeacher()
                    || (user.isTeacher() && user.getTeacherId().equals(dto.getTeacher().getId())))));
            return dto;
        }, data);
    }

    public StudyMaterialDto get(HoisUserDetails user, StudyMaterial material) {
        StudyMaterialDto dto = StudyMaterialDto.of(material);
        dto.setCanEdit(Boolean.valueOf(StudyMaterialUserRights.canEdit(user, material)));
        return dto;
    }

    public List<AutocompleteResult> subjectStudyPeriodTeachers(SubjectStudyPeriod subjectStudyPeriod,
            Long studyMaterialId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id");

        String filter = "t.id in (select sspt.teacher_id from subject_study_period_teacher sspt"
                + " where sspt.subject_study_period_id = :subjectStudyPeriod)";
        qb.parameter("subjectStudyPeriod", EntityUtil.getId(subjectStudyPeriod));
        if (studyMaterialId != null) {
            filter += " or t.id in (select sm.teacher_id from study_material sm where sm.id = :studyMaterialId)";
            qb.parameter("studyMaterialId", studyMaterialId);
        }
        qb.filter(filter);

        List<?> data = qb.select("t.id t_id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String fullname = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), fullname, fullname);
        }, data);
    }

    public List<AutocompleteResult> journalMaterialTeachers(Journal journal, Long studyMaterialId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id");

        String filter = "t.id in (select jt.teacher_id from journal_teacher jt where jt.journal_id = :journalId)";
        qb.parameter("journalId", EntityUtil.getId(journal));
        if (studyMaterialId != null) {
            filter += " or t.id in (select sm.teacher_id from study_material sm where sm.id = :studyMaterialId)";
            qb.parameter("studyMaterialId", studyMaterialId);
        }
        qb.filter(filter);

        List<?> data = qb.select("t.id t_id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String fullname = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), fullname, fullname);
        }, data);
    }

    public StudyMaterial createSubjectStudyPeriodMaterial(HoisUserDetails user, StudyMaterialForm materialForm,
            SubjectStudyPeriod subjectStudyPeriod) {
        StudyMaterial material = create(user, materialForm);
        connectSubjectStudyPeriod(user, subjectStudyPeriod, material.getId());
        return material;
    }

    public StudyMaterial createJournalMaterial(HoisUserDetails user, StudyMaterialForm materialForm, Journal journal) {
        StudyMaterial material = create(user, materialForm);
        connectJournal(user, journal, material.getId());
        return material;
    }

    private StudyMaterial create(HoisUserDetails user, StudyMaterialForm materialForm) {
        StudyMaterial material = new StudyMaterial();
        material.setSchool(em.getReference(School.class, user.getSchoolId()));
        material.setOisFile(createFile(user, materialForm.getOisFile()));
        return save(user, material, materialForm);
    }

    public StudyMaterial save(HoisUserDetails user, StudyMaterial material, StudyMaterialForm materialForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        material.setTeacher(em.getReference(Teacher.class, materialForm.getTeacher()));
        EntityUtil.bindToEntity(materialForm, material, "canEdit");
        return EntityUtil.save(material, em);
    }

    private OisFile createFile(HoisUserDetails user, OisFileCommand fileForm) {
        if (fileForm == null) {
            return null;
        }
        OisFile oisFile = new OisFile();
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(fileForm, oisFile);
        return EntityUtil.save(oisFile, em);
    }

    public StudyMaterialConnect connectSubjectStudyPeriod(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod,
            Long materialId) {
        EntityUtil.setUsername(user.getUsername(), em);
        StudyMaterialConnect materialConnect = new StudyMaterialConnect();
        materialConnect.setStudyMaterial(em.getReference(StudyMaterial.class, materialId));
        materialConnect.setSubjectStudyPeriod(subjectStudyPeriod);
        return EntityUtil.save(materialConnect, em);
    }

    public StudyMaterialConnect connectJournal(HoisUserDetails user, Journal journal, Long materialId) {
        EntityUtil.setUsername(user.getUsername(), em);
        StudyMaterialConnect materialConnect = new StudyMaterialConnect();
        materialConnect.setStudyMaterial(em.getReference(StudyMaterial.class, materialId));
        materialConnect.setJournal(journal);
        return EntityUtil.save(materialConnect, em);
    }

    public void delete(HoisUserDetails user, StudyMaterialConnect materialConnect) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(materialConnect, em);
        em.createNativeQuery(MATERIAL_DELETE).executeUpdate();
    }

}
