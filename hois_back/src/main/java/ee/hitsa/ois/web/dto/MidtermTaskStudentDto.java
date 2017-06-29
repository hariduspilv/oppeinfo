package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;

public class MidtermTaskStudentDto {
    private Long declarationSubject;
    private String name;
    private Boolean studentResultCanBeChanged;

    public static MidtermTaskStudentDto of(DeclarationSubject declarationSubject) {
        MidtermTaskStudentDto dto = new MidtermTaskStudentDto();
        dto.setDeclarationSubject(EntityUtil.getId(declarationSubject));
        dto.setName(declarationSubject.getDeclaration().getStudent().getPerson().getFullname());
        dto.setStudentResultCanBeChanged(Boolean.valueOf(MidtermTaskUtil.studentResultCanBeChanged(declarationSubject)));
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
}
