package ee.hitsa.ois.web.dto.student;

import java.time.LocalTime;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRemark;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentRemarkDto extends StudentRemarkForm {

    private Long id;
    private Long journalStudentEntryId;
    private String remarkInsertedBy;
    private String studentGroup;
    private AutocompleteResult journal;

    public static StudentRemarkDto of(StudentRemark studentRemark) {
        StudentRemarkDto dto = EntityUtil.bindToDto(studentRemark, new StudentRemarkDto(), "student", "remarkTime");
        Student s = studentRemark.getStudent();
        String fullname = PersonUtil.fullname(s.getPerson());
        dto.setStudent(new AutocompleteResult(s.getId(), fullname, fullname));
        dto.setRemarkDate(studentRemark.getRemarkTime().toLocalDate());
        dto.setRemarkTime(studentRemark.getRemarkTime().toLocalTime());
        dto.setRemarkInsertedBy(PersonUtil.idcodeFromFullnameAndIdcode(studentRemark.getInsertedBy()));
        dto.setStudentGroup(s.getStudentGroup() != null ? s.getStudentGroup().getCode() : null);
        return dto;
    }

    public static StudentRemarkDto of(JournalEntryStudent entry) {
        StudentRemarkDto dto = new StudentRemarkDto();
        dto.setJournalStudentEntryId(entry.getId());
        Student s = entry.getJournalStudent().getStudent();
        String fullname = PersonUtil.fullname(s.getPerson());
        dto.setStudent(new AutocompleteResult(s.getId(), fullname, fullname));

        dto.setRemarkDate(entry.getRemarkInserted().toLocalDate());
        LocalTime time = entry.getRemarkInserted().toLocalTime();
        dto.setRemarkTime(LocalTime.of(time.getHour(), time.getMinute()));

        dto.setRemark(entry.getAddInfo());
        dto.setRemarkInsertedBy(PersonUtil.idcodeFromFullnameAndIdcode(entry.getRemarkInsertedBy()));
        dto.setStudentGroup(s.getStudentGroup() != null ? s.getStudentGroup().getCode() : null);
        dto.setJournal(AutocompleteResult.of(entry.getJournalEntry().getJournal()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJournalStudentEntryId() {
        return journalStudentEntryId;
    }

    public void setJournalStudentEntryId(Long journalStudentEntryId) {
        this.journalStudentEntryId = journalStudentEntryId;
    }

    public String getRemarkInsertedBy() {
        return remarkInsertedBy;
    }

    public void setRemarkInsertedBy(String remarkInsertedBy) {
        this.remarkInsertedBy = remarkInsertedBy;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

}
