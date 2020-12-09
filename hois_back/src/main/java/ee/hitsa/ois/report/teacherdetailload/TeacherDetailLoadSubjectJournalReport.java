package ee.hitsa.ois.report.teacherdetailload;

import java.util.List;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadDto;

public class TeacherDetailLoadSubjectJournalReport {
	
	private Boolean isHigher;
	private Boolean isHigherSchool;
	private StudyYear studyYear;
	private StudyPeriod studyPeriod;
	private List<TeacherDetailLoadDto> teachers;
	
	public Boolean getIsHigher() {
		return isHigher;
	}
	public void setIsHigher(Boolean isHigher) {
		this.isHigher = isHigher;
	}
	public Boolean getIsHigherSchool() {
		return isHigherSchool;
	}
	public void setIsHigherSchool(Boolean isHigherSchool) {
		this.isHigherSchool = isHigherSchool;
	}
	public StudyYear getStudyYear() {
		return studyYear;
	}
	public void setStudyYear(StudyYear studyYear) {
		this.studyYear = studyYear;
	}
	public StudyPeriod getStudyPeriod() {
		return studyPeriod;
	}
	public void setStudyPeriod(StudyPeriod studyPeriod) {
		this.studyPeriod = studyPeriod;
	}
	public List<TeacherDetailLoadDto> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<TeacherDetailLoadDto> teachers) {
		this.teachers = teachers;
	}

}
