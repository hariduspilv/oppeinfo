package ee.hitsa.ois.web.dto;

public class OccupiedAutocompleteResult extends AutocompleteResult {

    private Boolean isOccupied;
    
    public OccupiedAutocompleteResult(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

}
