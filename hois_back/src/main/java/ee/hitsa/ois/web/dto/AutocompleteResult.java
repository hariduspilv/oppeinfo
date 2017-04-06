package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.SaisAdmission;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class AutocompleteResult extends EntityConnectionCommand {

    private final String nameEt;
    private final String nameEn;

    public AutocompleteResult() {
        this(null, null, null);
    }

    public AutocompleteResult(Long id, String nameEt, String nameEn) {
        super(id);
        this.nameEt = nameEt;
        this.nameEn = nameEn;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public static AutocompleteResult of(Curriculum curriculum) {
        return new AutocompleteResult(curriculum.getId(), curriculum.getNameEt(), curriculum.getNameEn());
    }

    public static AutocompleteResult of(CurriculumSpeciality curriculumSpeciality) {
        return new AutocompleteResult(curriculumSpeciality.getId(), curriculumSpeciality.getNameEt(), curriculumSpeciality.getNameEn());
    }

    public static AutocompleteResult of(CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        return new AutocompleteResult(curriculumVersion.getId(), CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEt()), CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEn()));
    }

    public static AutocompleteResult of(DirectiveCoordinator coordinator) {
        return new AutocompleteResult(coordinator.getId(), coordinator.getName(), coordinator.getName());
    }

    public static AutocompleteResult of(SaisAdmission saisAdmission) {
        return new AutocompleteResult(saisAdmission.getId(), saisAdmission.getCode(), saisAdmission.getCode());
    }

    public static AutocompleteResult of(School school) {
        return new AutocompleteResult(school.getId(), school.getNameEt(), school.getNameEn());
    }

    public static AutocompleteResult of(SchoolDepartment schoolDepartment) {
        return new AutocompleteResult(schoolDepartment.getId(), schoolDepartment.getNameEt(), schoolDepartment.getNameEn());
    }

    public static AutocompleteResult of(StateCurriculum stateCurriculum) {
        return new AutocompleteResult(stateCurriculum.getId(), stateCurriculum.getNameEt(), stateCurriculum.getNameEn());
    }

    public static AutocompleteResult of(Student student) {
        Person p = student.getPerson();
        String name = PersonUtil.fullnameAndIdcode(p.getFirstname(), p.getLastname(), p.getIdcode());
        return new AutocompleteResult(student.getId(), name, name);
    }

    public static AutocompleteResult of(StudentGroup studentGroup) {
        return new AutocompleteResult(studentGroup.getId(), studentGroup.getCode(), studentGroup.getCode());
    }

    public static AutocompleteResult of(StudyPeriod studyPeriod) {
        return new AutocompleteResult(studyPeriod.getId(), studyPeriod.getNameEt(), studyPeriod.getNameEn());
    }

    public static AutocompleteResult of(Subject subject) {
        return new AutocompleteResult(subject.getId(), SubjectUtil.subjectName(subject.getCode(), subject.getNameEt(), subject.getCredits()), SubjectUtil.subjectName(subject.getCode(), subject.getNameEn(), subject.getCredits()));
    }

    public static AutocompleteResult of(SubjectAutocompleteResult subject) {
        return new AutocompleteResult(subject.getId(), SubjectUtil.subjectName(subject.getCode(), subject.getNameEt()), SubjectUtil.subjectName(subject.getCode(), subject.getNameEn()));
    }

    public static AutocompleteResult of(Teacher teacher) {
        String name = teacher.getPerson().getFullname();
        return new AutocompleteResult(teacher.getId(), name, name);
    }
}
