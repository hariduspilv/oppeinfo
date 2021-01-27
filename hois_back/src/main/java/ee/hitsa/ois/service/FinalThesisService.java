package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisDtoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.FinalThesisCercs;
import ee.hitsa.ois.domain.FinalThesisSupervisor;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.FinalThesisStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalThesisUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.FinalThesisForm;
import ee.hitsa.ois.web.commandobject.FinalThesisSearchCommand;
import ee.hitsa.ois.web.commandobject.FinalThesisSupervisorForm;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm.TeacherPersonForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisDto;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisSearchDto;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisStudentDto;

@Transactional
@Service
public class FinalThesisService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    private static final String SEARCH_FROM = "from final_thesis ft"
            + " left join final_thesis_supervisor fts on fts.final_thesis_id = ft.id"
            + " join student s on ft.student_id = s.id"
            + " left join student_group sg on s.student_group_id = sg.id"
            + " join person p on s.person_id = p.id"
            + " join curriculum_version cv on s.curriculum_version_id = cv.id" 
            + " join curriculum c on cv.curriculum_id = c.id";
    private static final String SEARCH_SELECT = "ft.id as final_thesis_id, ft.theme_et, ft.theme_en, s.id as student_id, p.firstname," + 
            " p.lastname, p.idcode, string_agg(fts.firstname || ' ' || fts.lastname, ', ' order by fts.is_primary desc)," +
            " cv.code as curriculum_version_code, c.name_et, c.name_en, sg.code as student_group_code, ft.inserted," +
            " ft.confirmed, ft.status_code ft_status";

    private static final String FINAL_THESIS_EXPECTED = "exists (select c.id from curriculum c"
            + " join curriculum_version cv on cv.curriculum_id = c.id"
            + " left join curriculum_version_hmodule cvhm on cvhm.curriculum_version_id = cv.id"
            + " where cv.id = s.curriculum_version_id and"
            + " (c.is_higher = false or (cvhm.is_minor_speciality = false and (cvhm.type_code = '" + HigherModuleType.KORGMOODUL_L.name() + "'"
                + " and (s.curriculum_speciality_id is null or s.curriculum_speciality_id in ("
                    + " select cs.id from curriculum_version_hmodule_speciality cvhs"
                    + " join curriculum_version_speciality cvs on cvs.id = cvhs.curriculum_version_speciality_id"
                    + " join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id"
                    + " where cvhs.curriculum_version_hmodule_id = cvhm.id))))))";

    private static final String NO_FINAL_EXAM_DECLARED = "not exists (select ds.id from student s2"
            + " join curriculum_version cv2 on cv2.id = s2.curriculum_version_id"
            + " join curriculum_version_hmodule cvhm2 on cvhm2.curriculum_version_id = cv2.id"
            + " join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvhm2.id"
            + " join subject_study_period ssp on ssp.subject_id = cvhs.subject_id"
            + " join declaration_subject ds on ds.subject_study_period_id = ssp.id"
            + " join declaration d on d.id = ds.declaration_id and d.student_id = s2.id"
            + " where s2.id = s.id and cvhm2.type_code = '" + HigherModuleType.KORGMOODUL_F.name() +  "')";

    private static final String EXISTING_FINAL_THESIS = "select ft.id from final_thesis ft where ft.student_id = s.id limit 1";
    private static final String NO_FINAL_THESIS = "not exists (" + EXISTING_FINAL_THESIS + ")";

    private static final String SPECIALITY_REQUIREMENT_MET = "case when (select count(cvs.id) from curriculum_version cv2 "
            + "join curriculum_version_speciality cvs on cvs.curriculum_version_id = cv2.id "
            + "where cv2.id = s.curriculum_version_id) > 1 then s.curriculum_speciality_id is not null else true end";

    public Page<FinalThesisSearchDto> search(HoisUserDetails user, @Valid FinalThesisSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalContains(Arrays.asList("ft.theme_et", "ft.theme_en"), "theme", criteria.getTheme());
        qb.optionalCriteria("ft.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalContains("p.firstname || ' ' || p.lastname" , "name", criteria.getStudentName());
        
        if (user.isTeacher()) {
            qb.filter("exists(select ft.id from final_thesis "
                    + "join final_thesis_supervisor fts on fts.final_thesis_id = ft.id "
                    + "where fts.teacher_id = " + user.getTeacherId() + ")");
        }

        if (criteria.getSupervisor() != null) {
            qb.filter("exists(select ft.id from final_thesis "
                    + "join final_thesis_supervisor fts on fts.final_thesis_id = ft.id "
                    + "where upper(fts.firstname) like '%" + criteria.getSupervisor().toUpperCase() +  "%' "
                    + "or upper(fts.lastname) like '%" + criteria.getSupervisor().toUpperCase() + "%' "
                    + "or upper(fts.firstname || ' ' || fts.lastname) like '%" + criteria.getSupervisor().toUpperCase() + "%')");
        }
        
        if (criteria.getCurriculumVersion() != null) {
            qb.filter("exists(select ft.id from final_thesis "
                    + "join student s on s.id = ft.student_id "
                    + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                    + "where cv.id = " + criteria.getCurriculumVersion() + ")");
        }
        
        if (criteria.getStudentGroup() != null) {
            qb.filter("exists(select ft.id from final_thesis "
                    + "join student s on s.id = ft.student_id "
                    + "where s.student_group_id = " + criteria.getStudentGroup() + ")");
        }
        
        qb.optionalCriteria("ft.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("ft.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("ft.confirmed >= :confirmedFrom", "confirmedFrom", criteria.getConfirmedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("ft.confirmed <= :confirmedThru", "confirmedThru", criteria.getConfirmedThru(), DateUtils::lastMomentOfDay);
        
        qb.groupBy("ft.id, s.id, p.id, cv.id, c.id, sg.id");
        
        Page<Object> result = JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable);
        
        return result.map(r -> {
            FinalThesisSearchDto dto = new FinalThesisSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setThemeEt(resultAsString(r, 1));
            dto.setThemeEn(resultAsString(r, 2));
            
            String studentName = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 3), studentName, studentName));
            
            dto.setIdcode(resultAsString(r, 6));
            dto.setSupervisors(resultAsString(r, 7));
            String curriculumVersionCode = resultAsString(r, 8);
            dto.setCurriculumVersion(new AutocompleteResult(null,
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 9)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 10))));
            dto.setStudentGroup(resultAsString(r, 11));
            dto.setInserted(resultAsLocalDate(r, 12));
            dto.setConfirmed(resultAsLocalDate(r, 13));
            dto.setStatus(resultAsString(r, 14));
            //TODO: get rid of em.getReference ?
            dto.setCanBeEdited(Boolean.valueOf(FinalThesisUtil.canEdit(user, em.getReference(FinalThesis.class, dto.getId()))));
            return dto;
        });
    }

    public FinalThesisDtoContainer get(HoisUserDetails user, FinalThesis finalThesis) {
        FinalThesisDtoContainer container = new FinalThesisDtoContainer();
        container.setFinalThesis(finalThesisDto(user, finalThesis));
        container.setStudent(new FinalThesisStudentDto(finalThesis.getStudent()));
        setFinalThesisRequirements(container, finalThesis.getStudent().getId());
        return container;
    }

    private FinalThesisDto finalThesisDto(HoisUserDetails user, FinalThesis finalThesis) {
        FinalThesisDto dto = FinalThesisDto.of(finalThesis);
        dto.setCanBeEdited(Boolean.valueOf(FinalThesisUtil.canEdit(user, finalThesis)));
        dto.setCanBeDeleted(Boolean.valueOf(FinalThesisUtil.canDelete(user, finalThesis)));
        dto.setCanBeConfirmed(Boolean.valueOf(FinalThesisUtil.canConfirm(user, finalThesis)));
        if (user.isStudent()) {
            dto.getSupervisors().stream().filter(s -> s.getTeacher() != null).forEach(s -> s.setIdcode(null));
        }
        
        return dto;
    }

    public FinalThesisDtoContainer studentFinalThesis(HoisUserDetails user, Long studentId) {
        List<FinalThesis> theses = em.createQuery("select ft from FinalThesis ft"
                + " where ft.student.id = ?1", FinalThesis.class)
                .setParameter(1, user.getStudentId())
                .setMaxResults(1).getResultList();

        FinalThesisDtoContainer container = new FinalThesisDtoContainer();
        if (!theses.isEmpty()) {
            container.setFinalThesis(finalThesisDto(user, theses.get(0)));
        }
        container.setStudent(new FinalThesisStudentDto(em.getReference(Student.class, studentId)));
        setFinalThesisRequirements(container, studentId);
        return container;
    }

    private void setFinalThesisRequirements(FinalThesisDtoContainer container, Long studentId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        qb.parameter("studentStatusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        Object result = qb.select("s.status_code in (:studentStatusCodes) is_student_active, "
                + FINAL_THESIS_EXPECTED + " is_final_thesis_expected, "
                + NO_FINAL_EXAM_DECLARED + " no_final_exam_declared, "
                + SPECIALITY_REQUIREMENT_MET + " speciality_requirement_met", em)
                .setMaxResults(1).getResultList().get(0);

        container.setIsStudentActive(resultAsBoolean(result, 0));
        container.setFinalThesisExpected(resultAsBoolean(result, 1));
        container.setNoFinalExamDeclaration(resultAsBoolean(result, 2));
        container.setSpecialitySet(resultAsBoolean(result, 3));
    }

    public FinalThesis create(HoisUserDetails user, FinalThesisForm form) {
        isFinalThesisStudentValid(form);

        FinalThesis finalThesis = EntityUtil.bindToEntity(form, new FinalThesis(), "student", "status", "supervisors");
        finalThesis.setStudent(EntityUtil.getOptionalOne(Student.class, form.getStudent(), em));
        finalThesis.setStatus(em.getReference(Classifier.class, FinalThesisStatus.LOPUTOO_STAATUS_S.name()));
        return save(finalThesis, form);
    }

    private void isFinalThesisStudentValid(FinalThesisForm form) {
        FinalThesisDtoContainer container = new FinalThesisDtoContainer();
        setFinalThesisRequirements(container, form.getStudent().getId());

        ValidationFailedException.throwIf(!Boolean.TRUE.equals(container.getIsStudentActive()),
                "finalThesis.error.studentNotActive");
        ValidationFailedException.throwIf(!Boolean.TRUE.equals(container.getFinalThesisExpected()),
                "finalThesis.error.noFinalThesisModule");
        ValidationFailedException.throwIf(!Boolean.TRUE.equals(container.getNoFinalExamDeclaration()),
                "finalThesis.error.existsFinalExamDeclaration");
        ValidationFailedException.throwIf(!Boolean.TRUE.equals(container.getSpecialitySet()),
                "finalThesis.error.specialityNotSet");
    }

    public Map<String, Boolean> isFinalThesisStudentActive(FinalThesis finalThesis) {
        return Collections.singletonMap("isActive", StudentUtil.isActive(finalThesis.getStudent()));
    }

    public FinalThesis save(FinalThesis finalThesis, FinalThesisForm form) {
        EntityUtil.bindToEntity(form, finalThesis, classifierRepository, "student", "status", "supervisors", "draft", "cercs");
        finalThesis.setDraft(Boolean.TRUE.equals(form.getHasDraft()) ? form.getDraft() : null);

        EntityUtil.bindEntityCollection(finalThesis.getSupervisors(), FinalThesisSupervisor::getId,
            form.getSupervisors(), FinalThesisSupervisorForm::getId, supervisorForm -> {
                FinalThesisSupervisor supervisor = new FinalThesisSupervisor();
                supervisor.setFinalThesis(finalThesis);
                updateSupervisor(supervisorForm, supervisor);
                return supervisor;
            }, (supervisorForm, supervisor) -> {
                Long oldTeacherId = EntityUtil.getNullableId(supervisor.getTeacher());
                Long newTeacherId = supervisorForm.getTeacher() != null ? supervisorForm.getTeacher().getId() : null;
                
                // inserted is used to send data about final thesis to EHIS
                // if teacher is changed but record is not deleted then it should delete old and create a new record.
                if (Objects.equals(oldTeacherId, newTeacherId)) {
                    updateSupervisor(supervisorForm, supervisor);
                } else {
                    finalThesis.getSupervisors().remove(supervisor);
                    FinalThesisSupervisor nSupervisor = new FinalThesisSupervisor();
                    nSupervisor.setFinalThesis(finalThesis);
                    updateSupervisor(supervisorForm, nSupervisor);
                    finalThesis.getSupervisors().add(nSupervisor);
                }
        });
        
        if (finalThesis.getStudent().getCurriculumVersion() != null
                && CurriculumUtil.isMagisterOrDoctoralOrIntegratedStudy(
                        finalThesis.getStudent().getCurriculumVersion().getCurriculum())) {
            finalThesis.setCurriculumGrade(EntityUtil.getOptionalOne(CurriculumGrade.class, form.getCurriculumGrade(), em));
            EntityUtil.bindEntityCollection(finalThesis.getCercses(), c -> c.getCercs().getCode(), form.getCercses(),
                    c -> c.getCercs(), (formCercs) -> {
                        FinalThesisCercs cercs = new FinalThesisCercs();
                        cercs.setCercs(em.getReference(Classifier.class, formCercs.getCercs()));
                        cercs.setFinalThesis(finalThesis);
                        return cercs;
                    });
        } else {
            finalThesis.setCurriculumGrade(null);
            finalThesis.setLanguage(null);
            finalThesis.getCercses().clear();
        }
        
        if (FinalThesisUtil.confirmed(finalThesis)) {
            FinalThesisUtil.hasRequiredSupervisors(finalThesis);
        }
        
        return EntityUtil.save(finalThesis, em);
    }
    
    private void updateSupervisor(FinalThesisSupervisorForm supervisorForm, FinalThesisSupervisor supervisor) {
        EntityUtil.bindToEntity(supervisorForm, supervisor, classifierRepository, "finalThesis", "teacher");
        if (supervisorForm.getTeacher() != null) {
            Teacher teacher = em.getReference(Teacher.class, supervisorForm.getTeacher().getId());
            supervisor.setTeacher(teacher);
            supervisor.setIdcode(teacher.getPerson().getIdcode());
            supervisor.setOccupation(teacher.getTeacherOccupation().getOccupationEt());
            supervisor.setEmail(teacher.getEmail());
            supervisor.setPhone(teacher.getPhone());
        }
    }

    public void delete(HoisUserDetails user, FinalThesis finalThesis) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(finalThesis, em);
    }

    public List<AutocompleteResult> students(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on s.person_id = p.id");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in :statusCodes", "statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("s.type_code != :studentType", "studentType", StudentType.OPPUR_K.name());
        qb.filter(NO_FINAL_THESIS);
        qb.filter(FINAL_THESIS_EXPECTED);
        qb.filter(NO_FINAL_EXAM_DECLARED);
        qb.filter(SPECIALITY_REQUIREMENT_MET);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", lookup.getName());

        qb.sort("p.lastname", "p.firstname");
        List<?> data = qb.select("s.id, p.firstname, p.lastname, p.idcode", em).setMaxResults(20).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcode(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }
    
    public TeacherDto teacher(Long teacherId) {
        Teacher teacher = em.getReference(Teacher.class, teacherId);
        TeacherDto dto = new TeacherDto();
        dto.setPerson(EntityUtil.bindToDto(teacher.getPerson(), new TeacherPersonForm()));
        dto.setTeacherOccupation(new AutocompleteResult(null, teacher.getTeacherOccupation().getOccupationEt(),
                teacher.getTeacherOccupation().getOccupationEn()));
        dto.setEmail(teacher.getEmail());
        return dto;
    }
    
    public FinalThesis confirm(HoisUserDetails user, FinalThesis finalThesis, FinalThesisForm form) {
        isFinalThesisStudentValid(form);

        finalThesis.setStatus(em.getReference(Classifier.class,  FinalThesisStatus.LOPUTOO_STAATUS_K.name()));
        finalThesis.setConfirmed(LocalDateTime.now());
        finalThesis.setConfirmedBy(user.getUsername());
        return save(finalThesis, form);
    }
}
