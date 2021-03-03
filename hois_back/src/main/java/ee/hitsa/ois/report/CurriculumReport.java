package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;
import static ee.hitsa.ois.util.TranslateUtil.translate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumReport {

    public static final String TEMPLATE_NAME = "curriculum.xhtml";

    private final String nameEt;
    private final String nameEn;
    private final String origStudyLevel;
    private final String studyForm;
    private final String school;
    private final BigDecimal credits;
    private final String studyPeriod;
    private final String group;
    private final String merCode;
    private final String studyLang;
    private final String otherLanguages;
    private final LocalDate merRegDate;
    private final LocalDate approvalDate;
    private final String admissionRequirements;
    private final List<String> specialities;
    private final List<String> minorSpecialities;
    private final String objectives;
    private final String outcomes;
    private final String grade;
    private final String documents;
    private final List<Structure> structure;
    private final String optionalStudyDescription;
    private final String graduationRequirements;
    private final String addInfo;

    public CurriculumReport(CurriculumVersion curriculumVersion) {
        this(curriculumVersion, Language.ET);
    }

    private CurriculumReport(CurriculumVersion curriculumVersion, Language lang) {
        Objects.requireNonNull(curriculumVersion);

        Curriculum curriculum = curriculumVersion.getCurriculum();
        nameEt = curriculum.getNameEt();
        nameEn = curriculum.getNameEn();
        origStudyLevel = name(curriculum.getOrigStudyLevel(), lang);
        studyForm = curriculum.getStudyForms().stream().map(r -> name(r.getStudyForm(), lang)).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining(", "));
        List<String> jointSchools = new ArrayList<>();
        jointSchools.add(name(curriculum.getSchool(), lang));
        if(Boolean.TRUE.equals(curriculum.getJoint())) {
            // CurriculumJointPartner.name(Et|En) contains only name of abroad school, otherwise use CurriculumJointPartner.ehisSchool.name(Et|En)
            jointSchools.addAll(curriculum.getJointPartners().stream().map(r -> name(r.isAbroad() ? r : r.getEhisSchool(), lang)).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList()));
        }
        school = String.join(", ", jointSchools);
        credits = curriculum.getCredits();
        // study years and months
        Integer studyMonths = curriculum.getStudyPeriod();
        if(studyMonths != null) {
            StringBuilder sb = new StringBuilder();
            int years = studyMonths.intValue() / 12;
            int months = studyMonths.intValue() % 12;
            if(years > 0) {
                sb.append(String.valueOf(years));
                sb.append(' ');
                sb.append(translate(years != 1 ? "years" : "year", lang));
                sb.append(' ');
            }
            sb.append(String.valueOf(months));
            sb.append(' ');
            sb.append(translate(months != 1 ? "months" : "month", lang));
            studyPeriod = sb.toString();
        } else {
            studyPeriod = null;
        }
        group = name(curriculum.getGroup(), lang);
        merCode = curriculum.getMerCode();
        studyLang = curriculum.getStudyLanguages().stream().map(r -> name(r.getStudyLang(), lang)).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining(" "+translate("or", lang)+" "));
        otherLanguages = curriculum.getOtherLanguages();
        merRegDate = curriculum.getMerRegDate();
        approvalDate = curriculum.getApproval();
        // XXX language-specific field
        admissionRequirements = curriculum.getAdmissionRequirementsEt();
        specialities = curriculumVersion.getSpecialities().stream().map(r -> String.format("%s %s", name(r.getCurriculumSpeciality(), lang), r.getCurriculumSpeciality().getCredits())).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        minorSpecialities = curriculumVersion.getModules().stream()
                .filter(r -> Boolean.TRUE.equals(r.getMinorSpeciality()))
                .sorted(Comparator
                        .comparing(CurriculumVersionHigherModule::getOrderNr,
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Comparator.comparing(m -> name(m, lang), String.CASE_INSENSITIVE_ORDER)))
                .map(r -> String.format("%s %s", name(r, lang), r.getTotalCredits())).collect(Collectors.toList());
        // XXX language-specific field
        objectives = curriculum.getObjectivesEt();
        // XXX language-specific field
        outcomes = curriculum.getOutcomesEt();
        grade = curriculum.getGrades().stream().map(r -> name(r, lang)).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining(", "));
        documents = "Diplom ja akadeemiline õiend";
        List<CurriculumVersionHigherModule> modules = StreamUtil.toFilteredList(r -> !Boolean.TRUE.equals(r.getMinorSpeciality()), curriculumVersion.getModules());
        Map<Long, List<CurriculumVersionHigherModule>> moduleMap = new HashMap<>();
        for(CurriculumVersionHigherModule m : modules) {
            for(CurriculumVersionHigherModuleSpeciality hs : m.getSpecialities()) {
                Long specialityId = EntityUtil.getId(hs.getSpeciality());
                List<CurriculumVersionHigherModule> ml = moduleMap.get(specialityId);
                if(ml == null) {
                    moduleMap.put(specialityId, ml = new ArrayList<>());
                }
                ml.add(m);
            }
        }
        structure = curriculumVersion.getSpecialities().stream().map(r -> new Structure(r, moduleMap.get(EntityUtil.getId(r)), lang)).sorted(Comparator.comparing(Structure::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        optionalStudyDescription = curriculum.getOptionalStudyDescription();
        // XXX language-specific field
        graduationRequirements = curriculum.getGraduationRequirementsEt();
        addInfo = curriculum.getAddInfo();
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getOrigStudyLevel() {
        return origStudyLevel;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public String getSchool() {
        return school;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getStudyPeriod() {
        return studyPeriod;
    }

    public String getGroup() {
        return group;
    }

    public String getMerCode() {
        return merCode;
    }

    public String getStudyLang() {
        return studyLang;
    }

    public String getOtherLanguages() {
        return otherLanguages;
    }

    public LocalDate getMerRegDate() {
        return merRegDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public String getAdmissionRequirements() {
        return admissionRequirements;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public List<String> getMinorSpecialities() {
        return minorSpecialities;
    }

    public String getObjectives() {
        return objectives;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public String getGrade() {
        return grade;
    }

    public String getDocuments() {
        return documents;
    }

    public List<Structure> getStructure() {
        return structure;
    }

    public String getOptionalStudyDescription() {
        return optionalStudyDescription;
    }

    public String getGraduationRequirements() {
        return graduationRequirements;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public static class Structure {
        private final String name;
        private final List<String> modules;

        public Structure(CurriculumVersionSpeciality speciality, List<CurriculumVersionHigherModule> modules, Language lang) {
            this.name = name(speciality.getCurriculumSpeciality(), lang);
            this.modules = StreamUtil.nullSafeList(modules).stream()
                    .sorted(Comparator
                            .comparing(CurriculumVersionHigherModule::getOrderNr,
                                    Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Comparator.comparing(m -> name(m, lang), String.CASE_INSENSITIVE_ORDER)))
                    .map(r -> String.format("%s %s/%s", name(r, lang), r.getCompulsoryStudyCredits(), r.getOptionalStudyCredits())).collect(Collectors.toList());
        }

        public String getName() {
            return name;
        }

        public List<String> getModules() {
            return modules;
        }
    }
}
