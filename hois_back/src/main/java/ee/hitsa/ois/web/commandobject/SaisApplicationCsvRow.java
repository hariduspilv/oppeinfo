package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

@JsonPropertyOrder({
    "code",
    "applicationNr",
    "firstname",
    "lastname",
    "idcode",
    "citizenship",
    "residenceCountry",
    "fin",
    "saisChanged",
    "status",
    "curriculumVersionCode",
    "studyLoad",
    "studyForm",
    "language",
    "studyLevel",
    "startDate",
    "endDate"})
public class SaisApplicationCsvRow {

    private String code;
    private String applicationNr;
    private String firstname;
    private String lastname;
    private String idcode;

    @ClassifierRestriction(value = { MainClassCode.RIIK }, useClassifierValue = true)
    private String citizenship;

    @ClassifierRestriction(value = { MainClassCode.RIIK }, useClassifierValue = true)
    private String residenceCountry;

    @ClassifierRestriction(value = { MainClassCode.FINALLIKAS }, useClassifierValue = true)
    private String fin;

    @JsonDeserialize(using = SaisApplicationLocalDateDeserializer.class)
    private LocalDate saisChanged;

    @ClassifierRestriction(value = { MainClassCode.SAIS_AVALDUSESTAATUS }, useClassifierValue = true)
    private String status;
    private String curriculumVersionCode;

    @ClassifierRestriction(value = { MainClassCode.OPPEKOORMUS }, useClassifierValue = true)
    private String studyLoad;

    @ClassifierRestriction(value = { MainClassCode.OPPEVORM }, useClassifierValue = true)
    private String studyForm;

    @ClassifierRestriction(value = { MainClassCode.OPPEKEEL }, useClassifierValue = true)
    private String language;

    @ClassifierRestriction(value = { MainClassCode.OPPEASTE }, useClassifierValue = true)
    private String studyLevel;

    @JsonDeserialize(using = SaisApplicationLocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonDeserialize(using = SaisApplicationLocalDateDeserializer.class)
    private LocalDate endDate;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getApplicationNr() {
        return applicationNr;
    }
    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }
    public String getResidenceCountry() {
        return residenceCountry;
    }
    public void setResidenceCountry(String residenceCountry) {
        this.residenceCountry = residenceCountry;
    }
    public String getFin() {
        return fin;
    }
    public void setFin(String fin) {
        this.fin = fin;
    }
    public LocalDate getSaisChanged() {
        return saisChanged;
    }
    public void setSaisChanged(LocalDate saisChanged) {
        this.saisChanged = saisChanged;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCurriculumVersionCode() {
        return curriculumVersionCode;
    }
    public void setCurriculumVersionCode(String curriculumVersionCode) {
        this.curriculumVersionCode = curriculumVersionCode;
    }
    public String getStudyLoad() {
        return studyLoad;
    }
    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }
    public String getStudyForm() {
        return studyForm;
    }
    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getStudyLevel() {
        return studyLevel;
    }
    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


}

class SaisApplicationLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final ThreadLocal<DateTimeFormatter> CSV_DATE_FORMATTER = ThreadLocal.withInitial(() -> {
        return DateTimeFormatter.ofPattern("d.M.yyyy");
    });

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            return LocalDate.parse(p.getText(), CSV_DATE_FORMATTER.get());
        } catch (@SuppressWarnings("unused") Exception e) {
            return null;
        }
    }

}

