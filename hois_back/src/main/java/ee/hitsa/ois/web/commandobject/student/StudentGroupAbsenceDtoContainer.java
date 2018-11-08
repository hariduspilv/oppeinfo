package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentGroupAbsenceDtoContainer {

    private List<AutocompleteResult> journals;
    private List<LocalDate> dates;
    private Map<LocalDate, List<AutocompleteResult>> journalsByDates;
    private List<AutocompleteResult> students;
    private List<StudentGroupAbsenceDto> studentAbsences;

    public List<AutocompleteResult> getJournals() {
        return journals;
    }

    public void setJournals(List<AutocompleteResult> journals) {
        this.journals = journals;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public Map<LocalDate, List<AutocompleteResult>> getJournalsByDates() {
        return journalsByDates;
    }

    public void setJournalsByDates(Map<LocalDate, List<AutocompleteResult>> journalsByDates) {
        this.journalsByDates = journalsByDates;
    }

    public List<AutocompleteResult> getStudents() {
        return students;
    }

    public void setStudents(List<AutocompleteResult> students) {
        this.students = students;
    }

    public List<StudentGroupAbsenceDto> getStudentAbsences() {
        return studentAbsences;
    }

    public void setStudentAbsences(List<StudentGroupAbsenceDto> studentAbsences) {
        this.studentAbsences = studentAbsences;
    }

}
