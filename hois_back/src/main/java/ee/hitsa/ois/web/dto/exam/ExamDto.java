package ee.hitsa.ois.web.dto.exam;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExam;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.exam.ExamForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ExamDto extends ExamForm {

    private final Long id;
    private final SubjectStudyPeriodDto subjectStudyPeriodDto;
    private final String name;
    private final List<ExamStudent> studentRecords;
    private final Boolean userCanEdit;

    public ExamDto(SubjectStudyPeriodExam exam, AutocompleteResult room, List<String> teachers, List<ExamStudent> students, boolean editable, List<String> groups) {
        setSubjectStudyPeriod(EntityUtil.getId(exam.getSubjectStudyPeriod()));
        setStartDate(exam.getTimetableEvent().getStart());
        setStartTime(exam.getTimetableEvent().getStart().toLocalTime());
        setEndTime(exam.getTimetableEvent().getEnd().toLocalTime());
        setType(EntityUtil.getCode(exam.getType()));
        setRoom(room);
        setDeadlineDate(exam.getDeadline());
        setDeadlineTime(exam.getDeadline() != null ? exam.getDeadline().toLocalTime() : null);
        setPlaces(exam.getPlaces());
        setAddInfo(exam.getAddInfo());
        setStudents(StreamUtil.toMappedList(r -> r.getId(), students));
        setVersion(exam.getVersion());

        id = exam.getId();
        subjectStudyPeriodDto = SubjectStudyPeriodDto.of(exam.getSubjectStudyPeriod(), teachers, groups);
        name = exam.getTimetableEvent().getName();
        studentRecords = students;
        userCanEdit = Boolean.valueOf(editable);
    }

    public Long getId() {
        return id;
    }

    public SubjectStudyPeriodDto getSubjectStudyPeriodDto() {
        return subjectStudyPeriodDto;
    }

    public String getName() {
        return name;
    }

    public List<ExamStudent> getStudentRecords() {
        return studentRecords;
    }

    public Boolean getUserCanEdit() {
        return userCanEdit;
    }

    public static class ExamStudent {

        private Long id;
        private Long studentId;
        private String fullname;
        private AutocompleteResult curriculumVersion;
        private String studentGroup;
        private LocalDate registered;
        private Boolean grade;

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

        public AutocompleteResult getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
        }

        public String getStudentGroup() {
            return studentGroup;
        }

        public void setStudentGroup(String studentGroup) {
            this.studentGroup = studentGroup;
        }

        public LocalDate getRegistered() {
            return registered;
        }

        public void setRegistered(LocalDate registered) {
            this.registered = registered;
        }

        public Boolean getGrade() {
            return grade;
        }

        public void setGrade(Boolean grade) {
            this.grade = grade;
        }
    }
}
