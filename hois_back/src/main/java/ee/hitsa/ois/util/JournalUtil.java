package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateBaseDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;

public abstract class JournalUtil {

    public static boolean confirmed(Journal journal) {
        return ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_K, journal.getStatus());
    }

    public static boolean hasPermissionToView(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasPermissionToView(HoisUserDetails user, Journal journal) {
        if (user.isSchoolAdmin() || user.isTeacher()) {
            UserUtil.assertSameSchool(user, journal.getSchool());
        } else if (user.isLeadingTeacher()) {
            UserUtil.throwAccessDeniedIf(!UserUtil.isLeadingTeacher(user, journal));
        } else {
            return false;
        }
        return hasPermissionToView(user);
    }

    private static boolean teacherIsJournalFiller(HoisUserDetails user, Journal journal) {
        Map<Long, JournalTeacher> teachers = StreamUtil.toMap(jt -> EntityUtil.getId(jt.getTeacher()), journal.getJournalTeachers());
        JournalTeacher teacher = teachers.get(user.getTeacherId());
        return teacher != null && Boolean.TRUE.equals(teacher.getIsFiller());
    }

    private static boolean teacherIsJournalConfirmer(HoisUserDetails user, Journal journal) {
        Map<Long, JournalTeacher> teachers = StreamUtil.toMap(jt -> EntityUtil.getId(jt.getTeacher()), journal.getJournalTeachers());
        JournalTeacher teacher = teachers.get(user.getTeacherId());
        return teacher != null && Boolean.TRUE.equals(teacher.getIsConfirmer());
    }

    public static boolean hasPermissionToChange(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdminOrLeadingTeacher(user, journal) ||
                UserUtil.isTeacher(user, journal.getSchool()) && !confirmed(journal) && teacherIsJournalFiller(user, journal)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasPermissionToViewReview(HoisUserDetails user, Journal journal) {
        return ((UserUtil.isSchoolAdmin(user, journal.getSchool()) || UserUtil.isLeadingTeacher(user, journal))
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PAEVIKYLE))
                || UserUtil.isJournalTeacher(user, journal);
    }

    public static boolean hasPermissionToReview(HoisUserDetails user, Journal journal) {
        return UserUtil.isSchoolAdminOrLeadingTeacher(user, journal) &&
                UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PAEVIKYLE);
    }

    public static boolean hasPermissionToConfirm(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdmin(user, journal.getSchool()) || 
                UserUtil.isTeacher(user, journal.getSchool()) && teacherIsJournalConfirmer(user, journal)) &&
                UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PAEVIK);
    }

    public static boolean hasFinalEntry(Journal journal) {
        return journal.getJournalEntries().stream()
                .anyMatch(je -> ClassifierUtil
                        .equals(JournalEntryType.SISSEKANNE_L, je.getEntryType()));
    }

    public static boolean canConfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal) && !confirmed(journal);
    }

    /**
     * Teacher cannot unconfirm, that is why check if user is admin repeated
     */
    public static boolean canUnconfirm(HoisUserDetails user, Journal journal) {
        return hasPermissionToConfirm(user, journal) && UserUtil.isSchoolAdmin(user, journal.getSchool())  && 
                confirmed(journal);
    }

    public static boolean filterJournalEntryStudentsByCurriculumModule(Long curriculumModuleId,
            JournalEntryStudent jes) {
        return jes.getJournalEntry().getJournal().getJournalOccupationModuleThemes().stream()
                .anyMatch(t -> EntityUtil.getId(t.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule())
                .equals(curriculumModuleId));
    }

    /**
     * @return students who are studying and have no final result
     */
    public static List<JournalStudentDto> withoutFinalResult(Journal journal) {
        List<JournalStudent> result = journal.getJournalStudents().stream()
                .filter(js -> StudentUtil.isActive(js.getStudent()))
                .filter(js -> js.getJournalEntryStudents().stream()
                       .filter(jes -> jes.getGrade() != null)
                       .allMatch(jes -> !ClassifierUtil
                       .equals(JournalEntryType.SISSEKANNE_L, jes.getJournalEntry().getEntryType())))
                .collect(Collectors.toList());
        return StreamUtil.toMappedList(JournalStudentDto::of, result);
    }

    public static boolean canRemoveStudent(HoisUserDetails user, Journal journal) {
        return UserUtil.isSchoolAdminOrLeadingTeacher(user, journal.getSchool()) && hasPermissionToChange(user, journal);
    }

    /**
     * Show "confirm all" button for school administrator 2 weeks before the end of study year
     *
     * @param user
     * @param studyYear
     * @return
     */
    public static boolean canConfirmAll(HoisUserDetails user, StudyYear studyYear) {
        return studyYear != null && user.isSchoolAdmin() && UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PAEVIK) &&
                LocalDate.now().plusWeeks(2).isAfter(studyYear.getEndDate());
    }

    public static boolean isFinalResult(JournalEntryByDateBaseDto dto) {
        return JournalEntryType.SISSEKANNE_L.name().equals(dto.getEntryType());
    }

    public static boolean isOutcomeEntryWithoutDate(JournalEntryByDateBaseDto dto) {
        return dto.getOutcomeOrderNr() != null && dto.getEntryDate() == null;
    }

    public static boolean canEditOutcomeGrade(HoisUserDetails user, StudentCurriculumModuleOutcomesResult result) {
        // userrights are checked in hasPermissionToChange method
        if (EntityUtil.getNullableId(result.getApelApplication()) == null) {
            String grade = EntityUtil.getNullableCode(result.getGrade());
            return grade == null || !OccupationalGrade.isPositive(grade) ||
                    (user.isSchoolAdmin() || user.isLeadingTeacher() || (user.isTeacher() && user.getTeacherId()
                            .equals(EntityUtil.getNullableId(result.getGradeInsertedTeacher()))));
        }
        return false;
    }

    public static void assertCanView(HoisUserDetails user) {
        if(!hasPermissionToView(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanView(HoisUserDetails user, Journal journal) {
        if(!hasPermissionToView(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanChange(HoisUserDetails user, Journal journal) {
        if(!hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanConfirm(HoisUserDetails user, Journal journal) {
        if(!canConfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanUnconfirm(HoisUserDetails user, Journal journal) {
        if(!canUnconfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanReview(HoisUserDetails user, Journal journal) {
        if (!hasPermissionToReview(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanRemoveStudent(HoisUserDetails user, Journal journal) {
        if (!canRemoveStudent(user, journal)) {
            throw new ValidationFailedException("journal.messages.removingStudentIsNotAllowed");
        }
    }

    public static void assertCanAddStudent(HoisUserDetails user, Journal journal) {
        if (!hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("journal.messages.addingStudentIsNotAllowed");
        }
    }

    public static void assertCanConfirmAll(HoisUserDetails user, StudyYear studyYear) {
        if(!canConfirmAll(user, studyYear)) {
            throw new ValidationFailedException("journal.messages.confirmAllNotAllowed");
        }
    }

    public static void assertCanEditOutcomeGrade(HoisUserDetails user, StudentCurriculumModuleOutcomesResult result) {
        if(!canEditOutcomeGrade(user, result)) {
            throw new ValidationFailedException("journal.messages.changingOutcomeResultNotAllowed");
        }
    }

    public static void setOutcomeEntriesUnqiueOrderNrs(List<? extends JournalEntryByDateBaseDto> entries) {
        // order outcomes by curriculum module id and their order nr and then give outcomes from different modules a unique outcome order nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateBaseDto::getCurriculumModule, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateBaseDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        List<Long> orderNrs = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            Long entryOrderNr = entries.get(i) != null && entries.get(i).getOutcomeOrderNr() != null ? entries.get(i).getOutcomeOrderNr() : null;
            if (entryOrderNr != null) {
                entries.get(i).setOutcomeOrderNr(uniqueOrderNr(entryOrderNr, orderNrs));
            }
        }
    }

    public static Long uniqueOrderNr(Long entryOrderNr, List<Long> assignedOrderNrs) {
        if (!assignedOrderNrs.contains(entryOrderNr)) {
            assignedOrderNrs.add(entryOrderNr);
            return entryOrderNr;
        }
        Long highestAssignedNr = Collections.max(assignedOrderNrs);
        Long newOrderNr = Long.valueOf(highestAssignedNr.longValue() + 1);
        assignedOrderNrs.add(newOrderNr);
        return newOrderNr;
    }

    public static void orderJournalEntriesByDate(List<? extends JournalEntryByDateBaseDto> entries) {
        // order day entries by lesson nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateBaseDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateBaseDto::getStartLessonNr, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateBaseDto::getLessons, Comparator.nullsFirst(Comparator.naturalOrder())));

        // outcome entries that don't have a date are ordered last among entries without date, all other entries are ordered by date
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateBaseDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateBaseDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder())));

        Collections.sort(entries, (JournalEntryByDateBaseDto o1, JournalEntryByDateBaseDto o2) -> {
            if (isOutcomeEntryWithoutDate(o1) && !isOutcomeEntryWithoutDate(o2)) {
                return 1;
            } else if (!isOutcomeEntryWithoutDate(o1) && isOutcomeEntryWithoutDate(o2)) {
                return -1;
            }
            return 0;
        });

        // put final results to the end of the list
        Collections.sort(entries, (JournalEntryByDateBaseDto o1, JournalEntryByDateBaseDto o2) -> {
            if (isFinalResult(o1) && !isFinalResult(o2)) {
                return 1;
            } else if (!isFinalResult(o1) && isFinalResult(o2)) {
                return -1;
            }
            return 0;
        });
    }

    public static List<CurriculumVersionOccupationModuleTheme> journalCurriculumModuleThemes(Journal journal) {
        return StreamUtil.toMappedList(JournalOccupationModuleTheme::getCurriculumVersionOccupationModuleTheme,
                journal.getJournalOccupationModuleThemes());
    }

    public static boolean allThemesAssessedByOutcomes(Journal journal) {
        return allThemesAssessedByOutcomes(journalCurriculumModuleThemes(journal));
    }

    public static boolean allThemesAssessedByOutcomes(List<CurriculumVersionOccupationModuleTheme> themes) {
        long outcomeBasedAssessmentThemes = themes.stream()
                .filter(t -> Boolean.TRUE.equals(t.getModuleOutcomes())).count();
        return themes.size() == outcomeBasedAssessmentThemes;
    }
}
