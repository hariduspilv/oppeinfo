package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.FinalThesisSupervisor;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.FinalThesisStatus;
import ee.hitsa.ois.enums.StudentStatus;
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
import ee.hitsa.ois.web.dto.FinalThesisDto;
import ee.hitsa.ois.web.dto.FinalThesisSearchDto;
import ee.hitsa.ois.web.dto.TeacherDto;

@Transactional
@Service
public class FinalThesisService {

    @Autowired
    private EntityManager em;

    private static final String SEARCH_FROM = "from final_thesis ft"
            + " left join final_thesis_supervisor fts on fts.final_thesis_id = ft.id"
            + " join student s on ft.student_id = s.id"
            + " join student_group sg on s.student_group_id = sg.id"
            + " join person p on s.person_id = p.id"
            + " join curriculum_version cv on s.curriculum_version_id = cv.id" 
            + " join curriculum c on cv.curriculum_id = c.id";
    private static final String SEARCH_SELECT = "ft.id as final_thesis_id, ft.theme_et, ft.theme_en, s.id as student_id, p.firstname," + 
            " p.lastname, p.idcode, string_agg(fts.firstname || ' ' || fts.lastname, ', ' order by fts.is_primary desc)," +
            " cv.code as curriculum_version_code, c.name_et, c.name_en, sg.code as student_group_code, ft.inserted, ft.confirmed";

    public Page<FinalThesisSearchDto> search(HoisUserDetails user, @Valid FinalThesisSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
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
            //TODO: get rid of em.getReference ?
            dto.setCanBeEdited(Boolean.valueOf(FinalThesisUtil.canEdit(user, em.getReference(FinalThesis.class, dto.getId()))));
            return dto;
        });
    }
    
    public FinalThesisDto get(HoisUserDetails user, FinalThesis finalThesis) {
        FinalThesisDto dto = FinalThesisDto.of(finalThesis);
        dto.setCanBeEdited(Boolean.valueOf(FinalThesisUtil.canEdit(user, finalThesis)));
        dto.setCanBeConfirmed(Boolean.valueOf(FinalThesisUtil.canConfirm(user, finalThesis)));
        
        if (user.isStudent()) {
            dto.getSupervisors().stream().filter(s -> s.getTeacher() != null).forEach(s -> s.setIdcode(null));
        }
        
        return dto;
    }
    
    public FinalThesis create(FinalThesisForm form) {
        studentIsUnique(form.getStudent().getId());
        
        FinalThesis finalThesis = EntityUtil.bindToEntity(form, new FinalThesis(), "student", "status", "supervisors");
        finalThesis.setStudent(EntityUtil.getOptionalOne(Student.class, form.getStudent(), em));
        finalThesis.setStatus(em.getReference(Classifier.class, FinalThesisStatus.LOPUTOO_STAATUS_S.name()));

        return save(finalThesis, form);
    }
    
    private void studentIsUnique(Long studentId) {
        List<?> data = em.createNativeQuery("select ft.id from final_thesis ft where ft.student_id = ?1")
        .setParameter(1, studentId).setMaxResults(1).getResultList();
        
        if (!data.isEmpty()) {
            throw new ValidationFailedException("finalThesis.error.studentNotUnique");
        }
    }
    
    public FinalThesis save(FinalThesis finalThesis, FinalThesisForm form) {
        EntityUtil.bindToEntity(form, finalThesis, "student", "status", "supervisors", "draft");
        finalThesis.setDraft(Boolean.TRUE.equals(form.getHasDraft()) ? form.getDraft() : null);

        EntityUtil.bindEntityCollection(finalThesis.getSupervisors(), FinalThesisSupervisor::getId,
            form.getSupervisors(), FinalThesisSupervisorForm::getId, supervisorForm -> {
                FinalThesisSupervisor supervisor = EntityUtil.bindToEntity(supervisorForm, new FinalThesisSupervisor(), "finalThesis", "teacher");
                supervisor.setFinalThesis(finalThesis);
                if (supervisorForm.getTeacher() != null) {
                    Teacher teacher = em.getReference(Teacher.class, supervisorForm.getTeacher().getId());
                    supervisor.setTeacher(teacher);
                    supervisor.setIdcode(teacher.getPerson().getIdcode());
                    supervisor.setOccupation(teacher.getTeacherOccupation().getOccupationEt());
                    supervisor.setEmail(teacher.getEmail());
                    supervisor.setPhone(teacher.getPhone());
                }
                return supervisor;
            }, (supervisorForm, supervisor) -> {
                EntityUtil.bindToEntity(supervisorForm, supervisor, "finalThesis", "teacher");
                if (supervisorForm.getTeacher() != null) {
                    Teacher teacher = em.getReference(Teacher.class, supervisorForm.getTeacher().getId());
                    supervisor.setTeacher(teacher);
                    supervisor.setIdcode(teacher.getPerson().getIdcode());
                    supervisor.setOccupation(teacher.getTeacherOccupation().getOccupationEt());
                    supervisor.setEmail(teacher.getEmail());
                    supervisor.setPhone(teacher.getPhone());
                }
        });
        
        if (FinalThesisUtil.confirmed(finalThesis)) {
            FinalThesisUtil.hasRequiredSupervisors(finalThesis);
        }
        
        return EntityUtil.save(finalThesis, em);
    }
    
    public List<AutocompleteResult> students(HoisUserDetails user, SearchCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id")
                .sort("p.lastname", "p.firstname");
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in :statusCodes", "statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", lookup.getName());
        qb.filter("exists (select c.id from curriculum c "
                + "join curriculum_version cv on cv.curriculum_id = c.id "
                + "left join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                + "where cv.id = s.curriculum_version_id "
                + "and (c.is_higher = false or cvh.type_code = 'KORGMOODUL_L'))");
        qb.filter("s.id not in (select ft.student_id from final_thesis ft)");
        
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
        finalThesis.setStatus(em.getReference(Classifier.class,  FinalThesisStatus.LOPUTOO_STAATUS_K.name()));
        finalThesis.setConfirmed(LocalDateTime.now());
        finalThesis.setConfirmedBy(user.getUsername());
        return save(finalThesis, form);
    }
    
    public Map<String, Object> studentFinalThesis(HoisUserDetails user) {
        List<?> finalThesisRequired = em.createNativeQuery("select s.id from student s " + 
                "join curriculum_version cv on cv.id = s.curriculum_version_id " + 
                "join curriculum c on c.id = cv.curriculum_id " + 
                "left join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id " + 
                "where s.id = ?1 and (c.is_higher = false or cvh.type_code = 'KORGMOODUL_L')")
                .setParameter(1, user.getStudentId()).getResultList();
        Boolean required = !finalThesisRequired.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
        
        List<?> existingThesis = em.createNativeQuery("select ft.id from final_thesis ft where ft.student_id = ?1")
        .setParameter(1, user.getStudentId()).setMaxResults(1).getResultList();
        Long finalThesisId = !existingThesis.isEmpty() ? resultAsLong(existingThesis.get(0), 0) : null;
        
        Map<String, Object> studentFinalThesis = new HashMap<>();
        studentFinalThesis.put("finalThesisRequired", required);
        studentFinalThesis.put("finalThesis", finalThesisId);
        return studentFinalThesis;
    }
    
}
