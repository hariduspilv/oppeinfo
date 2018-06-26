package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierSearchDto extends ClassifierSelection {

    public ClassifierSearchDto(String code, String nameEt, String nameEn, String nameRu, Boolean valid, 
            Boolean higher, Boolean vocational, String mainClassCode, String value, String value2,
            LocalDate validFrom, LocalDate validThru) {
        super(code, nameEt, nameEn, nameRu, valid, higher, vocational, mainClassCode, value, value2, validFrom, validThru);
    }

    public static ClassifierSearchDto of(Classifier c) {
        return new ClassifierSearchDto(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(),
                Boolean.valueOf(c.isValid()), Boolean.valueOf(c.isHigher()), Boolean.valueOf(c.isVocational()),
                c.getMainClassCode(), c.getValue(), c.getValue2(), c.getValidFrom(), c.getValidThru());
    }

}
