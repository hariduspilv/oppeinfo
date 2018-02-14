package ee.hitsa.ois.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.util.HttpUtil;

@Service
@Transactional
public class OisFileService {

    private static final Map<String, String> QUERIES = new HashMap<>();
    static {
        QUERIES.put("apelapplication", "select aaf.oisFile from ApelApplicationFile aaf where aaf.oisFile.id = ?1");
        QUERIES.put("application", "select af.oisFile from ApplicationFile af where af.oisFile.id = ?1");
        QUERIES.put("curriculum", "select cf.oisFile from CurriculumFile cf where cf.oisFile.id = ?1");
        QUERIES.put("practicejournal", "select pjf.oisFile from PracticeJournalFile pjf where pjf.oisFile.id = ?1");
        QUERIES.put("protocol", "select p.oisFile from Protocol p where p.oisFile.id = ?1");
        QUERIES.put("scholarshipapplication", "select saf.oisFile from ScholarshipApplicationFile saf where saf.oisFile.id = ?1");
        QUERIES.put("school", "select s.logo from School s where s.logo.id = ?1");
        QUERIES.put("student", "select s.photo from Student s where s.photo.id = ?1");
        QUERIES.put("studymaterial", "select m.oisFile from StudyMaterial m where m.oisFile.id = ?1");
    }

    @Autowired
    private EntityManager em;

    public void get(String type, Long id, HttpServletResponse response) throws IOException {
        String sql = QUERIES.get(type);
        if(sql == null || id == null) {
            // wrong type or missing id
            throw new AssertionFailedException("Bad parameters for file get operation");
        }

        // TODO additional checks based on user role
        List<OisFile> file = em.createQuery(sql, OisFile.class)
            .setParameter(1, id)
            .setMaxResults(1).getResultList();
        if(file.isEmpty()) {
            throw new EntityNotFoundException();
        }
        OisFile oisFile = file.get(0);
        HttpUtil.file(response, oisFile.getFname(), oisFile.getFtype(), oisFile.getFdata());
    }
}
