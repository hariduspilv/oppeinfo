package ee.hitsa.ois.web.commandobject.sais;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ee.hitsa.ois.web.dto.ClassifierSelection;


@JsonPropertyOrder({
    "Klassifikaator",
    "Väärtus",
    "Nimetus"})
public class SaisApplicationClassifiersCsv {
    @JsonProperty("Klassifikaator")
    private String mainClassCode;

    @JsonProperty("Väärtus")
    private String value;

    @JsonProperty("Nimetus")
    private String nameEt;

    public static SaisApplicationClassifiersCsv of(ClassifierSelection classifierSelection) {
        SaisApplicationClassifiersCsv row = new SaisApplicationClassifiersCsv();
        row.setMainClassCode(classifierSelection.getMainClassCode());
        row.setNameEt(classifierSelection.getNameEt());
        row.setValue(classifierSelection.getValue());
        return row;
    }

    public String getMainClassCode() {
        return mainClassCode;
    }

    public void setMainClassCode(String mainClassCode) {
        this.mainClassCode = mainClassCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
}
