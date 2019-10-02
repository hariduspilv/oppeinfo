package ee.hitsa.ois.web.dto.poll.xls;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollCommentXlsDto {
    
    private Long pollId;
    private AutocompleteResult name;
    private String subjectCode;
    private String comment;
    private String teacher;
    
    public Long getPollId() {
        return pollId;
    }
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

}
