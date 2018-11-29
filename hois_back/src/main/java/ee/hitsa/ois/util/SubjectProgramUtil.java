package ee.hitsa.ois.util;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

@Component
public class SubjectProgramUtil {
    
    @Autowired
    private EntityManager em;

    public boolean canView(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id ");
        from.append("left join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = ssp.subject_id ");
        from.append("left join curriculum_version_hmodule cvhm on cvhm.id = cvhms.curriculum_version_hmodule_id ");
        from.append("left join curriculum_version cv on cv.id = cvhm.curriculum_version_id ");
        from.append("left join curriculum c on c.id = cv.curriculum_id ");
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
        qb.requiredCriteria("sp.id = :programId", "programId", program.getId());
        qb.requiredCriteria("(sspt.teacher_id = :teacherId or c.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        
        if (qb.select("sp.id", em).getResultList().size() != 1) {
            return false;
        }
        return true;
    }

    public void assertCanView(HoisUserDetails user, SubjectProgram program) {
        if (!canView(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canCreate(HoisUserDetails user) {
        if (!user.isTeacher()) {
            return false;
        }
        return true;
    }
    
    public void assertCanCreate(HoisUserDetails user) {
        if (!canCreate(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canEdit(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        return true;
    }

    public void assertCanEdit(HoisUserDetails user, SubjectProgram program) {
        if (!canEdit(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canDelete(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        return true;
    }

    public void assertCanDelete(HoisUserDetails user, SubjectProgram program) {
        if (!canDelete(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canSearch(HoisUserDetails user) {
        if (!user.isTeacher()) {
            return false;
        }
        return true;
    }

    public void assertCanSearch(HoisUserDetails user) {
        if (!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canConfirm(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id ");
        from.append("join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = ssp.subject_id ");
        from.append("join curriculum_version_hmodule cvhm on cvhm.id = cvhms.curriculum_version_hmodule_id ");
        from.append("join curriculum_version cv on cv.id = cvhm.curriculum_version_id ");
        from.append("join curriculum c on c.id = cv.curriculum_id ");
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
        qb.requiredCriteria("sp.id = :programId", "programId", program.getId());
        qb.requiredCriteria("c.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        qb.requiredCriteria("sp.status_code = :status", "status", SubjectProgramStatus.AINEPROGRAMM_STAATUS_V);
        
        if (qb.select("sp.id", em).getResultList().size() != 1) {
            return false;
        }
        return true;
    }
    
    public void assertCanConfirm(HoisUserDetails user, SubjectProgram program) {
        if (!canConfirm(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public boolean canComplete(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        if (!ClassifierUtil.equals(SubjectProgramStatus.AINEPROGRAMM_STAATUS_I, program.getStatus())) {
            return false;
        }
        return true;
    }

    public void assertCanComplete(HoisUserDetails user, SubjectProgram program) {
        if (!canComplete(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public boolean canReject(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id ");
        from.append("join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = ssp.subject_id ");
        from.append("join curriculum_version_hmodule cvhm on cvhm.id = cvhms.curriculum_version_hmodule_id ");
        from.append("join curriculum_version cv on cv.id = cvhm.curriculum_version_id ");
        from.append("join curriculum c on c.id = cv.curriculum_id ");
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
        qb.requiredCriteria("sp.id = :programId", "programId", program.getId());
        qb.requiredCriteria("c.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        qb.filter("(sp.status_code = 'AINEPROGRAMM_STAATUS_V' or sp.status_code = 'AINEPROGRAMM_STAATUS_K')");
        
        if (qb.select("sp.id", em).getResultList().size() != 1) {
            return false;
        }
        return true;
    }

    public void assertCanReject(HoisUserDetails user, SubjectProgram program) {
        if (!canReject(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        
    }
    
}
