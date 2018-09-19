package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;

public class StudentScholarshipEnding extends StudentMessage {

    private final String scholarshipType;
    
    public StudentScholarshipEnding() {
        scholarshipType = null;
    }
    
    public StudentScholarshipEnding(ScholarshipApplication application) {
        super(application.getStudent());
        
        scholarshipType = application.getScholarshipTerm().getType().getNameEt();
    }
    
    public String getOppetoetuseStipendiumiLiik() {
        return scholarshipType;
    }
}
