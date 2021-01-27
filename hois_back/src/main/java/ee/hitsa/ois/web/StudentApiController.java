package ee.hitsa.ois.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.services.StudentApiService;
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
	
	@GetMapping("/immat/{id}")
    public StudentsDto immat(@PathVariable("id") String schoolEhisValue, @Valid StudentCommand command) {
		return studentApiService.immat(schoolEhisValue, command);
    }
	
	@GetMapping("/exmat/{id}")
    public ExmatStudentsDto exmat(@PathVariable("id") String schoolEhisValue, @Valid StudentCommand command) {
		return studentApiService.exmat(schoolEhisValue, command);
    }
	
	@GetMapping("/timetable/{id}")
    public TimetableEventsDto timetable(@PathVariable("id") String schoolEhisValue, @Valid TimetableStudentCommand command) {
		return studentApiService.timetable(schoolEhisValue, command);
	}
	
}
