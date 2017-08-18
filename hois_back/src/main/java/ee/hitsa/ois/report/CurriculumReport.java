package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;
import static ee.hitsa.ois.util.TranslateUtil.translate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.enums.Language;

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
    private final String specialization;
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
        studyForm = curriculum.getStudyForms().stream().map(r -> name(r.getStudyForm(), lang)).sorted().collect(Collectors.joining(", "));
        List<String> jointSchools = new ArrayList<>();
        jointSchools.add(name(curriculum.getSchool(), lang));
        if(Boolean.TRUE.equals(curriculum.getJoint())) {
            // CurriculumJointPartner.name(Et|En) contains only name of abroad school, otherwise use CurriculumJointPartner.ehisSchool.name(Et|En)
            jointSchools.addAll(curriculum.getJointPartners().stream().map(r -> r.isAbroad() ? name(r, lang) : name(r.getEhisSchool(), lang)).sorted().collect(Collectors.toList()));
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
        studyLang = curriculum.getStudyLanguages().stream().map(r -> name(r.getStudyLang(), lang)).sorted().collect(Collectors.joining(" "+translate("or", lang)+" "));
        otherLanguages = curriculum.getOtherLanguages();
        merRegDate = curriculum.getMerRegDate();
        approvalDate = curriculum.getApproval();
        // XXX language-specific field
        admissionRequirements = curriculum.getAdmissionRequirementsEt();
        specialities = curriculumVersion.getSpecialities().stream().map(r -> String.format("%s %s", name(r.getCurriculumSpeciality(), lang), r.getCurriculumSpeciality().getCredits())).sorted().collect(Collectors.toList());
        minorSpecialities = curriculumVersion.getModules().stream().filter(r -> Boolean.TRUE.equals(r.getMinorSpeciality())).map(r -> String.format("%s %s", name(r, lang), r.getTotalCredits())).sorted().collect(Collectors.toList());
        // XXX language-specific field
        objectives = curriculum.getObjectivesEt();
        // XXX language-specific field
        outcomes = curriculum.getOutcomesEt();
        grade = curriculum.getGrades().stream().map(r -> name(r, lang)).sorted().collect(Collectors.joining(", "));
        documents = "Diplom ja akadeemiline õiend";
        structure = curriculumVersion.getModules().stream().filter(r -> !Boolean.TRUE.equals(r.getMinorSpeciality())).map(r -> new Structure(r, lang)).sorted(Comparator.comparing(Structure::getName)).collect(Collectors.toList());
        specialization = curriculum.getSpecialization();
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

    public String getSpecialization() {
        return specialization;
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

        public Structure(CurriculumVersionHigherModule hmodule, Language lang) {
            this.name = name(hmodule, lang);
            this.modules = hmodule.getSpecialities().stream().map(r -> r.getModule()).map(r -> String.format("%s %s/%s", name(r, lang), r.getCompulsoryStudyCredits(), r.getOptionalStudyCredits())).sorted().collect(Collectors.toList());
        }

        public String getName() {
            return name;
        }

        public List<String> getModules() {
            return modules;
        }
    }
}
