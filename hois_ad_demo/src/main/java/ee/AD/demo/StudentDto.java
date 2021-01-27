package ee.AD.demo;

import java.util.stream.Collectors;

public class StudentDto extends StudentBaseDto {
    
	private String type;
	private String personalEmail;
	private String phone;
	private String schoolDepartment;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
    public String getSchoolDepartment() {
        return schoolDepartment;
    }
    public void setSchoolDepartment(String schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }
    
    @Override
    public String toString() {
        return (getFirstname() != null ? "firstname:" + getFirstname() + ", " : "")
                + (getLastname() != null ? "lastname:" + getLastname() + ", " : "")
                + (getIdcode() != null ? "idcode:" + getIdcode() + ", " : "")
                + (getUqcode() != null ? "uqcode:" + getUqcode() + ", " : "")
                + (getCurriculumMerCode() != null ? "curriculumMerCode:" + getCurriculumMerCode() + ", " : "")
                + (getCurriculumNameEt() != null ? "curriculumNameEt:" + getCurriculumNameEt() + ", " : "")
                + (getCurriculumVersionCode() != null
                        ? "curriculumVersionCode:" + getCurriculumVersionCode() + ", "
                        : "")
                + (getEmail() != null ? "email:" + getEmail() + ", " : "")
                + (getBirthdate() != null ? "birthdate:" + getBirthdate() + ", " : "")
                + (type != null ? "type:" + type + ", " : "")
                + (personalEmail != null ? "personalEmail:" + personalEmail + ", " : "")
                + (phone != null ? "phone:" + phone + ", " : "")
                + (schoolDepartment != null ? "schoolDepartment:" + schoolDepartment : "");
    }
}
