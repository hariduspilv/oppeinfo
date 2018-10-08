package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.moodle.MoodleContext;
import ee.hitsa.ois.service.moodle.MoodleService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodTeacherForm;

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
    private MoodleService moodleService;

    public SubjectStudyPeriod create(SubjectStudyPeriodForm form, MoodleContext context) {
        SubjectStudyPeriod subjectStudyPeriod = new SubjectStudyPeriod();
        subjectStudyPeriod.setSubject(em.getReference(Subject.class, form.getSubject()));
        subjectStudyPeriod.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return update(subjectStudyPeriod, form, context);
    }

    public SubjectStudyPeriod update(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriodForm form,
            MoodleContext context) {
        EntityUtil.bindToEntity(form, subjectStudyPeriod, classifierRepository, "subject", "studyPeriod", "teachers",
                "studentGroups");
        updateSubjectStudyPeriodTeachers(subjectStudyPeriod, form);
        updateStudentGroups(subjectStudyPeriod, form.getStudentGroups());
        if (subjectStudyPeriod.getMoodleCourseId() != null) {
            moodleService.validateMoodleCourseId(context, subjectStudyPeriod, subjectStudyPeriod.getMoodleCourseId());
        }
        subjectStudyPeriod = EntityUtil.save(subjectStudyPeriod, em);
        subjectStudyPeriodDeclarationService.addToDeclarations(subjectStudyPeriod);
        return subjectStudyPeriod;
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

    private void updateStudentGroups(SubjectStudyPeriod subjectStudyPeriod, List<Long> newStudentGroupIds) {
        Map<Long, SubjectStudyPeriodStudentGroup> oldStudentGroupsMap = StreamUtil.toMap(t -> EntityUtil.getId(t.getStudentGroup()),
                subjectStudyPeriod.getStudentGroups());
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
