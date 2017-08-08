package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class MidtermTaskUtil {

    public static void checkIfStudentResultCanBeChanged(DeclarationSubject declarationSubject) {
        if(!studentResultCanBeChanged(declarationSubject)) {
            throw new ValidationFailedException("midtermTask.error.studentResultCannotBeChanded");
        }
    }

    public static boolean studentResultCanBeChanged(DeclarationSubject declarationSubject) {
        Declaration declaration = declarationSubject.getDeclaration();
        return ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_K, declaration.getStatus()) &&
               !ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_L, declaration.getStudent().getStatus()) && 
               !studentHasConfirmedProtocol(declarationSubject);
    }
    
    private static boolean studentHasConfirmedProtocol(DeclarationSubject declarationSubject) {
        return !CollectionUtils.isEmpty(declarationSubject.getSubjectStudyPeriod().getProtocols()
                .stream().filter(p -> HigherProtocolUtil
                        .isConfirmed(p.getProtocol()) && 
                        protocolIncludesStudent(p, declarationSubject)).collect(Collectors.toSet()));
    }
    
    private static boolean protocolIncludesStudent(ProtocolHdata p, DeclarationSubject declarationSubject) {
        Set<Long> protocolStudents = 
                StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getStudent()), 
                        p.getProtocol().getProtocolStudents());
        return protocolStudents.contains(EntityUtil.getId(declarationSubject.getDeclaration().getStudent()));
    }

    public static Boolean getStudentResultIsText(MidtermTask midtermTask) {
        return Boolean.valueOf(BigDecimal.ZERO.compareTo(midtermTask.getMaxPoints()) == 0);
    }

    public static void checkUserRights(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        if (!user.isTeacher() && !user.isSchoolAdmin() || 
                user.getSchoolId() == null || !user.getSchoolId().equals
                (EntityUtil.getNullableId(subjectStudyPeriod.getSubject().getSchool()))) {
            throw new ValidationFailedException("midtermTask.error.nopermission");
        }
    }

    public static void checkStudentResultsPoints(MidtermTaskStudentResult studentResult) {
        AssertionFailedException.throwIf(studentResult.getPoints() != null && 
                studentResult.getMidtermTask().getMaxPoints().compareTo(studentResult.getPoints()) < 0,
                "MidtermTask maxPoints exceeded");
    }

    public static boolean midtermTaskCanBeEdited(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        return !LocalDate.now().isAfter(subjectStudyPeriod.getStudyPeriod().getEndDate()) && (
                user.isSchoolAdmin() || user.isTeacher());
    }

    public static Boolean midtermTaskCanBeDeleted(MidtermTask midtermTask) {
        return  Boolean.valueOf(CollectionUtils.isEmpty(midtermTask.getStudentResults()));
    }

    public static void checkIfMidtermTasksCanBeEdited(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod){
        AssertionFailedException.throwIf(!midtermTaskCanBeEdited(user, subjectStudyPeriod),
                "You cannot change midtermTasks!");
    }

    public static Set<MidtermTaskStudentResult> getStudentResults(SubjectStudyPeriod subjectStudyPeriod) {
        Set<MidtermTaskStudentResult> studentResults = new HashSet<>();
        for(DeclarationSubject declarationSubject : subjectStudyPeriod.getDeclarationSubjects()) {
            studentResults.addAll(declarationSubject.getMidtermTaskStudentResults());
        }
        return studentResults;
    }
    
    public static boolean resultIsText(MidtermTask task) {
        return BigDecimal.ZERO.compareTo(task.getMaxPoints()) == 0;
    }
    
    public static void assertNotPractice(SubjectStudyPeriod ssp){
        if(Boolean.TRUE.equals(ssp.getSubject().getIsPractice())) {
            throw new ValidationFailedException("midtermTask.error.isPractice");
        }
    }
}
