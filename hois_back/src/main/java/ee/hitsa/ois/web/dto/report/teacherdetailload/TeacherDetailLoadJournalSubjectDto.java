package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadJournalSubjectDto extends PeriodDetailLoadDto implements Cloneable {

    private AutocompleteResult journalSubject;
    private List<String> studentGroups = new ArrayList<>();
    private String subjectCode;
    private BigDecimal credits;

    public AutocompleteResult getJournalSubject() {
        return journalSubject;
    }

    public void setJournalSubject(AutocompleteResult journalSubject) {
        this.journalSubject = journalSubject;
    }

    public List<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}
    
    public TeacherDetailLoadJournalSubjectDto clone() throws CloneNotSupportedException {
        return (TeacherDetailLoadJournalSubjectDto) super.clone();
    }

}
