package ee.hitsa.ois.web.dto;

import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
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
import ee.hitsa.ois.domain.poll.PollJournal;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTimeBuilding;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EnterpriseUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;

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

    public static AutocompleteResult of(Building building) {
        String name = building.getCode() + " - " + building.getName();
        return new AutocompleteResult(building.getId(), name, name);
    }

    public static AutocompleteResult of(Curriculum curriculum) {
        return new CurriculumResult(curriculum.getId(), curriculum.getNameEt(), curriculum.getNameEn(),
                curriculum.getCode());
    }

    public static AutocompleteResult of(CurriculumGrade grade) {
        return new AutocompleteResult(grade.getId(), grade.getNameEt(), grade.getNameEn());
    }

    public static AutocompleteResult of(CurriculumModule curriculumModule) {
        return new AutocompleteResult(curriculumModule.getId(),
                CurriculumUtil.moduleName(curriculumModule.getNameEt(), curriculumModule.getModule().getNameEt(), curriculumModule.getCurriculum().getCode()),
                CurriculumUtil.moduleName(curriculumModule.getNameEn(), curriculumModule.getModule().getNameEn(), curriculumModule.getCurriculum().getCode()));
    }

    public static CurriculumModuleOutcomeResult of(CurriculumModuleOutcome outcome) {
        return new CurriculumModuleOutcomeResult(outcome.getId(), outcome.getOutcomeEt(), outcome.getOutcomeEt(), outcome.getOrderNr());
    }

    public static AutocompleteResult of(CurriculumSpeciality curriculumSpeciality) {
        return new AutocompleteResult(curriculumSpeciality.getId(), curriculumSpeciality);
    }

    public static CurriculumVersionResult of(CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        return new CurriculumVersionResult(curriculumVersion.getId(),
                CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEt()),
                CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEn()),
                curriculum.getId(), null, null, Boolean.valueOf(CurriculumUtil.isVocational(curriculum)));
    }

    public static AutocompleteResult of(CurriculumVersionHigherModule module) {
        return new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn());
    }

    public static AutocompleteResult of(CurriculumVersionOccupationModule omodule) {
        return curriculumVersionOccupationModuleResult(omodule, true);
    }

    public static AutocompleteResult of(CurriculumVersionOccupationModule omodule,
            boolean includeCurriculumVersionCode) {
        return curriculumVersionOccupationModuleResult(omodule, includeCurriculumVersionCode);
    }

    private static AutocompleteResult curriculumVersionOccupationModuleResult(CurriculumVersionOccupationModule omodule,
            boolean includeCurriculumVersionCode) {
        CurriculumModule curriculumModule = omodule.getCurriculumModule();
        Classifier moduleCode = curriculumModule.getModule();
        if (includeCurriculumVersionCode) {
            String nameEt = CurriculumUtil.moduleName(curriculumModule.getNameEt(), moduleCode.getNameEt(),
                    omodule.getCurriculumVersion().getCode());
            String nameEn = CurriculumUtil.moduleName(curriculumModule.getNameEn(), moduleCode.getNameEn(),
                    omodule.getCurriculumVersion().getCode());
            return new AutocompleteResult(omodule.getId(), nameEt, nameEn);
        }
        String nameEt = CurriculumUtil.moduleName(curriculumModule.getNameEt(), moduleCode.getNameEt());
        String nameEn = CurriculumUtil.moduleName(curriculumModule.getNameEn(), moduleCode.getNameEn());
        return new AutocompleteResult(omodule.getId(), nameEt, nameEn);
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
        Person p = student.getPerson();
        String name = PersonUtil.fullnameAndIdcode(p.getFirstname(), p.getLastname(), p.getIdcode());
        return new AutocompleteResult(student.getId(), name, name);
    }

    public static AutocompleteResult of(StudentGroup studentGroup) {
        String code = studentGroup.getCode();
        return new AutocompleteResult(studentGroup.getId(), code, code);
    }
    
    public static AutocompleteResult of(PollJournal pollJournal) {
        Journal journal = pollJournal.getJournal();
        Set<String> studentGroups = journal.getJournalOccupationModuleThemes().stream()
                .filter(js -> js.getLessonPlanModule() != null && js.getLessonPlanModule().getLessonPlan() != null &&
                js.getLessonPlanModule().getLessonPlan().getStudentGroup() != null && js.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode() != null)
                .filter(StreamUtil.distinctByKey(js -> js.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode()))
                .map(js -> js.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode())
                .collect(Collectors.toSet());
        String journalStudentGroups = null;
        if (studentGroups != null) {
            journalStudentGroups = String.join(",", studentGroups);
        }
        String journalAndStudentGroups = journal.getNameEt() +
                ((journalStudentGroups == null || "".equals(journalStudentGroups)) ? "" : " (" + journalStudentGroups + ")");
        AutocompleteResult dto = new AutocompleteResult(journal.getId(), journalAndStudentGroups, journalAndStudentGroups);
        return dto;
    }
    
    public static AutocompleteResult of(PracticeEvaluation evaluation) {
        String name = evaluation.getNameEt();
        return new AutocompleteResult(evaluation.getId(), name, name);
    }

    public static AutocompleteResult of(StudyMaterial studyMaterial) {
        String name = studyMaterial.getNameEt();
        return new AutocompleteResult(studyMaterial.getId(), name, name);
    }

    public static AutocompleteResult of(StudyPeriod studyPeriod) {
        return new AutocompleteResult(studyPeriod.getId(), studyPeriod);
    }
    
    public static AutocompleteResult ofWithYear(StudyPeriod studyPeriod) {
        return new AutocompleteResult(studyPeriod.getId(), studyPeriod.getStudyYear().getYear().getNameEt() + " " + studyPeriod.getNameEt(),
                studyPeriod.getStudyYear().getYear().getNameEn() + " " + studyPeriod.getNameEn());
    }

    public static AutocompleteResult of(StudyYear studyYear) {
        return new AutocompleteResult(studyYear.getId(), studyYear.getYear());
    }

    public static AutocompleteResult of(Subject subject) {
        return new AutocompleteResult(subject.getId(), SubjectUtil.subjectName(subject.getCode(), subject.getNameEt(), subject.getCredits()), SubjectUtil.subjectName(subject.getCode(), subject.getNameEn(), subject.getCredits()));
    }

    public static AutocompleteResult of(SubjectSearchDto subject) {
        return new AutocompleteResult(subject.getId(), SubjectUtil.subjectName(subject.getCode(), subject.getNameEt(), subject.getCredits()), SubjectUtil.subjectName(subject.getCode(), subject.getNameEn(), subject.getCredits()));
    }
    
    public static AutocompleteResult of(SubjectProgram program) {
        // TODO: What should be a name for Subject Program?
        return new SubjectProgramResult(program.getId(), null, null, program.getSubjectStudyPeriodTeacher().getTeacher().getPerson().getFullname(),
                program.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getStudyPeriod(), program.getStatus().getCode());
    }

    public static AutocompleteResult of(Teacher teacher) {
        String name = teacher.getPerson().getFullname();
        return new AutocompleteResult(teacher.getId(), name, name);
    }

    public static AutocompleteResult of(TeacherOccupation teacherOccupation) {
        return new AutocompleteResult(teacherOccupation.getId(), teacherOccupation.getOccupationEt(), teacherOccupation.getOccupationEn());
    }

    public static AutocompleteResult of(CurriculumVersionOccupationModuleTheme theme) {
        return new AutocompleteResult(EntityUtil.getId(theme), theme.getNameEt(), theme.getNameEt());
    }

    public static AutocompleteResult of(Journal journal) {
        return new AutocompleteResult(journal.getId(), journal.getNameEt(), journal.getNameEt());
    }

    public static AutocompleteResult of(Committee committee) {
        return new AutocompleteResult(committee.getId(), committee.getNameEt(), committee.getNameEt());
    }
    
    public static AutocompleteResult of(Committee committee, boolean full) {
        if (full) {
            String name = committee.getNameEt() == null ? "-" : committee.getNameEt();
            String members = StreamUtil.nullSafeList(committee.getMembers()).stream()
                    .filter(member -> member.getPerson() != null)
                    .map(member -> member.getPerson().getFullname())
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
}
