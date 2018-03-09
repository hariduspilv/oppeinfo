package ee.hois.moodle;

public class Grade {
    private Object points;
    private String student;
    private String comment;
    
    public Object getPoints() {
        return points;
    }
    public void setPoints(Object points) {
        this.points = points;
    }
    
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
