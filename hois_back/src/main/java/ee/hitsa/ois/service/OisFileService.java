package ee.hitsa.ois.service;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        QUERIES.put("supportservice", "select sss.oisFile from StudentSupportService sss where sss.oisFile.id = ?1");
        QUERIES.put("pollThemeQuestionFile", "select ptqf.oisFile from PollThemeQuestionFile ptqf where ptqf.oisFile.id = ?1");
    }

    @Autowired
    private EntityManager em;
    
    // 128 bit key(16 letters)
    private static String key;
    
    public static String getKey() {
    	return key;
    }
    
    @Value("${file.cypher.key}")
    public void setKey(String keyFromProps) {
        key = keyFromProps;
    }
    
    public static Long getId(String id) {
        if (id == null) return null;
        byte[] deCoded = Base64.decodeBase64(id);
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            id = new String(cipher.doFinal(deCoded));
        } catch(@SuppressWarnings("unused") Exception e) {
            return null;
        }
        return Long.valueOf(id);
    }

    public void get(String type, String id, HttpServletResponse response) throws IOException {
    	byte[] deCoded = Base64.decodeBase64(id);
        String sql = QUERIES.get(type);
        if(sql == null || id == null) {
            // wrong type or missing id
            throw new AssertionFailedException("Bad parameters for file get operation");
        }
        try {
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, aesKey);
	        id = new String(cipher.doFinal(deCoded));
        } catch(@SuppressWarnings("unused") Exception e) {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        // TODO additional checks based on user role
        List<OisFile> file = null;
        try {
        	file = em.createQuery(sql, OisFile.class)
                    .setParameter(1, Long.valueOf(id))
                    .setMaxResults(1).getResultList();
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        if(file == null || file.isEmpty()) {
            throw new EntityNotFoundException();
        }
        OisFile oisFile = file.get(0);
        HttpUtil.file(response, oisFile.getFname(), oisFile.getFtype(), oisFile.getFdata());
    }
}
