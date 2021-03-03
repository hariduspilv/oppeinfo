package ee.hitsa.ois.report;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class StateCurriculumReport {
    
    public static final String TEMPLATE_NAME = "statecurriculum.xhtml";
    public static final String SECONDARY_TEMPLATE_NAME = "secondarystatecurriculum.xhtml";

    private final String nameEt;
    private final String nameEn;
    private final String iscedClass;
    private final String stateCurrClass;
    private final BigDecimal credits;
    private final String objectives;
    private final String outcomes;
    private final String admissionRequirements;
    private final String graduationRequirements;
    private final String description;
    private final Long courses;
    private final String riigiteatajaUrl;
    private final Boolean basicSchool;
    private final List<StateCurriculumModuleReport> modules;
    private final List<StateCurriculumSubjectAreaReport> subjectAreas;
    private final List<StateCurriculumOccupationReport> occupations;

    public StateCurriculumReport(StateCurriculum stateCurriculum) {
        Objects.requireNonNull(stateCurriculum);
        nameEt = stateCurriculum.getNameEt();
        nameEn = stateCurriculum.getNameEn();
        iscedClass = stateCurriculum.getIscedClass().getNameEt();
        stateCurrClass = EntityUtil.getCode(stateCurriculum.getStateCurrClass());
        basicSchool = Boolean.valueOf("EHIS_ROK_PROK".equals(stateCurrClass));
        credits = stateCurriculum.getCredits();
        objectives = stateCurriculum.getObjectivesEt();
        outcomes = stateCurriculum.getOutcomesEt();
        admissionRequirements = stateCurriculum.getAdmissionRequirementsEt();
        graduationRequirements = stateCurriculum.getGraduationRequirementsEt();
        description = stateCurriculum.getDescription();
        courses = stateCurriculum.getCourses();
        riigiteatajaUrl = stateCurriculum.getRiigiteatajaUrl();
        modules = StreamUtil.toMappedList(m -> new StateCurriculumModuleReport(m), stateCurriculum.getModules());
        occupations = StreamUtil.toMappedList(m -> new StateCurriculumOccupationReport(m), stateCurriculum.getOccupations());
        Map<String, List<StateCurriculumModule>> map = stateCurriculum.getModules().stream()
                .filter(p -> ClassifierUtil.mainClassCodeEquals(MainClassCode.EHIS_AINE, p.getModule()))
                .filter(p -> {
                    Optional<ClassifierConnect> connect = ClassifierUtil.parentLinkFor(p.getModule(), MainClassCode.AINEVALDKOND);
                    boolean hasConnect = false;
                    if (connect.isPresent()) {
                        String code = EntityUtil.getCode(connect.get().getConnectClassifier());
                        if (code != null) {
                            hasConnect = true;
                        }
                    }
                    return hasConnect;
                })
                .collect(Collectors.groupingBy(p -> (String) p.getModule().getClassifierConnects().iterator().next().getConnectClassifier().getCode()));
        subjectAreas = StreamUtil.toMappedList(m -> new StateCurriculumSubjectAreaReport(m), new TreeMap<>(map).entrySet().stream());
    }
    
    public List<StateCurriculumOccupationReport> getOccupations() {
        return occupations;
    }

    public List<StateCurriculumModuleReport> getModules() {
        return modules;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public static String getTemplateName() {
        return TEMPLATE_NAME;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getObjectives() {
        return objectives;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public String getAdmissionRequirements() {
        return admissionRequirements;
    }

    public String getGraduationRequirements() {
        return graduationRequirements;
    }

    public String getDescription() {
        return description;
    }

    public List<StateCurriculumSubjectAreaReport> getSubjectAreas() {
        return subjectAreas;
    }

    public static String getSecondaryTemplateName() {
        return SECONDARY_TEMPLATE_NAME;
    }

    public Long getCourses() {
        return courses;
    }

    public String getRiigiteatajaUrl() {
        return riigiteatajaUrl;
    }

    public String getStateCurrClass() {
        return stateCurrClass;
    }

    public Boolean getBasicSchool() {
        return basicSchool;
    }
}
