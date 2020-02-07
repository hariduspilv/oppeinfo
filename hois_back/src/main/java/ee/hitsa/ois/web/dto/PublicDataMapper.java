package ee.hitsa.ois.web.dto;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.BaseLog;
import ee.hitsa.ois.domain.BaseTask;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyForm;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectLanguage;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.CurriculumVersionYearCapacitiesUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.util.TranslateUtil;

public class PublicDataMapper {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<String, Function<Curriculum, ?>> CURRICULUM = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumJointPartner, ?>> CURRICULUM_JOINT_PARTNER = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumModule, ?>> CURRICULUM_MODULE = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumOccupation, ?>> CURRICULUM_OCCUPATION = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumSpeciality, ?>> CURRICULUM_SPECIALITY = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersion, ?>> CURRICULUM_VERSION = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionElectiveModule, ?>> CURRICULUM_VERSION_ELECTIVE_MODULE = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionHigherModule, ?>> CURRICULUM_VERSION_HIGHER_MODULE = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionHigherModuleSubject, ?>> CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionOccupationModule, ?>> CURRICULUM_VERSION_OCCUPATION_MODULE = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionOccupationModuleCapacity, ?>> CURRICULUM_VERSION_OCCUPATION_MODULE_CAPACITY = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionOccupationModuleTheme, ?>> CURRICULUM_VERSION_OCCUPATION_MODULE_THEME = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionOccupationModuleThemeCapacity, ?>> CURRICULUM_VERSION_OCCUPATION_MODULE_THEME_CAPACITY = new LinkedHashMap<>();
    private static final Map<String, Function<CurriculumVersionOccupationModuleYearCapacity, ?>> CURRICULUM_VERSION_OCCUPATION_MODULE_YEAR_CAPACITY = new LinkedHashMap<>();
    private static final Map<String, Function<Subject, ?>> SUBJECT = new LinkedHashMap<>();
    private static final Map<String, Function<SubjectProgram, ?>> SUBJECT_PROGRAM = new LinkedHashMap<>();
    private static final Map<String, Function<SubjectStudyPeriodTeacher, ?>> SUBJECT_STUDY_PERIOD_TEACHER = new LinkedHashMap<>();
    private static final Map<String, Function<SubjectProgramStudyContent, ?>> SUBJECT_PROGRAM_STUDY_CONTENT = new LinkedHashMap<>();

    private final Language lang;

    public PublicDataMapper(Language lang) {
        this.lang = lang;
    }

    public Object map(Object o) {
        if (o instanceof Curriculum) {
            return map((Curriculum)o, CURRICULUM);
        }
        if (o instanceof CurriculumJointPartner) {
            return map((CurriculumJointPartner)o, CURRICULUM_JOINT_PARTNER);
        }
        if (o instanceof CurriculumModule) {
            return map((CurriculumModule)o, CURRICULUM_MODULE);
        }
        if (o instanceof CurriculumOccupation) {
            return map((CurriculumOccupation)o, CURRICULUM_OCCUPATION);
        }
        if (o instanceof CurriculumSpeciality) {
            return map((CurriculumSpeciality)o, CURRICULUM_SPECIALITY);
        }
        if (o instanceof CurriculumVersion) {
            return map((CurriculumVersion)o, CURRICULUM_VERSION);
        }
        if (o instanceof CurriculumVersionElectiveModule) {
            return map((CurriculumVersionElectiveModule)o, CURRICULUM_VERSION_ELECTIVE_MODULE);
        }
        if (o instanceof CurriculumVersionHigherModule) {
            return map((CurriculumVersionHigherModule)o, CURRICULUM_VERSION_HIGHER_MODULE);
        }
        if (o instanceof CurriculumVersionHigherModuleSubject) {
            return map((CurriculumVersionHigherModuleSubject)o, CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT);
        }
        if (o instanceof CurriculumVersionOccupationModule) {
            return map((CurriculumVersionOccupationModule)o, CURRICULUM_VERSION_OCCUPATION_MODULE);
        }
        if (o instanceof CurriculumVersionOccupationModuleCapacity) {
            return map((CurriculumVersionOccupationModuleCapacity)o, CURRICULUM_VERSION_OCCUPATION_MODULE_CAPACITY);
        }
        if (o instanceof CurriculumVersionOccupationModuleTheme) {
            return map((CurriculumVersionOccupationModuleTheme)o, CURRICULUM_VERSION_OCCUPATION_MODULE_THEME);
        }
        if (o instanceof CurriculumVersionOccupationModuleThemeCapacity) {
            return map((CurriculumVersionOccupationModuleThemeCapacity)o, CURRICULUM_VERSION_OCCUPATION_MODULE_THEME_CAPACITY);
        }
        if (o instanceof CurriculumVersionOccupationModuleYearCapacity) {
            return map((CurriculumVersionOccupationModuleYearCapacity)o, CURRICULUM_VERSION_OCCUPATION_MODULE_YEAR_CAPACITY);
        }
        if (o instanceof CurriculumVersionSpeciality) {
            // TODO
            return null;
        }
        if (o instanceof Subject) {
            return map((Subject)o, SUBJECT);
        }
        if (o instanceof SubjectProgram) {
            return map((SubjectProgram)o, SUBJECT_PROGRAM);
        }
        if (o instanceof SubjectStudyPeriodTeacher) {
            return map((SubjectStudyPeriodTeacher)o, SUBJECT_STUDY_PERIOD_TEACHER);
        }
        if (o instanceof SubjectProgramStudyContent) {
            return map((SubjectProgramStudyContent)o, SUBJECT_PROGRAM_STUDY_CONTENT);
        }
        if (o instanceof Translatable) {
            return TranslateUtil.name((Translatable)o, lang);
        }
        if (o instanceof Collection) {
            return StreamUtil.toMappedList(this::map, (Collection<?>)o);
        }
        if (o instanceof BaseEntity || o instanceof BaseLog || o instanceof BaseTask) {
            // don't let any entity classes pass thru to avoid disclosure of private data
            LOG.warn("Bad public data class: {}", o.getClass().getName());
            return null;
            // throw new IllegalStateException("Bad public data class: " + o.getClass().getName());
        }
        return o;
    }

    private <T> Map<String, Object> map(T object, Map<String, Function<T, ?>> properties) {
        Map<String, Object> dto = new LinkedHashMap<>();
        for(Map.Entry<String, Function<T, ?>> property : properties.entrySet()) {
            Object value = property.getValue().apply(object);
            dto.put(property.getKey(), map(value));
        }
        return dto;
    }

    private static List<Map<String, Object>> calculateYearCapacities(CurriculumVersion cv) {
        if (CurriculumUtil.isHigher(cv.getCurriculum())) {
            return null;
        }

        List<Map<String, Object>> capacities = new ArrayList<>();
        int studyYears = CurriculumUtil.studyYears(cv.getCurriculum());
        for(int year = 1; year <= studyYears; year++) {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("studyYearNumber", Integer.valueOf(year));
            dto.put("credits", CurriculumVersionYearCapacitiesUtil.calculate(cv.getOccupationModules(), Short.valueOf((short)year)));
            capacities.add(dto);
        }
        return capacities;
    }

    static {
        CURRICULUM.put("id", Curriculum::getId);
        CURRICULUM.put("higher", Curriculum::getHigher);
        CURRICULUM.put("nameEt", Curriculum::getNameEt);
        CURRICULUM.put("nameEn", Curriculum::getNameEn);
        CURRICULUM.put("nameRu", Curriculum::getNameRu);
        CURRICULUM.put("code", Curriculum::getCode);
        CURRICULUM.put("merCode", Curriculum::getMerCode);
        CURRICULUM.put("approval", Curriculum::getApproval);
        CURRICULUM.put("approvalDokNr", Curriculum::getApprovalDokNr);
        CURRICULUM.put("outcomesEt", Curriculum::getOutcomesEt);
        CURRICULUM.put("outcomesEn", Curriculum::getOutcomesEn);
        CURRICULUM.put("structure", Curriculum::getStructure);
        CURRICULUM.put("admissionRequirementsEt", Curriculum::getAdmissionRequirementsEt);
        CURRICULUM.put("admissionRequirementsEn", Curriculum::getAdmissionRequirementsEn);
        CURRICULUM.put("graduationRequirementsEt", Curriculum::getGraduationRequirementsEt);
        CURRICULUM.put("graduationRequirementsEn", Curriculum::getGraduationRequirementsEn);
        CURRICULUM.put("credits", Curriculum::getCredits);
        CURRICULUM.put("draftText", Curriculum::getDraftText);
        CURRICULUM.put("specialization", Curriculum::getSpecialization);
        CURRICULUM.put("practiceDescription", Curriculum::getPracticeDescription);
        CURRICULUM.put("finalExamDescription", Curriculum::getFinalExamDescription);
        CURRICULUM.put("optionalStudyDescription", Curriculum::getOptionalStudyDescription);
        CURRICULUM.put("description", Curriculum::getDescription);
        CURRICULUM.put("contactPerson", Curriculum::getContactPerson);
        CURRICULUM.put("nameGenitiveEt", Curriculum::getNameGenitiveEt);
        CURRICULUM.put("nameGenitiveEn", Curriculum::getNameGenitiveEn);
        CURRICULUM.put("languageDescription", Curriculum::getLanguageDescription);
        CURRICULUM.put("otherLanguages", Curriculum::getOtherLanguages);
        CURRICULUM.put("objectivesEt", Curriculum::getObjectivesEt);
        CURRICULUM.put("objectivesEn", Curriculum::getObjectivesEn);
        CURRICULUM.put("addInfo", Curriculum::getAddInfo);
        CURRICULUM.put("merRegDate", Curriculum::getMerRegDate);
        CURRICULUM.put("accreditationDate", Curriculum::getAccreditationDate);
        CURRICULUM.put("accreditationResolution", Curriculum::getAccreditationResolution);
        CURRICULUM.put("accreditationValidDate", Curriculum::getAccreditationValidDate);
        CURRICULUM.put("accreditationNr", Curriculum::getAccreditationNr);
        CURRICULUM.put("occupation", Curriculum::getOccupation);
        CURRICULUM.put("studyPeriod", Curriculum::getStudyPeriod);
        CURRICULUM.put("joint", Curriculum::getJoint);
        CURRICULUM.put("optionalStudyCredits", Curriculum::getOptionalStudyCredits);
        CURRICULUM.put("validFrom", Curriculum::getValidFrom);
        CURRICULUM.put("validThru", Curriculum::getValidThru);
        CURRICULUM.put("group", Curriculum::getGroup);
        CURRICULUM.put("ehisStatus", Curriculum::getEhisStatus);
        CURRICULUM.put("jointMentor", Curriculum::getJointMentor);
        CURRICULUM.put("consecution", Curriculum::getConsecution);
        CURRICULUM.put("origStudyLevel", Curriculum::getOrigStudyLevel);
        CURRICULUM.put("iscedClass", Curriculum::getIscedClass);
        CURRICULUM.put("school", Curriculum::getSchool);
        CURRICULUM.put("draft", Curriculum::getDraft);
        CURRICULUM.put("studyLanguages", c -> StreamUtil.toMappedList(CurriculumStudyLanguage::getStudyLang, c.getStudyLanguages()));
        CURRICULUM.put("departments", c -> StreamUtil.toMappedList(CurriculumDepartment::getSchoolDepartment, c.getDepartments()));
        CURRICULUM.put("grades", Curriculum::getGrades);
        CURRICULUM.put("jointPartners", Curriculum::getJointPartners);
        CURRICULUM.put("specialities", Curriculum::getSpecialities);
        CURRICULUM.put("studyForms", c -> StreamUtil.toMappedList(CurriculumStudyForm::getStudyForm, c.getStudyForms()));
        CURRICULUM.put("modules", Curriculum::getModules);
        CURRICULUM.put("occupations", Curriculum::getOccupations);
        CURRICULUM.put("versions", c -> StreamUtil.toFilteredList(CurriculumUtil::isCurriculumVersionConfirmed, c.getVersions()));

        CURRICULUM_JOINT_PARTNER.put("abroad", CurriculumJointPartner::isAbroad);
        CURRICULUM_JOINT_PARTNER.put("contractEt", CurriculumJointPartner::getContractEt);
        CURRICULUM_JOINT_PARTNER.put("contractEn", CurriculumJointPartner::getContractEn);
        CURRICULUM_JOINT_PARTNER.put("supervisor", CurriculumJointPartner::getSupervisor);
        CURRICULUM_JOINT_PARTNER.put("nameEt", CurriculumJointPartner::getNameEt);
        CURRICULUM_JOINT_PARTNER.put("nameEn", CurriculumJointPartner::getNameEn);
        CURRICULUM_JOINT_PARTNER.put("ehisSchool", CurriculumJointPartner::getEhisSchool);

        CURRICULUM_MODULE.put("module", CurriculumModule::getModule);
        CURRICULUM_MODULE.put("nameEt", CurriculumModule::getNameEt);
        CURRICULUM_MODULE.put("nameEn", CurriculumModule::getNameEn);
        CURRICULUM_MODULE.put("credits", CurriculumModule::getCredits);
        CURRICULUM_MODULE.put("objectivesEt", CurriculumModule::getObjectivesEt);
        CURRICULUM_MODULE.put("objectivesEn", CurriculumModule::getObjectivesEn);
        CURRICULUM_MODULE.put("assessmentsEt", CurriculumModule::getAssessmentsEt);
        CURRICULUM_MODULE.put("assessmentsEn", CurriculumModule::getAssessmentsEn);
        CURRICULUM_MODULE.put("practice", CurriculumModule::getPractice);
        CURRICULUM_MODULE.put("isAdditional", CurriculumModule::getIsAdditional);
        CURRICULUM_MODULE.put("occupations", c -> StreamUtil.toMappedList(CurriculumModuleOccupation::getOccupation, c.getOccupations()));
        CURRICULUM_MODULE.put("competences", c -> StreamUtil.toMappedList(CurriculumModuleCompetence::getCompetence, c.getCompetences()));
        CURRICULUM_MODULE.put("outcomes", c -> StreamUtil.toMappedList(AutocompleteResult::of, c.getOutcomes()));

        CURRICULUM_OCCUPATION.put("occupation", CurriculumOccupation::getOccupation);
        CURRICULUM_OCCUPATION.put("occupationGrant", CurriculumOccupation::getOccupationGrant);
        CURRICULUM_OCCUPATION.put("specialities", co -> StreamUtil.toMappedList(CurriculumOccupationSpeciality::getSpeciality, co.getSpecialities()));

        CURRICULUM_SPECIALITY.put("nameEt", CurriculumSpeciality::getNameEt);
        CURRICULUM_SPECIALITY.put("nameEn", CurriculumSpeciality::getNameEn);
        CURRICULUM_SPECIALITY.put("credits", CurriculumSpeciality::getCredits);
        CURRICULUM_SPECIALITY.put("description", CurriculumSpeciality::getDescription);
        CURRICULUM_SPECIALITY.put("occupationEt", CurriculumSpeciality::getOccupationEt);
        CURRICULUM_SPECIALITY.put("occupationEn", CurriculumSpeciality::getOccupationEn);
        CURRICULUM_SPECIALITY.put("occupation", CurriculumSpeciality::getOccupation);

        CURRICULUM_VERSION.put("id", CurriculumVersion::getId);
        CURRICULUM_VERSION.put("code", CurriculumVersion::getCode);
        CURRICULUM_VERSION.put("admissionYear", CurriculumVersion::getAdmissionYear);
        CURRICULUM_VERSION.put("targetGroup", CurriculumVersion::getTargetGroup);
        CURRICULUM_VERSION.put("individual", CurriculumVersion::getIndividual);
        CURRICULUM_VERSION.put("teachers", CurriculumVersion::getTeachers);
        CURRICULUM_VERSION.put("description", CurriculumVersion::getDescription);
        CURRICULUM_VERSION.put("type", CurriculumVersion::getType);
        CURRICULUM_VERSION.put("schoolDepartment", CurriculumVersion::getSchoolDepartment);
        CURRICULUM_VERSION.put("curriculumStudyForm", cv -> Optional.ofNullable(cv.getCurriculumStudyForm()).map(CurriculumStudyForm::getStudyForm).orElse(null));
        CURRICULUM_VERSION.put("validFrom", CurriculumVersion::getValidFrom);
        CURRICULUM_VERSION.put("validThru", CurriculumVersion::getValidThru);
        CURRICULUM_VERSION.put("yearCapacities", PublicDataMapper::calculateYearCapacities);

        CURRICULUM_VERSION.put("modules", CurriculumVersion::getModules);
        CURRICULUM_VERSION.put("specialities", CurriculumVersion::getSpecialities);
        CURRICULUM_VERSION.put("occupationModules", CurriculumVersion::getOccupationModules);

        CURRICULUM_VERSION_ELECTIVE_MODULE.put("nameEt", CurriculumVersionElectiveModule::getNameEt);
        CURRICULUM_VERSION_ELECTIVE_MODULE.put("nameEn", CurriculumVersionElectiveModule::getNameEn);
        CURRICULUM_VERSION_ELECTIVE_MODULE.put("subjects", CurriculumVersionElectiveModule::getSubjects);

        CURRICULUM_VERSION_HIGHER_MODULE.put("nameEt", CurriculumVersionHigherModule::getNameEt);
        CURRICULUM_VERSION_HIGHER_MODULE.put("nameEn", CurriculumVersionHigherModule::getNameEn);
        CURRICULUM_VERSION_HIGHER_MODULE.put("objectivesEt", CurriculumVersionHigherModule::getObjectivesEt);
        CURRICULUM_VERSION_HIGHER_MODULE.put("objectivesEn", CurriculumVersionHigherModule::getObjectivesEn);
        CURRICULUM_VERSION_HIGHER_MODULE.put("outcomesEt", CurriculumVersionHigherModule::getOutcomesEt);
        CURRICULUM_VERSION_HIGHER_MODULE.put("outcomesEn", CurriculumVersionHigherModule::getOutcomesEn);
        CURRICULUM_VERSION_HIGHER_MODULE.put("typeNameEt", CurriculumVersionHigherModule::getTypeNameEt);
        CURRICULUM_VERSION_HIGHER_MODULE.put("typeNameEn", CurriculumVersionHigherModule::getTypeNameEn);
        CURRICULUM_VERSION_HIGHER_MODULE.put("totalCredits", CurriculumVersionHigherModule::getTotalCredits);
        CURRICULUM_VERSION_HIGHER_MODULE.put("optionalStudyCredits", CurriculumVersionHigherModule::getOptionalStudyCredits);
        CURRICULUM_VERSION_HIGHER_MODULE.put("compulsoryStudyCredits", CurriculumVersionHigherModule::getCompulsoryStudyCredits);
        CURRICULUM_VERSION_HIGHER_MODULE.put("electiveModulesNumber", CurriculumVersionHigherModule::getElectiveModulesNumber);
        CURRICULUM_VERSION_HIGHER_MODULE.put("minorSpeciality", CurriculumVersionHigherModule::getMinorSpeciality);
        CURRICULUM_VERSION_HIGHER_MODULE.put("type", CurriculumVersionHigherModule::getType);
        CURRICULUM_VERSION_HIGHER_MODULE.put("subjects", CurriculumVersionHigherModule::getSubjects);
        CURRICULUM_VERSION_HIGHER_MODULE.put("electiveModules", CurriculumVersionHigherModule::getElectiveModules);
        CURRICULUM_VERSION_HIGHER_MODULE.put("specialities", c -> c.getSpecialities().stream().map(s -> s.getSpeciality()).collect(Collectors.toList()));

        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("optional", CurriculumVersionHigherModuleSubject::getOptional);
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("subject", CurriculumVersionHigherModuleSubject::getSubject);
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("studyYearNumber", CurriculumVersionHigherModuleSubject::getStudyYearNumber);
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("autumn", CurriculumVersionHigherModuleSubject::getAutumn);
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("spring", CurriculumVersionHigherModuleSubject::getSpring);
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("assessment", ms -> ms.getSubject().getAssessment().getValue());
        CURRICULUM_VERSION_HIGHER_MODULE_SUBJECT.put("subjectId", ms -> ms.getSubject().getId());

        CURRICULUM_VERSION_OCCUPATION_MODULE.put("nameEt", cvom -> cvom.getCurriculumModule().getNameEt());
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("nameEn", cvom -> cvom.getCurriculumModule().getNameEn());
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("requirementsEt", CurriculumVersionOccupationModule::getRequirementsEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("assessmentsEt", CurriculumVersionOccupationModule::getAssessmentsEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("learningMethodsEt", CurriculumVersionOccupationModule::getLearningMethodsEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("assessmentMethodsEt", CurriculumVersionOccupationModule::getAssessmentMethodsEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("assessment", CurriculumVersionOccupationModule::getAssessment);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("totalGradeDescription", CurriculumVersionOccupationModule::getTotalGradeDescription);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("passDescription", CurriculumVersionOccupationModule::getPassDescription);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("grade3Description", CurriculumVersionOccupationModule::getGrade3Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("grade4Description", CurriculumVersionOccupationModule::getGrade4Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("grade5Description", CurriculumVersionOccupationModule::getGrade5Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("independentStudyEt", CurriculumVersionOccupationModule::getIndependentStudyEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("studyMaterials", CurriculumVersionOccupationModule::getStudyMaterials);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("supervisor", CurriculumVersionOccupationModule::getSupervisor);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("capacities", CurriculumVersionOccupationModule::getCapacities);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("themes", CurriculumVersionOccupationModule::getThemes);
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("totalCredits", cvom -> cvom.getCurriculumModule().getCredits());
        CURRICULUM_VERSION_OCCUPATION_MODULE.put("yearCapacities", cvom -> StreamUtil.nullSafeSet(cvom.getYearCapacities()).stream().sorted(
                Comparator.comparing(CurriculumVersionOccupationModuleYearCapacity::getStudyYearNumber)).collect(Collectors.toList()));

        CURRICULUM_VERSION_OCCUPATION_MODULE_CAPACITY.put("capacityType", CurriculumVersionOccupationModuleCapacity::getCapacityType);
        CURRICULUM_VERSION_OCCUPATION_MODULE_CAPACITY.put("hours", CurriculumVersionOccupationModuleCapacity::getHours);
        CURRICULUM_VERSION_OCCUPATION_MODULE_CAPACITY.put("contact", CurriculumVersionOccupationModuleCapacity::getContact);

        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("nameEt", CurriculumVersionOccupationModuleTheme::getNameEt);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("credits", CurriculumVersionOccupationModuleTheme::getCredits);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("hours", CurriculumVersionOccupationModuleTheme::getHours);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("proportion", CurriculumVersionOccupationModuleTheme::getProportion);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("subthemes", CurriculumVersionOccupationModuleTheme::getSubthemes);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("studyYearNumber", CurriculumVersionOccupationModuleTheme::getStudyYearNumber);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("assessment", CurriculumVersionOccupationModuleTheme::getAssessment);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("totalGradeDescription", CurriculumVersionOccupationModuleTheme::getTotalGradeDescription);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("passDescription", CurriculumVersionOccupationModuleTheme::getPassDescription);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("grade3Description", CurriculumVersionOccupationModuleTheme::getGrade3Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("grade4Description", CurriculumVersionOccupationModuleTheme::getGrade4Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("grade5Description", CurriculumVersionOccupationModuleTheme::getGrade5Description);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("outcomes", c -> c.getOutcomes().stream().map(o -> o.getOutcome()).map(AutocompleteResult::of).collect(Collectors.toList()));
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME.put("capacities", CurriculumVersionOccupationModuleTheme::getCapacities);

        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME_CAPACITY.put("capacityType", CurriculumVersionOccupationModuleThemeCapacity::getCapacityType);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME_CAPACITY.put("hours", CurriculumVersionOccupationModuleThemeCapacity::getHours);
        CURRICULUM_VERSION_OCCUPATION_MODULE_THEME_CAPACITY.put("contact", CurriculumVersionOccupationModuleThemeCapacity::getContact);

        CURRICULUM_VERSION_OCCUPATION_MODULE_YEAR_CAPACITY.put("studyYearNumber", CurriculumVersionOccupationModuleYearCapacity::getStudyYearNumber);
        CURRICULUM_VERSION_OCCUPATION_MODULE_YEAR_CAPACITY.put("credits", CurriculumVersionOccupationModuleYearCapacity::getCredits);

        SUBJECT.put("id", Subject::getId);
        SUBJECT.put("school", Subject::getSchool);
        SUBJECT.put("code", Subject::getCode);
        SUBJECT.put("nameEt", Subject::getNameEt);
        SUBJECT.put("nameEn", Subject::getNameEn);
        SUBJECT.put("description", Subject::getDescription);
        SUBJECT.put("credits", Subject::getCredits);
        SUBJECT.put("assessment", Subject::getAssessment);
        SUBJECT.put("assessmentDescription", Subject::getAssessmentDescription);
        SUBJECT.put("objectivesEt", Subject::getObjectivesEt);
        SUBJECT.put("objectivesEn", Subject::getObjectivesEn);
        SUBJECT.put("outcomesEt", Subject::getOutcomesEt);
        SUBJECT.put("outcomesEn", Subject::getOutcomesEn);
        SUBJECT.put("descriptionEt", Subject::getDescriptionEt);
        SUBJECT.put("descriptionEn", Subject::getDescriptionEn);
        SUBJECT.put("studyLiterature", Subject::getStudyLiterature);
        SUBJECT.put("evaluationEt", Subject::getEvaluationEt);
        SUBJECT.put("evaluationEn", Subject::getEvaluationEn);
        SUBJECT.put("independentStudyEt", Subject::getIndependentStudyEt);
        SUBJECT.put("independentStudyEn", Subject::getIndependentStudyEn);
        SUBJECT.put("additionalInfo", Subject::getAdditionalInfo);
        SUBJECT.put("isPractice", Subject::getIsPractice);
        SUBJECT.put("schoolDepartment", Subject::getSchoolDepartment);
        SUBJECT.put("subjectLanguages", s -> StreamUtil.toMappedList(SubjectLanguage::getLanguage, s.getSubjectLanguages()));

        SUBJECT_PROGRAM.put("id", SubjectProgram::getId);
        SUBJECT_PROGRAM.put("subjectId", p -> p.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getSubject().getId());
        SUBJECT_PROGRAM.put("assessmentDescription", SubjectProgram::getAssessmentDescription);
        SUBJECT_PROGRAM.put("passDescription", SubjectProgram::getPassDescription);
        SUBJECT_PROGRAM.put("npassDescription", SubjectProgram::getNpassDescription);
        SUBJECT_PROGRAM.put("grade0Description", SubjectProgram::getGrade0Description);
        SUBJECT_PROGRAM.put("grade1Description", SubjectProgram::getGrade1Description);
        SUBJECT_PROGRAM.put("grade2Description", SubjectProgram::getGrade2Description);
        SUBJECT_PROGRAM.put("grade3Description", SubjectProgram::getGrade3Description);
        SUBJECT_PROGRAM.put("grade4Description", SubjectProgram::getGrade4Description);
        SUBJECT_PROGRAM.put("grade5Description", SubjectProgram::getGrade5Description);
        SUBJECT_PROGRAM.put("independentStudy", SubjectProgram::getIndependentStudy);
        SUBJECT_PROGRAM.put("studyContents", SubjectProgram::getStudyContents);
        SUBJECT_PROGRAM.put("studyContentType", p -> p.getStudyContentType().getCode());
        SUBJECT_PROGRAM.put("studyDescription", SubjectProgram::getStudyDescription);
        SUBJECT_PROGRAM.put("studyLiterature", SubjectProgram::getStudyLiterature);
        SUBJECT_PROGRAM.put("subjectStudyPeriodTeacher", p -> p.getSubjectStudyPeriodTeacher());

        SUBJECT_STUDY_PERIOD_TEACHER.put("id", sspt -> sspt.getTeacher().getId());
        SUBJECT_STUDY_PERIOD_TEACHER.put("name", sspt -> sspt.getTeacher().getPerson().getFullname());

        SUBJECT_PROGRAM_STUDY_CONTENT.put("id", SubjectProgramStudyContent::getId);
        SUBJECT_PROGRAM_STUDY_CONTENT.put("weekNr", SubjectProgramStudyContent::getWeekNr);
        SUBJECT_PROGRAM_STUDY_CONTENT.put("studyDt", SubjectProgramStudyContent::getStudyDt);
        SUBJECT_PROGRAM_STUDY_CONTENT.put("studyInfo", SubjectProgramStudyContent::getStudyInfo);
    }
}
