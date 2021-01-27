package ee.AD.demo;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javax.naming.directory.DirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestService {
    
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Value("${hois.backend.student.endpoint}")
    private String studentEndpoint;
	
	@Value("${hois.school.ehis_value}")
    private Long schoolId;
	
	@Value("${hois.sync.days}")
    private Long syncDays;
	
	@Autowired
	private UserGenerator generator;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    
    /**
     * Get exmatriculated students
     * 
     * @param startFrom required
     * @param startThru optional
     * @return
     */
    public ExmatStudentsDto getExmatStudents(LocalDateTime startFrom, LocalDateTime startThru) {
        String url = studentEndpoint + "/exmat/" + schoolId;
        UriComponentsBuilder builder = buildUrl(url, startFrom, startThru);
        String fullUrl = builder.toUriString();
        log.info(fullUrl);
        HttpEntity<ExmatStudentsDto> response = restTemplate.exchange(fullUrl, HttpMethod.GET, null, ExmatStudentsDto.class);
        return response.getBody();
    }
    
    /**
     * Get immatriculated students
     * 
     * @param startFrom required
     * @param startThru optional
     * @return
     */
    public StudentsDto getImmatStudents(LocalDateTime startFrom, LocalDateTime startThru) {
        String url = studentEndpoint + "/immat/" + schoolId;
        UriComponentsBuilder builder = buildUrl(url, startFrom, startThru);
        String fullUrl = builder.toUriString();
        log.info(fullUrl);
        HttpEntity<StudentsDto> response = restTemplate.exchange(fullUrl, HttpMethod.GET, null, StudentsDto.class);
        StudentsDto students = response.getBody();
        return students;
    }
    
    /**
     * Get timetable
     * 
     * @param startFrom required
     * @param startThru required
     * @param roomName optional
     * @return
     */
    public TimetableEventsDto getTimetableStudents(LocalDateTime startFrom, LocalDateTime startThru, String roomName) {
        String url = studentEndpoint + "/timetable/" + schoolId;
        UriComponentsBuilder builder = buildUrl(url, startFrom, startThru, roomName);
        String fullUrl = builder.toUriString();
        log.info(fullUrl);
        HttpEntity<TimetableEventsDto> response = restTemplate.exchange(fullUrl, HttpMethod.GET, null, TimetableEventsDto.class);
        return response.getBody();
    }
    
    private UriComponentsBuilder buildUrl(String url, LocalDateTime startFrom, LocalDateTime startThru, String roomName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParamIfPresent("startFrom", Optional.ofNullable(startFrom != null ? startFrom.format(formatter) + 'Z' : null))
                .queryParamIfPresent("startThru", Optional.ofNullable(startThru != null ? startThru.format(formatter) + 'Z' : null))
                .queryParamIfPresent("roomName", Optional.ofNullable(roomName));
        return builder;
    }
    
    private UriComponentsBuilder buildUrl(String url, LocalDateTime startFrom, LocalDateTime startThru) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParamIfPresent("startFrom", Optional.ofNullable(startFrom != null ? startFrom.format(formatter) + 'Z' : null))
                .queryParamIfPresent("startThru", Optional.ofNullable(startThru != null ? startThru.format(formatter) + 'Z' : null));
        return builder;
    }
    
    /**
     * Get immatriculated students whose accounts should be created
     * 
     * @return
     */
    public StudentsDto getImmatStudentsForUserCreation() {
        LocalDate now = LocalDate.now();
        LocalDateTime startFrom = now.minusDays(syncDays.longValue()).atStartOfDay();
        LocalDateTime startThru = now.atStartOfDay();
        return getImmatStudents(startFrom, startThru);
    }
    
    /**
     * Get exmatriculated students whose accounts should be disabled
     * 
     * @return
     */
    public ExmatStudentsDto getExmatStudentsForUserDeactivation() {
        LocalDate now = LocalDate.now();
        LocalDateTime startFrom = now.minusDays(syncDays.longValue()).atStartOfDay();
        LocalDateTime startThru = now.atStartOfDay();
        return getExmatStudents(startFrom, startThru);
    }
    
    public StudentsDto createUsers() {
        StudentsDto students = getImmatStudentsForUserCreation();
        createAccounts(students);
        return students;
    }
    
    public ExmatStudentsDto disableUsers() {
        ExmatStudentsDto students = getExmatStudentsForUserDeactivation();
        disableAccounts(students);
        return students;
    }
    
    private void createAccounts(StudentsDto students) {
        List<StudentDto> studentList = students.getStudents();
        for (StudentDto student : studentList) {
            log.info(studentList.indexOf(student) + 1 + ": " + student.toString());
            DirContext dir = generator.createContext();
            String username = null;
            String password = null;
            if (student.getEmail() != null) {
                int index = student.getEmail().indexOf('@');
                if (index != -1) {
                    username = student.getEmail().substring(0, index);
                    username = username.toLowerCase().replace("-", "").replace("..", ".");
                    if (username.length() > 20) {
                        if (student.getIdcode() != null) {
                            username = student.getIdcode();
                        } else {
                            username = student.getUqcode();
                        }
                    }
                }
            } else {
                log.error(Messages.CANNOT_CREATE_ERROR, "email missing");
            }
            if (student.getIdcode() != null) {
                password = student.getIdcode();
            } else {
                password = student.getUqcode();
            }
            if (password == null) {
                log.error(Messages.CANNOT_CREATE_ERROR, "unique code and Id code missing for password");
            }
            if (username != null && password != null && dir != null) {
                try {
                    generator.createUser(username, password, student,dir);
                } catch (Exception e) {
                    log.error(Messages.CANNOT_CREATE_ERROR, username, e);
                }
            }
        }
    }
    
    private void disableAccounts(ExmatStudentsDto students) {
        List<StudentBaseDto> studentList = students.getStudents();
        for (StudentBaseDto student : studentList) {
            log.info(studentList.indexOf(student) + 1 + ": " + student.toString());
            DirContext dir = generator.createContext();
            String username = null;
            if (student.getEmail() != null) {
                int index = student.getEmail().indexOf('@');
                if (index != -1) {
                    username = student.getEmail().substring(0, index);
                    username = username.toLowerCase().replace("-", "").replace("..", ".");
                    if (username.length() > 20) {
                        if (student.getIdcode() != null) {
                            username = student.getIdcode();
                        } else {
                            username = student.getUqcode();
                        }
                    }
                }
                
            } else {
                log.error(Messages.CANNOT_DISABLE_ERROR, "email missing");
            }
            if (username != null && dir != null) {
                try {
                    generator.disableUser(username, dir);
                } catch (Exception e) {
                    log.error(Messages.CANNOT_DISABLE_ERROR, username, e);
                }
            }
        }
    }

}
