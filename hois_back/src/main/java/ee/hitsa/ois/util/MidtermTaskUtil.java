package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.domain.MidtermTaskStudentResultHistory;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import org.springframework.util.StringUtils;

public abstract class MidtermTaskUtil {

    public static void checkIfStudentResultCanBeChanged(DeclarationSubject declarationSubject) {
        if(!studentResultCanBeChanged(declarationSubject)) {
            throw new ValidationFailedException("midtermTask.error.studentResultCannotBeChanded");
        }
    }

    public static boolean studentResultCanBeChanged(DeclarationSubject declarationSubject) {
        Declaration declaration = declarationSubject.getDeclaration();
        return DeclarationUtil.declarationConfirmed(declaration) &&
               StudentUtil.isActive(declaration.getStudent())/* &&
               !studentHasConfirmedProtocol(declarationSubject)*/;
    }

    private static boolean studentHasConfirmedProtocol(DeclarationSubject declarationSubject) {
        return declarationSubject.getSubjectStudyPeriod().getProtocols()
                .stream().anyMatch(p -> ProtocolUtil.confirmed(p.getProtocol()) &&
                        protocolIncludesStudent(p, declarationSubject));
    }

    private static boolean studentHasConfirmedRepeatProtocol(DeclarationSubject declarationSubject) {
        return declarationSubject.getSubjectStudyPeriod().getProtocols()
                .stream().anyMatch(p -> ClassifierUtil.equals(ProtocolType.PROTOKOLLI_LIIK_K, p.getType()) &&
                        ProtocolUtil.confirmed(p.getProtocol()) &&
                        protocolIncludesStudent(p, declarationSubject));
    }

    private static boolean protocolIncludesStudent(ProtocolHdata p, DeclarationSubject declarationSubject) {
        return p.getProtocol().getProtocolStudents().stream().anyMatch(ps -> 
        EntityUtil.getId(declarationSubject.getDeclaration().getStudent())
                .equals(EntityUtil.getId(ps.getStudent())));
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

    public static void checkStudentResultsPoints(MidtermTaskStudentResultHistory studentResultHistory) {
        AssertionFailedException.throwIf(studentResultHistory.getPoints() != null &&
                        studentResultHistory.getMidtermTaskStudentResult().getMidtermTask()
                                .getMaxPoints().compareTo(studentResultHistory.getPoints()) < 0,
                "MidtermTask maxPoints exceeded");
    }

    public static boolean midtermTaskCanBeEdited(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        return !LocalDate.now().isAfter(subjectStudyPeriod.getStudyPeriod().getEndDate()) &&
                (user.isSchoolAdmin() || user.isTeacher());
    }

    public static Boolean midtermTaskCanBeDeleted(MidtermTask midtermTask) {
        return  Boolean.valueOf(midtermTask.getStudentResults().isEmpty());
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

    public static boolean resultIsText(MidtermTaskStudentResultHistory history) {
        return StringUtils.hasText(history.getPointsTxt());
    }
    
    public static boolean isPractice(SubjectStudyPeriod ssp) {
        return Boolean.TRUE.equals(ssp.getSubject().getIsPractice());
    }

    public static void assertNotPractice(SubjectStudyPeriod ssp){
        if(isPractice(ssp)) {
            throw new ValidationFailedException("midtermTask.error.isPractice");
        }
    }

    public static void checkIfStudentResultCanBeDeleted(MidtermTaskStudentResult result) {
        if (result.getHistory() != null && !result.getHistory().isEmpty()) {
            throw new ValidationFailedException("midtermTask.error.studentResultHasHistory");
        }
    }
}
