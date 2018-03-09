package ee.hitsa.ois.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.config.MoodleProperties;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hois.moodle.EnrollResponse;
import ee.hois.moodle.Grade;
import ee.hois.moodle.GradeItem;
import ee.hois.moodle.MoodleClient;

@Service
public class MoodleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private MoodleProperties properties;
    @Autowired
    private MoodleClient client;
    
    public boolean courseLinkPossible(HoisUserDetails user, Long courseId, List<String> academicianIds) {
        return client.courseLinkPossible(properties, getIdcode(user), courseId, academicianIds);
    }

    public EnrollResponse enrollStudents(HoisUserDetails user, Long courseId, List<String> academicianIds, 
            List<String> studentIds) {
        return client.enrollStudents(properties, getIdcode(user), courseId, academicianIds, studentIds);
    }

    public List<String> getEnrolledUsers(HoisUserDetails user, Long courseId, List<String> academicianIds) {
        return client.getEnrolledUsers(properties, getIdcode(user), courseId, academicianIds);
    }

    public List<GradeItem> getGradeItems(HoisUserDetails user, Long courseId, List<String> academicianIds) {
        return client.getGradeItems(properties, getIdcode(user), courseId, academicianIds);
    }

    public Map<Long, List<Grade>> getGradesByItemId(HoisUserDetails user, Long courseId, List<String> academicianIds, 
            List<Long> gradeItemIds, List<String> studentIds) {
        return client.getGradesByItemId(properties, getIdcode(user), courseId, academicianIds,
                gradeItemIds, studentIds);
    }
    
    private String getIdcode(HoisUserDetails user) {
        return em.getReference(Person.class, user.getPersonId()).getIdcode();
    }
    
}
