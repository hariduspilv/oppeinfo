package ee.hitsa.ois.util;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.SchoolTimetableType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;

public abstract class TimetableUserRights {

    private static boolean canSearchEvents(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_SYNDMUS)
                        || UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PERSYNDMUS));
    }

    public static void assertCanSearchEvents(HoisUserDetails user) {
        if (!canSearchEvents(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canViewEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        TimetableEvent event = eventTime.getTimetableEvent();
        School school = event.getSchool();
        if (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
            return UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school)
                    || (UserUtil.isTeacher(user, school) && !Boolean.TRUE.equals(event.getIsPersonal()));
        } else if (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PERSYNDMUS)) {
            return (UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school))
                    && isPersonalEvent(user, eventTime);
        }
        return false;
    }

    public static void assertCanViewEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        if (!canViewEvent(user, eventTime)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    private static boolean canCreateEvent(HoisUserDetails user, TimetableSingleEventForm form) {
        if (Boolean.TRUE.equals(form.getIsPersonal())) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PERSYNDMUS)
                    && (user.isSchoolAdmin() || user.isLeadingTeacher());
        }

        if (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
            return user.isSchoolAdmin() || user.isLeadingTeacher()
                    || isTeachersEvent(user, StreamUtil.toMappedList(t -> t.getTeacher().getId(), form.getTeachers()));
        }

        return false;
    }

    public static void assertCanCreateEvent(HoisUserDetails user, TimetableSingleEventForm form) {
        if (!canCreateEvent(user, form)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canEditOrDeleteEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        TimetableEvent event = eventTime.getTimetableEvent();
        if (event.getJuhanEventId() != null || (EntityUtil.getNullableId(event.getTimetableObject()) != null
                && eventTime.getStart().isBefore(LocalDateTime.now()))) {
            return false;
        }

        School school = event.getSchool();
        if (Boolean.TRUE.equals(event.getIsPersonal())) {
            return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PERSYNDMUS)
                    && (UserUtil.isSchoolAdmin(user, school)
                            || (UserUtil.isLeadingTeacher(user, school) && isPersonalEvent(user, eventTime)));
        }

        if (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
            return UserUtil.isSchoolAdmin(user, school) || UserUtil.isLeadingTeacher(user, school)
                    || canTeacherEditOrDeleteEvent(user, eventTime);
        }

        return false;
    }

    private static boolean canTeacherEditOrDeleteEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        TimetableEvent event = eventTime.getTimetableEvent();
        if (UserUtil.isTeacher(user, event.getSchool())) {
            boolean isSingleEvent = EntityUtil.getNullableId(event.getTimetableObject()) == null;
            if (isSingleEvent) {
                return user.getTeacherId().equals(EntityUtil.getNullableId(event.getInsertedTeacher()));
            } else {
                return isTeachersEvent(user, StreamUtil.toMappedList(t -> EntityUtil.getId(t.getTeacher()),
                        eventTime.getTimetableEventTeachers()));
            }
        }
        return false;
    }

    private static boolean isPersonalEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        return user.getPersonId().equals(EntityUtil.getNullableId(eventTime.getTimetableEvent().getPerson()));
    }

    public static void assertCanEditOrDeleteEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        if (!canEditOrDeleteEvent(user, eventTime)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean isTeachersEvent(HoisUserDetails user, List<Long> teacherIds) {
        if (!user.isTeacher()) {
            return false;
        }
        if (teacherIds != null && teacherIds.contains(user.getTeacherId())) {
            return true;
        }
        return false;
    }
    
    /**
     * Check if user can import or export timetable
     * 
     * @param user - authenticated user
     * @param school - user's school
     * @return true if the user can import or export timetable
     */
    public static boolean canImportOrExportTimetable(HoisUserDetails user, School school) {
        return user.isSchoolAdmin() && ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_UNTIS, SchoolTimetableType.TIMETABLE_ASC);
    }
    
    /**
     * Check if an user can import or export timetable. If he/she cannot export then it throws an error
     * 
     * @param user - authenticated user
     * @param school - user's school
     * @throws ValidationFailedException
     */
    public static void assertCanImportOrExportTimetable(HoisUserDetails user, School school) {
        ValidationFailedException.throwIf(!canImportOrExportTimetable(user, school), "main.messages.error.nopermission");
    }
}
