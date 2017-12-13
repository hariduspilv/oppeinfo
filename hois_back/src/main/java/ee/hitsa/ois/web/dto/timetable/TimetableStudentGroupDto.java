package ee.hitsa.ois.web.dto.timetable;

public class TimetableStudentGroupDto {
    protected Long id;
    protected String code;
    protected Long curriculumId;

    public TimetableStudentGroupDto(Long id, String code, Long curriculumId) {
        this.id = id;
        this.code = code;
        this.curriculumId = curriculumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }
    
    @Override 
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!TimetableStudentGroupDto.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TimetableStudentGroupDto other = (TimetableStudentGroupDto) obj;
        if(other.getId() == null || this.getId() == null) {
            return false;
        }
        if(this.id == null || other.id == null) {
            return false;
        }
        if(this.id.longValue() != other.id.longValue()) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.id == null ? 31 : this.id.hashCode();
    }

}