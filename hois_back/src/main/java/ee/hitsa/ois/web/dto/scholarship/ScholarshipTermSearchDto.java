package ee.hitsa.ois.web.dto.scholarship;

import java.time.LocalDate;

public class ScholarshipTermSearchDto {
    private Long id;
    private String nameEt;
    private String type;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private Long places;
    private Boolean isOpen;

    public ScholarshipTermSearchDto() {

    }

    public ScholarshipTermSearchDto(Long id, String nameEt, String type, LocalDate applicationStart,
            LocalDate applicationEnd, Long places, Boolean isOpen) {
        this.id = id;
        this.nameEt = nameEt;
        this.type = type;
        this.applicationStart = applicationStart;
        this.applicationEnd = applicationEnd;
        this.places = places;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getApplicationStart() {
        return applicationStart;
    }

    public void setApplicationStart(LocalDate applicationStart) {
        this.applicationStart = applicationStart;
    }

    public LocalDate getApplicationEnd() {
        return applicationEnd;
    }

    public void setApplicationEnd(LocalDate applicationEnd) {
        this.applicationEnd = applicationEnd;
    }

    public Long getPlaces() {
        return places;
    }

    public void setPlaces(Long places) {
        this.places = places;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

}
