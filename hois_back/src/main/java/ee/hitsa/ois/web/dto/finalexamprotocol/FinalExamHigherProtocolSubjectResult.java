package ee.hitsa.ois.web.dto.finalexamprotocol;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalExamHigherProtocolSubjectResult extends AutocompleteResult {

    private final Long subjectStudyPeriod;
    
    public FinalExamHigherProtocolSubjectResult(Long id, String nameEt, String nameEn, Long subjectStudyPeriod) {
        super(id, nameEt, nameEn);
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    
}
