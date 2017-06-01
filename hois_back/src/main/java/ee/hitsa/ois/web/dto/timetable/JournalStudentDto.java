package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;

public class JournalStudentDto {

    private Long id;
    private Long studentId;
    private String fullname;
    private String studentGroup;
    private String curriculum;

    public static JournalStudentDto of(Student student) {
        JournalStudentDto dto = new JournalStudentDto();
        dto.setStudentId(student.getId());
        dto.setFullname(PersonUtil.fullname(student.getPerson()));
        if (student.getStudentGroup() != null) {
            dto.setStudentGroup(student.getStudentGroup().getCode());
        }
        if (student.getCurriculumVersion() != null) {
            dto.setCurriculum(CurriculumUtil.versionName(student.getCurriculumVersion().getCode(),
                    student.getCurriculumVersion().getCurriculum().getNameEt()));
        }
        return dto;
    }

    public static JournalStudentDto of(JournalStudent journalStudent) {
        JournalStudentDto dto = of(journalStudent.getStudent());
        dto.setId(journalStudent.getId());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }


}
