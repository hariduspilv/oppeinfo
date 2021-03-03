package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SpecialityAutocompleteResult;

public class StudentSpecialitySearchDto {
    
    private Long id;
    private String name;
    private String idcode;
    private AutocompleteResult group;
    private SpecialityAutocompleteResult speciality;
    private SpecialityAutocompleteResult occupationSpeciality;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdcode() {
        return idcode;
    }
    
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    public AutocompleteResult getGroup() {
        return group;
    }
    
    public void setGroup(AutocompleteResult group) {
        this.group = group;
    }
    
    public SpecialityAutocompleteResult getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(SpecialityAutocompleteResult speciality) {
        this.speciality = speciality;
    }

    public SpecialityAutocompleteResult getOccupationSpeciality() {
        return occupationSpeciality;
    }

    public void setOccupationSpeciality(SpecialityAutocompleteResult occupationSpeciality) {
        this.occupationSpeciality = occupationSpeciality;
    }
}
