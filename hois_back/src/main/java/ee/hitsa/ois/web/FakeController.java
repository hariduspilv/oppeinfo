package ee.hitsa.ois.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.web.dto.PersonDto;


@RestController
@RequestMapping("/fake")
public class FakeController {
    
    @GetMapping("/get505Error")
    public ResponseEntity<PersonDto> get505Error() {
        return new ResponseEntity<>(null, HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    }

}
