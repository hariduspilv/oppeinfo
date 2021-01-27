package ee.hitsa.ois.web.dto;

import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserCurriculum;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EnterpriseUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramResult;

public class AutocompleteResult extends EntityConnectionCommand implements Translatable {

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

    public AutocompleteResult(Long id, Translatable data) {
        this(id, data.getNameEt(), data.getNameEn());
    }

    @Override 
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null || id == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return id.equals(((AutocompleteResult) obj).id);
    }

    @Override
    public int hashCode() {
        return this.id == null ? 31 : this.id.hashCode();
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public static AutocompleteResult of(BaseModule baseModule) {
        return new AutocompleteResult(baseModule.getId(), baseModule.getNameEt(), baseModule.getNameEn());
    }

    public static AutocompleteResult of(BaseModuleOutcomes outcome) {
        return new AutocompleteResult(outcome.getId(), outcome.getOutcomeEt(), outcome.getOutcomeEn());
    }

    public static AutocompleteResult of(Room room) {
        String code = room.getBuilding().getCode() + "-" + room.getCode();
        return new AutocompleteResult(room.getId(), code, code);
    }

    public static AutocompleteResult of(Building building) {
        String name = building.getCode() + " - " + building.getName();
        return new AutocompleteResult(building.getId(), name, name);
    }

    public static AutocompleteResult of(Curriculum curriculum) {
        if (curriculum == null) return null;
        return new CurriculumResult(curriculum.getId(), curriculum.getNameEt(), curriculum.getNameEn(),
                curriculum.getCode());
    }

    public static AutocompleteResult curriculumWithMerCode(Curriculum curriculum) {
        return new CurriculumResult(curriculum.getId(), curriculum.getNameEt(), curriculum.getNameEn(),
                curriculum.getCode(), curriculum.getMerCode());
    }

    public static AutocompleteResult of(CurriculumGrade grade) {
        return new AutocompleteResult(grade.getId(), grade.getNameEt(), grade.getNameEn());
    }

    public static AutocompleteResult of(CurriculumModule curriculumModule) {
        Classifier moduleType = curriculumModule.getModule();
        String curriculumCode = curriculumModule.getCurriculum().getCode();
        return curriculumModuleResult(curriculumModule.getId(), curriculumModule.getNameEt(),
                curriculumModule.getNameEn(), moduleType.getNameEt(), moduleType.getNameEn(), curriculumCode);
    }

    public static AutocompleteResult curriculumModuleResult(Long id, String nameEt, String nameEn, String moduleTypeEt,
            String moduleTypeEn, String curriculumCode) {
        return new AutocompleteResult(id, CurriculumUtil.moduleName(nameEt, moduleTypeEt, curriculumCode),
                CurriculumUtil.moduleName(nameEn != null ? nameEn : nameEt, moduleTypeEn, curriculumCode));
    }

    public static CurriculumModuleOutcomeResult of(CurriculumModuleOutcome outcome) {
        return new CurriculumModuleOutcomeResult(outcome.getId(), outcome.getOutcomeEt(), outcome.getOutcomeEn(),
                outcome.getOrderNr());
    }

    public static AutocompleteResult of(CurriculumSpeciality curriculumSpeciality) {
        return new AutocompleteResult(curriculumSpeciality.getId(), curriculumSpeciality);
    }

    public static CurriculumVersionResult of(CurriculumVersion curriculumVersion) {
        if (curriculumVersion == null) return null;
        Curriculum curriculum = curriculumVersion.getCurriculum();
        return new CurriculumVersionResult(curriculumVersion.getId(),
                CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEt()),
                CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEn()),
                curriculum.getId(), null, null, Boolean.valueOf(CurriculumUtil.isVocational(curriculum)));
    }

    public static AutocompleteResult of(CurriculumVersionHigherModule module) {
        return new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn());
    }

    public static CurriculumVersionOccupationModuleResult of(CurriculumVersionOccupationModule omodule) {
        return curriculumVersionOccupationModuleResult(omodule, true);
    }

    public static CurriculumVersionOccupationModuleResult of(CurriculumVersionOccupationModule omodule,
            boolean includeCurriculumVersionCode) {
        return curriculumVersionOccupationModuleResult(omodule, includeCurriculumVersionCode);
    }

    private static CurriculumVersionOccupationModuleResult curriculumVersionOccupationModuleResult(
            CurriculumVersionOccupationModule omodule, boolean includeCurriculumVersionCode) {
        CurriculumModule curriculumModule = omodule.getCurriculumModule();
        Classifier moduleCode = curriculumModule.getModule();
        String curriculumVersionCode = includeCurriculumVersionCode ? omodule.getCurriculumVersion().getCode() : null;
        CurriculumVersionOccupationModuleResult result = curriculumVersionOccupationModuleResult(omodule.getId(),
                curriculumModule.getNameEt(), curriculumModule.getNameEn(), moduleCode.getNameEt(),
                moduleCode.getNameEn(), curriculumVersionCode);
        result.setCurriculumModuleId(curriculumModule.getId());
        return result;
    }

    public static CurriculumVersionOccupationModuleResult curriculumVersionOccupationModuleResult(Long id,
            String moduleNameEt, String moduleNameEn, String moduleCodeNameEt, String moduleCodeNameEn,
            String curriculumCode) {
        String nameEt = CurriculumUtil.moduleName(moduleNameEt, moduleCodeNameEt, curriculumCode);
        String nameEn = CurriculumUtil.moduleName(moduleNameEn != null ? moduleNameEn : moduleNameEt, moduleCodeNameEn,
                curriculumCode);
        return new CurriculumVersionOccupationModuleResult(id, nameEt, nameEn);
    }

    public static AutocompleteResult of(Directive directive) {
        return new AutocompleteResult(directive.getId(), directive.getHeadline(), null);
    }

    public static AutocompleteResult of(DirectiveCoordinator coordinator) {
        String name = coordinator.getName();
        return new AutocompleteResult(coordinator.getId(), name, name);
    }

    public static AutocompleteResult of(Enterprise enterprise) {
        String name = EnterpriseUtil.getName(enterprise);
        return new AutocompleteResult(enterprise.getId(), name, name);
    }

    public static AutocompleteResult of(LessonTimeBuilding lessonTimeBuilding) {
        return of(lessonTimeBuilding.getBuilding());
    }

    public static AutocompleteResult of(MidtermTask midtermTask) {
        return new AutocompleteResult(midtermTask.getId(), midtermTask.getNameEt(), midtermTask.getNameEn());
    }

    public static AutocompleteResult of(Person person) {
        String name = person.getFullname();
        return new AutocompleteResult(person.getId(), name, name);
    }

    public static AutocompleteResult of(SaisAdmission saisAdmission) {
        String code = saisAdmission.getCode();
        return new AutocompleteResult(saisAdmission.getId(), code, code);
    }

    public static AutocompleteResult of(School school) {
        return new AutocompleteResult(school.getId(), school);
    }

    public static AutocompleteResult of(SchoolDepartment schoolDepartment) {
        return new AutocompleteResult(schoolDepartment.getId(), schoolDepartment);
    }

    public static AutocompleteResult of(StateCurriculum stateCurriculum) {
        return new AutocompleteResult(stateCurriculum.getId(), stateCurriculum);
    }

    public static AutocompleteResult of(Student student) {
        return of(student, true);
    }

    public static AutocompleteResult of(Student student, boolean addIdcode) {
        Person p = student.getPerson();
        String name = addIdcode ? PersonUtil.fullnameAndIdcode(p.getFirstname(), p.getLastname(), p.getIdcode()) : PersonUtil.fullname(p);
        return new AutocompleteResult(student.getId(), name, name);
    }

    public static AutocompleteResult of(StudentGroup studentGroup) {
        String code = studentGroup.getCode();
        return new AutocompleteResult(studentGroup.getId(), code, code);
    }

    public static AutocompleteResult of(PracticeEvaluation evaluation) {
        return new AutocompleteResult(evaluation.getId(), evaluation.getNameEt(), null);
    }

    public static AutocompleteResult of(StudyMaterial studyMaterial) {
        return new AutocompleteResult(studyMaterial.getId(), studyMaterial.getNameEt(), null);
    }

    public static AutocompleteResult of(StudyPeriod studyPeriod) {
        return new AutocompleteResult(studyPeriod.getId(), studyPeriod);
    }

    public static AutocompleteResult ofWithYear(StudyPeriod studyPeriod) {
        Classifier yearClassifier = studyPeriod.getStudyYear().getYear();
        String yearNameEt = yearClassifier.getNameEt();
        String yearNameEn = TranslateUtil.getNonNullableNameEn(yearClassifier);
        String periodNameEt = studyPeriod.getNameEt();
        String periodNameEn = TranslateUtil.getNonNullableNameEn(studyPeriod);

        return new AutocompleteResult(studyPeriod.getId(), yearNameEt + " " + periodNameEt,
                yearNameEn + " " + periodNameEn);
    }

    public static AutocompleteResult of(StudyYear studyYear) {
        return new AutocompleteResult(studyYear.getId(), studyYear.getYear());
    }

    public static AutocompleteResult of(Subject subject) {
        return new AutocompleteResult(subject.getId(),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEt(), subject.getCredits()),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEn(), subject.getCredits()));
    }
    
    public static AutocompleteResult of(Subject subject, boolean withCredits) {
        if (withCredits) return AutocompleteResult.of(subject);
        return new AutocompleteResult(subject.getId(),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEt()),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEn()));
    }

    public static AutocompleteResult of(SubjectSearchDto subject) {
        return new AutocompleteResult(subject.getId(),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEt(), subject.getCredits()),
                SubjectUtil.subjectName(subject.getCode(), subject.getNameEn(), subject.getCredits()));
    }

    public static AutocompleteResult of(SubjectProgram program) {
        return new SubjectProgramResult(program.getId(), null, null,
                program.getTeachers().stream()
                        .map(SubjectProgramTeacher::getSubjectStudyPeriodTeacher)
                        .map(SubjectStudyPeriodTeacher::getTeacher)
                        .map(Teacher::getPerson)
                        .sorted(PersonUtil.SORT)
                        .map(Person::getFullname)
                        .collect(Collectors.joining(", ")),
                program.getSubjectStudyPeriod().getStudyPeriod(),
                program.getStatus().getCode());
    }

    public static AutocompleteResult of(SubjectStudyPeriodSubgroup subgroup) {
        return of(subgroup, true);
    }
    
    public static AutocompleteResult of(SubjectStudyPeriodSubgroup subgroup, boolean includeTeacher) {
        if (!includeTeacher || subgroup.getTeacher() == null) {
            return new AutocompleteResult(subgroup.getId(), subgroup.getCode(), subgroup.getCode());
        }
        String name = String.format("%s (%s)", subgroup.getCode(), PersonUtil.fullname(subgroup.getTeacher().getTeacher().getPerson()));
        return new AutocompleteResult(subgroup.getId(), name, name);
    }

    public static AutocompleteResult of(Teacher teacher) {
        String name = teacher.getPerson().getFullname();
        return new AutocompleteResult(teacher.getId(), name, name);
    }

    public static AutocompleteResult of(TeacherOccupation teacherOccupation) {
        return new AutocompleteResult(teacherOccupation.getId(), teacherOccupation.getOccupationEt(),
                teacherOccupation.getOccupationEn());
    }

    public static AutocompleteResult of(CurriculumVersionOccupationModuleTheme theme) {
        return new AutocompleteResult(theme.getId(), theme.getNameEt(), theme.getNameEt());
    }

    public static AutocompleteResult of(Journal journal) {
        return new AutocompleteResult(journal.getId(), journal.getNameEt(), null);
    }

    public static AutocompleteResult of(Committee committee) {
        return new AutocompleteResult(committee.getId(), committee.getNameEt(), null);
    }

    public static AutocompleteResult of(Committee committee, boolean full) {
        if (full) {
            String name = committee.getNameEt() == null ? "-" : committee.getNameEt();
            String members = StreamUtil.nullSafeList(committee.getMembers()).stream()
                    .filter(member -> member.getMemberName() != null || member.getPerson() != null)
                    .map(member -> member.getPerson() != null ? member.getPerson().getFullname() : member.getMemberName())
                    .sorted()
                    .collect(Collectors.joining(", "));
            String fullName;
            if (members != null) {
                fullName = String.format("%s (%s)", name, members);
            } else {
                fullName = name;
            }
            return new AutocompleteResult(committee.getId(), fullName, fullName);
        }
        return of(committee);
    }

    public static AutocompleteResult of(Poll poll) {
        return new AutocompleteResult(poll.getId(), poll.getNameEt(), poll.getNameEn());
    }

    public static AutocompleteResult of(PollTheme theme) {
        return new AutocompleteResult(theme.getId(), theme.getNameEt(), theme.getNameEn());
    }

    public static AutocompleteResult of(ApelSchool apelSchool) {
        if (apelSchool == null) {
            return null;
        }
        return new AutocompleteResult(apelSchool.getId(), apelSchool.getNameEt(), apelSchool.getNameEn());
    }

    public static AutocompleteResult of(User user) {
        String name = user.getPerson().getFullname();
        return new AutocompleteResult(user.getId(), name, name);
    }

    public static AutocompleteResult of(UserSchoolRole role) {
        return new AutocompleteResult(role.getId(), role.getNameEt(), role.getNameEn());
    }

    public static AutocompleteResult of(UserCurriculum userCurriculum) {
        String merCode = userCurriculum.getCurriculum().getMerCode();
        String code = userCurriculum.getCurriculum().getCode();
        return new AutocompleteResult(userCurriculum.getId(),
                CurriculumUtil.curriculumName(merCode, code, userCurriculum.getCurriculum().getNameEt()),
                CurriculumUtil.curriculumName(merCode, code, userCurriculum.getCurriculum().getNameEn()));
    }
}
