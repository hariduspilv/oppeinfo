package ee.hitsa.ois.web.dto.finalprotocol;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalHigherProtocolSubjectResult extends AutocompleteResult {

    private final Long subjectStudyPeriod;
    
    public FinalHigherProtocolSubjectResult(Long id, String nameEt, String nameEn, Long subjectStudyPeriod) {
        super(id, nameEt, nameEn);
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    
}
