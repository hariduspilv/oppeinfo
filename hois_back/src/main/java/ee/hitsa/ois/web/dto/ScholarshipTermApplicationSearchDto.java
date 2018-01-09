package ee.hitsa.ois.web.dto;

import java.util.List;

import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;

public class ScholarshipTermApplicationSearchDto {
    private List<ScholarshipApplicationSearchDto> applications;
    private Long allowedCount;

    public ScholarshipTermApplicationSearchDto(Long allowedCount, List<ScholarshipApplicationSearchDto> applications) {
        this.allowedCount = allowedCount;
        this.applications = applications;
    }

    public List<ScholarshipApplicationSearchDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ScholarshipApplicationSearchDto> applications) {
        this.applications = applications;
    }

    public Long getAllowedCount() {
        return allowedCount;
    }

    public void setAllowedCount(Long allowedCount) {
        this.allowedCount = allowedCount;
    }

}
