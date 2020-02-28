package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.PersonUtil;

public class MidtermTaskStudentDto {
    private Long declarationSubject;
    private String name;
    private String studentGroup;

    // used for ordering in frontend
    private String firstname;
    private String lastname;

    private Boolean studentResultCanBeChanged;
    private Long studentId;
    private Boolean isMoodleRegistered;
    private AutocompleteResult subgroup;

    public static MidtermTaskStudentDto of(DeclarationSubject declarationSubject) {
        MidtermTaskStudentDto dto = new MidtermTaskStudentDto();
        dto.setDeclarationSubject(EntityUtil.getId(declarationSubject));

        Student student = declarationSubject.getDeclaration().getStudent();
        dto.setName(PersonUtil.fullname(student));
        dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
        Person person = student.getPerson();
        dto.setFirstname(person.getFirstname());
        dto.setLastname(person.getLastname());

        dto.setStudentResultCanBeChanged(Boolean.valueOf(MidtermTaskUtil.studentResultCanBeChanged(declarationSubject)));
        dto.setStudentId(EntityUtil.getId(declarationSubject.getDeclaration().getStudent()));
        dto.setIsMoodleRegistered(declarationSubject.getIsMoodleRegistered());
        dto.setSubgroup(declarationSubject.getSubgroup() != null ? AutocompleteResult.of(declarationSubject.getSubgroup()) : null);
        return dto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((declarationSubject == null) ? 0 : declarationSubject.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MidtermTaskStudentDto other = (MidtermTaskStudentDto) obj;
        if (declarationSubject == null) {
            if (other.declarationSubject != null)
                return false;
        } else if (!declarationSubject.equals(other.declarationSubject))
            return false;
        return true;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getStudentResultCanBeChanged() {
        return studentResultCanBeChanged;
    }

    public void setStudentResultCanBeChanged(Boolean studentResultCanBeChanged) {
        this.studentResultCanBeChanged = studentResultCanBeChanged;
    }

    public Long getDeclarationSubject() {
        return declarationSubject;
    }

    public void setDeclarationSubject(Long declarationSubject) {
        this.declarationSubject = declarationSubject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean getIsMoodleRegistered() {
        return isMoodleRegistered;
    }

    public void setIsMoodleRegistered(Boolean isMoodleRegistered) {
        this.isMoodleRegistered = isMoodleRegistered;
    }

    public AutocompleteResult getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(AutocompleteResult subgroup) {
        this.subgroup = subgroup;
    }
}
