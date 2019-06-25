package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveStudentSearchDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult curriculumVersion;
    private String studentGroup;
    private List<ApplicationMinDto> applications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public List<ApplicationMinDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationMinDto> applications) {
        this.applications = applications;
    }
    
    public static class ApplicationMinDto {
        
        private Long id;
        private String description;
        private LocalDateTime confirmed;
        
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public LocalDateTime getConfirmed() {
            return confirmed;
        }
        public void setConfirmed(LocalDateTime confirmed) {
            this.confirmed = confirmed;
        }
    }
}
