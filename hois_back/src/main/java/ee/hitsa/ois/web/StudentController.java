package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.web.commandobject.StudentSearchCommand;
import ee.hitsa.ois.web.dto.StudentSearchDto;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Page<StudentSearchDto> search(StudentSearchCommand criteria, Pageable pageable) {
        return studentService.search(criteria, pageable).map(StudentSearchDto::of);
    }
}
