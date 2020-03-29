package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.moodle.MoodleContext;
import ee.hitsa.ois.service.moodle.MoodleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodTeacherForm;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupForm;

@Transactional
@Service
public class SubjectStudyPeriodService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SubjectStudyPeriodDeclarationService subjectStudyPeriodDeclarationService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private MoodleService moodleService;

    public SubjectStudyPeriodDto get(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodDto dto = SubjectStudyPeriodDto.of(subjectStudyPeriod);
        dto.setCanUpdate(Boolean.valueOf(SubjectStudyPeriodUtil.canUpdate(user, subjectStudyPeriod,
                studyYearService.getCurrentStudyPeriod(user.getSchoolId()))));
        dto.setCanDelete(Boolean.valueOf(SubjectStudyPeriodUtil.canDelete(user, subjectStudyPeriod)));
        return dto;
    }

    public SubjectStudyPeriod create(HoisUserDetails user, SubjectStudyPeriodForm form, MoodleContext context) {
        SubjectStudyPeriod subjectStudyPeriod = new SubjectStudyPeriod();
        subjectStudyPeriod.setSubject(em.getReference(Subject.class, form.getSubject()));
        subjectStudyPeriod.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return update(user, subjectStudyPeriod, form, context);
    }

    public SubjectStudyPeriod update(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod,
            SubjectStudyPeriodForm form, MoodleContext context) {
        EntityUtil.bindToEntity(form, subjectStudyPeriod, classifierRepository, "subject", "studyPeriod", "teachers",
                "studentGroups", "subgroups");
        if (user.isSchoolAdmin()) {
            updateSubjectStudyPeriodTeachers(subjectStudyPeriod, form);
            updateStudentGroups(subjectStudyPeriod, form.getStudentGroups());
        }
        updateSubgroups(subjectStudyPeriod, form.getSubgroups());
        if (!subjectStudyPeriod.getSubgroups().isEmpty()) {
            LinkedList<SubjectStudyPeriodSubgroup> sortedSubgroupsByCode = new LinkedList<>(subjectStudyPeriod.getSubgroups().stream()
                    .sorted(SubjectStudyPeriodUtil.COMPARATOR_SUBGROUP)
                    .collect(Collectors.toList()));
            
            SubjectStudyPeriodSubgroup lastSubgroup = sortedSubgroupsByCode.peekLast();
            SubjectStudyPeriodSubgroup subgroup = sortedSubgroupsByCode.pollFirst();
            
            for (int i = 0; i < subjectStudyPeriod.getDeclarationSubjects().size(); i++) {
                DeclarationSubject ds = subjectStudyPeriod.getDeclarationSubjects().get(i);
                
                if (ds.getSubgroup() != null) {
                    continue;
                }
                
                while (subgroup != null && subgroup.getDeclarationSubjects().size() >= subgroup.getPlaces().intValue()) {
                    subgroup = sortedSubgroupsByCode.pollFirst();
                }
                
                if (subgroup == null) {
                    lastSubgroup.addDeclarationSubject(ds);
                } else {
                    subgroup.addDeclarationSubject(ds);
                }
            }
        }
        if (subjectStudyPeriod.getMoodleCourseId() != null) {
            moodleService.validateMoodleCourseId(context, subjectStudyPeriod, subjectStudyPeriod.getMoodleCourseId());
        }
        subjectStudyPeriod = EntityUtil.save(subjectStudyPeriod, em);
        subjectStudyPeriodDeclarationService.addToDeclarations(subjectStudyPeriod);
        return subjectStudyPeriod;
    }

    private static void updateSubgroups(SubjectStudyPeriod subjectStudyPeriod, List<SubjectStudyPeriodSubgroupForm> subgroups) {
        Map<Long, SubjectStudyPeriodTeacher> ssptByTeacherId = subjectStudyPeriod.getTeachers().stream()
                .collect(Collectors.toMap(sspt -> sspt.getTeacher().getId(), sspt -> sspt, (o, n) -> o));
        EntityUtil.bindEntityCollection(subjectStudyPeriod.getSubgroups(), SubjectStudyPeriodSubgroup::getId,
                subgroups, SubjectStudyPeriodSubgroupForm::getId,
                form -> {
                    SubjectStudyPeriodSubgroup subgroup = new SubjectStudyPeriodSubgroup();
                    subgroup.setCode(form.getCode());
                    subgroup.setPeriod(subjectStudyPeriod);
                    subgroup.setTeacher(form.getTeacher() != null ? ssptByTeacherId.get(form.getTeacher().getId()) : null);
                    subgroup.setPlaces(form.getPlaces());
                    return subgroup;
                }, (form, subgroup) -> {
                    subgroup.setCode(form.getCode());
                    subgroup.setPlaces(form.getPlaces());
                    subgroup.setTeacher(form.getTeacher() != null ? ssptByTeacherId.get(form.getTeacher().getId()) : null);
                },
                removedSubgroup -> {
                    ValidationFailedException.throwIf(!removedSubgroup.getDeclarationSubjects().isEmpty(), "You cannot delete subgroup which has declaration subjects");
                    if (!removedSubgroup.getTimetableEventSubgroups().isEmpty()) {
                        throw new EntityRemoveException(null, null);
                    }
                });
    }

    public void updateSubjectStudyPeriodTeachers(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriodForm form) {
        Map<Long, SubjectStudyPeriodTeacher> oldTeachersMap = StreamUtil.toMap(t -> EntityUtil.getId(t.getTeacher()),
                subjectStudyPeriod.getTeachers());
        List<SubjectStudyPeriodTeacher> newTeachers = new ArrayList<>();
        for (SubjectStudyPeriodTeacherForm t : form.getTeachers()) {
            SubjectStudyPeriodTeacher teacher = oldTeachersMap.get(t.getTeacherId());
            if (teacher == null) {
                teacher = new SubjectStudyPeriodTeacher();
                teacher.setSubjectStudyPeriod(subjectStudyPeriod);
                teacher.setTeacher(em.getReference(Teacher.class, t.getTeacherId()));
            }
            teacher.setIsSignatory(t.getIsSignatory());
            newTeachers.add(teacher);
        }
        subjectStudyPeriod.setTeachers(newTeachers);
    }

    /**
     * There was a problem when there could be duplicates in list subjectStudyPeriod.getStudentGroups()
     * 
     * @param subjectStudyPeriod
     * @param newStudentGroupIds
     */
    private void updateStudentGroups(SubjectStudyPeriod subjectStudyPeriod, List<Long> newStudentGroupIds) { 
        Map<Long, SubjectStudyPeriodStudentGroup> oldStudentGroupsMap = StreamUtil
                .toMap(t -> EntityUtil.getId(t.getStudentGroup()), subjectStudyPeriod.getStudentGroups().stream()
                        .filter(StreamUtil.distinctByKey(con -> EntityUtil.getId(con.getStudentGroup()))));
        List<SubjectStudyPeriodStudentGroup> newStudentGroups = new ArrayList<>();
        for (Long studentGroupId : newStudentGroupIds) {
            SubjectStudyPeriodStudentGroup studentGroup = oldStudentGroupsMap.get(studentGroupId);
            if (studentGroup == null) {
                studentGroup = new SubjectStudyPeriodStudentGroup();
                studentGroup.setSubjectStudyPeriod(subjectStudyPeriod);
                studentGroup.setStudentGroup(em.getReference(StudentGroup.class, studentGroupId));
            }
            newStudentGroups.add(studentGroup);
        }
        subjectStudyPeriod.setStudentGroups(newStudentGroups);
    }

    public void delete(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        // See SubjectStudyPeriod.java for explanation
        if(!subjectStudyPeriod.getMidtermTasks().isEmpty()) {
            throw new ValidationFailedException("main.messages.record.referenced");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(subjectStudyPeriod, em);
    }

}
