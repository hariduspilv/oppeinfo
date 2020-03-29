package ee.hitsa.ois.xml.timetable.asc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "studentsubject")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscStudentSubject {

    private AscStudent student;
    private AscSubject subject;
    private String seminarGroup;
    private String importance;
    private String alternateFor;

    @XmlAttribute(name = "studentid")
    @XmlIDREF
    public AscStudent getStudent() {
        return student;
    }

    public void setStudent(AscStudent student) {
        this.student = student;
    }

    @XmlAttribute(name = "subjectid")
    @XmlIDREF
    public AscSubject getSubject() {
        return subject;
    }

    public void setSubject(AscSubject subject) {
        this.subject = subject;
    }

    @XmlAttribute(name = "seminargroup")
    public String getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(String seminarGroup) {
        this.seminarGroup = seminarGroup;
    }

    @XmlAttribute(name = "importance")
    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    @XmlAttribute(name = "alternatefor")
    public String getAlternateFor() {
        return alternateFor;
    }

    public void setAlternateFor(String alternateFor) {
        this.alternateFor = alternateFor;
    }
}
