package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.Translatable;

public class SubjectStudyPeriodSubgroupDto extends SubjectStudyPeriodSubgroupForm implements Translatable {

    private Integer declared;
    
    private Boolean canDelete = Boolean.FALSE;

    public static SubjectStudyPeriodSubgroupDto of(SubjectStudyPeriodSubgroup subgroup) {
        SubjectStudyPeriodSubgroupDto dto = new SubjectStudyPeriodSubgroupDto();
        EntityUtil.bindToDto(subgroup, dto, "teacher");
        dto.setTeacher(subgroup.getTeacher() != null ?
                AutocompleteResult.of(subgroup.getTeacher().getTeacher()) : null);
        dto.setDeclared(Integer.valueOf(subgroup.getDeclarationSubjects().size()));
        if (subgroup.getDeclarationSubjects().isEmpty()) {
            dto.setCanDelete(Boolean.TRUE);
        }
        return dto;
    }
    
    public Integer getDeclared() {
        return declared;
    }

    public void setDeclared(Integer declared) {
        this.declared = declared;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public String getNameEt() {
        if (getTeacher() == null) {
            return getCode();
        }
        return String.format("%s (%s)", getCode(), getTeacher().getNameEt());
    }

    @Override
    public String getNameEn() {
        if (getTeacher() == null) {
            return getCode();
        }
        return String.format("%s (%s)", getCode(), getTeacher().getNameEn());
    }
}
