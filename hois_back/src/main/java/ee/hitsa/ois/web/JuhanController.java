package ee.hitsa.ois.web;

import ee.hitsa.ois.services.JuhanService;
import ee.hitsa.ois.web.commandobject.juhan.JuhanEventsForm;
import ee.hitsa.ois.web.commandobject.juhan.JuhanRoomCommand;
import ee.hitsa.ois.web.commandobject.juhan.JuhanTeacherCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/juhan")
public class JuhanController {

    @Autowired
    private JuhanService juhanService;

    @GetMapping("/teacher/{schoolId:\\d+}")
    public ResponseEntity<Map<String, Object>> teacher(@PathVariable Long schoolId, @Valid JuhanTeacherCommand criteria) {
        return juhanService.teacher(schoolId, criteria);
    }

    @GetMapping("/buildings/{schoolId:\\d+}")
    public Map<String, Object> buildings(@PathVariable Long schoolId) {
        return juhanService.buildings(schoolId);
    }

    @GetMapping("/rooms/{schoolId:\\d+}")
    public Map<String, Object> rooms(@PathVariable Long schoolId, @Valid JuhanRoomCommand criteria) {
        return juhanService.rooms(schoolId, criteria);
    }

    @PostMapping("/events")
    public ResponseEntity<Map<String, Object>> events(@Valid @RequestBody JuhanEventsForm eventsForm) {
        return juhanService.events(eventsForm);
    }

}
