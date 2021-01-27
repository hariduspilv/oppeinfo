package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.report.curriculum.CurriculumModulesReport;
import ee.hitsa.ois.report.curriculum.CurriculumReport;
import ee.hitsa.ois.report.curriculum.CurriculumVersionModulesReport;
import ee.hitsa.ois.report.curriculum.CurriculumVersionReport;

public class CurriculumVersionSummaryReport {
    
    public static final String TEMPLATE_NAME = "curriculum.version.summary.fo";

    private CurriculumModulesReport curriculumModules;
    
    private CurriculumReport general;

    private CurriculumVersionReport curriculumVersion;

    private CurriculumVersionModulesReport curriculumVersionModules;

    public CurriculumVersionSummaryReport(CurriculumVersion curriculumVersion, Language lang, String frontendBaseUrl) {
        this.general = new CurriculumReport(curriculumVersion.getCurriculum(), frontendBaseUrl);
        this.curriculumModules = new CurriculumModulesReport(curriculumVersion.getCurriculum());
        this.curriculumVersion = new CurriculumVersionReport(curriculumVersion);
        this.curriculumVersionModules = new CurriculumVersionModulesReport(curriculumVersion);
    }

    public CurriculumReport getGeneral() {
        return general;
    }

    public void setGeneral(CurriculumReport general) {
        this.general = general;
    }

    public CurriculumModulesReport getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(CurriculumModulesReport curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public CurriculumVersionReport getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersionReport curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public CurriculumVersionModulesReport getCurriculumVersionModules() {
        return curriculumVersionModules;
    }

    public void setCurriculumVersionModules(CurriculumVersionModulesReport curriculumVersionModules) {
        this.curriculumVersionModules = curriculumVersionModules;
    }

}
