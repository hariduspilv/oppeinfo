package ee.hitsa.ois.web.dto;

public class SchoolDepartmentResult extends AutocompleteResult {

    private Long schoolId;
    private String schoolCode;

    public SchoolDepartmentResult(Long id, String nameEt, String nameEn, Long schoolId, String schoolCode) {
        super(id, nameEt, nameEn);

        this.schoolId = schoolId;
        this.schoolCode = schoolCode;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }
}
