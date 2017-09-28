package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierSearchDto extends ClassifierSelection {

    private LocalDate validFrom;
    private LocalDate validThru;

    public ClassifierSearchDto(String code, String nameEt, String nameEn, String nameRu, Boolean valid, Boolean higher, Boolean vocational, String mainClassCode, String value, String value2) {
        super(code, nameEt, nameEn, nameRu, valid, higher, vocational, mainClassCode, value, value2);
    }

    public static ClassifierSearchDto of(Classifier c) {
        ClassifierSearchDto dto = new ClassifierSearchDto(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(),
                Boolean.valueOf(c.isValid()), Boolean.valueOf(c.isHigher()), Boolean.valueOf(c.isVocational()),
                c.getMainClassCode(), c.getValue(), c.getValue2());
        dto.setValidFrom(c.getValidFrom());
        dto.setValidThru(c.getValidThru());
        return dto;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
}
