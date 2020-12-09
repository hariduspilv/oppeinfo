package ee.hitsa.ois.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.api.StudentApiService;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.api.StudentCommand;
import ee.hitsa.ois.web.commandobject.api.TimetableStudentCommand;
import ee.hitsa.ois.web.dto.api.ExmatStudentsDto;
import ee.hitsa.ois.web.dto.api.StudentsDto;
import ee.hitsa.ois.web.dto.api.TimetableEventsDto;

@RestController
@RequestMapping("/api/student")
public class StudentApiController {
	
	@Autowired
	private StudentApiService studentApiService;
	
	@GetMapping("/immat/{id:\\d+}")
    public StudentsDto immat(@WithEntity School school, @Valid StudentCommand command) {
		return studentApiService.immat(school, command);
    }
	
	@GetMapping("/exmat/{id:\\d+}")
    public ExmatStudentsDto exmat(@WithEntity School school, @Valid StudentCommand command) {
		return studentApiService.exmat(school, command);
    }
	
	@GetMapping("/timetable/{id:\\d+}")
    public TimetableEventsDto timetable(@WithEntity School school, @Valid TimetableStudentCommand command) {
		return studentApiService.timetable(school, command);
	}
	
}
