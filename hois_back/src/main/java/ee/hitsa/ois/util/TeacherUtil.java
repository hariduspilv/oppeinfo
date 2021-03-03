package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.exception.AssertionFailedException;

import java.time.LocalDateTime;

public abstract class TeacherUtil {

    public static void assertContinuingEducationBelongsToTeacher(TeacherContinuingEducation continuingEducation, Teacher teacher) {
        AssertionFailedException.throwIf(!EntityUtil.getId(teacher).equals(EntityUtil.getId(continuingEducation.getTeacher())), "Teacher and continuing education don't match");
    }

    public static void assertMobilityBelongsToTeacher(TeacherMobility teacherMobility, Teacher teacher) {
        AssertionFailedException.throwIf(!EntityUtil.getId(teacher).equals(EntityUtil.getId(teacherMobility.getTeacher())), "Teacher and mobility don't match");
    }

    public static void assertQualificationBelongsToTeacher(TeacherQualification qualification, Teacher teacher) {
        AssertionFailedException.throwIf(!EntityUtil.getId(teacher).equals(EntityUtil.getId(qualification.getTeacher())), "Teacher and qualification don't match");
    }

    public static void assertEhisPositionBelongsToTeacher(TeacherPositionEhis teacherPositionEhis, Teacher teacher) {
        AssertionFailedException.throwIf(!EntityUtil.getId(teacher).equals(EntityUtil.getId(teacherPositionEhis.getTeacher())), "Teacher and ehis position don't match");
    }

    public static boolean canSendTeacherPosition(TeacherPositionEhis teacherPositionEhis, Teacher teacher, boolean higher) {
        LocalDateTime ehisSentDt = higher ? teacher.getEhisSentHdt() : teacher.getEhisSentVdt();
        return ehisSentDt == null ||
                ehisSentDt.isBefore(teacherPositionEhis.getChanged() != null
                        ? teacherPositionEhis.getChanged()
                        : teacherPositionEhis.getInserted());
    }

    public static boolean canSendTeacherContinuingEducation(TeacherContinuingEducation education, Teacher teacher, boolean higher) {
        LocalDateTime ehisSentDt = higher ? teacher.getEhisSentHdt() : teacher.getEhisSentVdt();
        return ehisSentDt == null || ehisSentDt.isBefore(education.getInserted());
    }

    public static boolean canSendTeacherQualification(TeacherQualification qualification, Teacher teacher, boolean higher) {
        LocalDateTime ehisSentDt = higher ? teacher.getEhisSentHdt() : teacher.getEhisSentVdt();
        return ehisSentDt == null ||
                ehisSentDt.isBefore(qualification.getChanged() != null
                        ? qualification.getChanged()
                        : qualification.getInserted());
    }
}
