package ee.hitsa.ois.web.dto;

import java.util.List;

import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationRankingSearchDto;

public class ScholarshipTermApplicationRankingSearchDto {
    private List<ScholarshipApplicationRankingSearchDto> applications;
    private Long allowedCount;

    public ScholarshipTermApplicationRankingSearchDto(Long allowedCount, List<ScholarshipApplicationRankingSearchDto> applications) {
        this.allowedCount = allowedCount;
        this.applications = applications;
    }

    public List<ScholarshipApplicationRankingSearchDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ScholarshipApplicationRankingSearchDto> applications) {
        this.applications = applications;
    }

    public Long getAllowedCount() {
        return allowedCount;
    }

    public void setAllowedCount(Long allowedCount) {
        this.allowedCount = allowedCount;
    }

}
