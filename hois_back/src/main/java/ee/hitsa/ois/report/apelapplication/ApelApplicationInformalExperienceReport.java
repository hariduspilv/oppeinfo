package ee.hitsa.ois.report.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalExperience;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.TranslateUtil;

public class ApelApplicationInformalExperienceReport {

    private final String name;
    private final String placeTime;
    private final Short hours;
    private final String documents;
    private final String type;

    public ApelApplicationInformalExperienceReport(ApelApplicationInformalExperience informalExperience, Language lang) {
        name = informalExperience.getNameEt();
        placeTime = informalExperience.getPlaceTime();
        hours = informalExperience.getHours();
        documents = informalExperience.getDocuments();
        type = TranslateUtil.name(informalExperience.getType(), lang);
    }

    public String getName() {
        return name;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public Short getHours() {
        return hours;
    }

    public String getDocuments() {
        return documents;
    }

    public String getType() {
        return type;
    }
}
