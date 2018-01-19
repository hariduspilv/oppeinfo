package ee.hitsa.ois.web.dto.finalExamVocationalProtocol;

import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;

public class FinalExamVocationalProtocolCurriculumOccupationDto {
    
    private Long id;
    private CurriculumOccupationDto curriculumOccupation;
    private String certificateNr;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public CurriculumOccupationDto getCurriculumOccupation() {
        return curriculumOccupation;
    }
    
    public void setCurriculumOccupation(CurriculumOccupationDto curriculumOccupation) {
        this.curriculumOccupation = curriculumOccupation;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }
    
}
