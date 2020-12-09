package ee.hitsa.ois.web.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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

}
