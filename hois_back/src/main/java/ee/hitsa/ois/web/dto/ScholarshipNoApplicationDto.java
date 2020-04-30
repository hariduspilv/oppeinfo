package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.ScholarshipNoApplication;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipNoApplicationDto {

    private List<String> allowedEhisTypes = new ArrayList<>();

    public static ScholarshipNoApplicationDto of(School school) {
        ScholarshipNoApplicationDto dto = new ScholarshipNoApplicationDto();
        dto.getAllowedEhisTypes().addAll(school.getScholarshipNoApplicationTypes().stream()
                .map(ScholarshipNoApplication::getScholarshipEhis).map(EntityUtil::getCode).collect(Collectors.toList()));
        return dto;
    }
    
    public List<String> getAllowedEhisTypes() {
        return allowedEhisTypes;
    }

    public void setAllowedEhisTypes(List<String> allowedEhisTypes) {
        this.allowedEhisTypes = allowedEhisTypes;
    }
}
