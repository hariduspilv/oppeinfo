package ee.hois.moodle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hois.moodle.dto.EnrollResponse;
import ee.hois.moodle.dto.ErrorResponse;
import ee.hois.moodle.dto.Grade;
import ee.hois.moodle.dto.GradeItem;
import ee.hois.moodle.dto.GradeItemsResponse;
import ee.hois.moodle.dto.GradesByItemIdResponse;
import ee.hois.moodle.dto.MoodleResponse;

public class MoodleClient {
    
    private static final String VALID = "VALID";

    private Cipher cipherInstance;
    private Key salt;
    private IvParameterSpec vector;
    private Signature signatureInstance;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private RestTemplate restTemplate;
    private ObjectMapper mapperInstance;

    public Boolean courseLinkPossible(Config config, LogContext log, String idcode, Long courseId, 
            List<String> academicianIds) {
        try {
            final String method = "courseLinkPossible";
            log.setQueryName(method);
            MoodleRequestParams requestParams = new MoodleRequestParams(method, idcode, courseId, academicianIds);
            String requestMessage = requestParams.getRequestMessage(config);
            log.setRequest(requestMessage);
            MoodleResponse response = post(config, requestMessage);
            if (response.getSignature() == null) {
                log.setResponse(response.getMessage());
                return Boolean.FALSE;
            }
            String decryptedMessage = decrypt(config, response);
            log.setResponse(decryptedMessage);
            return Boolean.valueOf(VALID.equals(getMapper().readValue(decryptedMessage, Map.class).get("message")));
        } catch (MoodleException e) {
            throw e;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    public EnrollResponse enrollStudents(Config config, LogContext log, String idcode, Long courseId, 
            List<String> academicianIds, List<String> studentIds) {
        try {
            final String method = "enrollStudents";
            log.setQueryName(method);
            MoodleRequestParams requestParams = new MoodleRequestParams(method, idcode, courseId, academicianIds);
            requestParams.appendStudents(studentIds);
            String decryptedMessage = post(config, log, requestParams);
            try {
                return getMapper().readValue(decryptedMessage, EnrollResponse.class);
            } catch (@SuppressWarnings("unused") JsonMappingException e) {
                throw new MoodleException(getMapper().readValue(decryptedMessage, ErrorResponse.class));
            }
        } catch (MoodleException e) {
            throw e;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    public List<GradeItem> getGradeItems(Config config, LogContext log, String idcode, Long courseId, 
            List<String> academicianIds) {
        try {
            final String method = "getGradeItems";
            log.setQueryName(method);
            MoodleRequestParams requestParams = new MoodleRequestParams(method, idcode, courseId, academicianIds);
            String decryptedMessage = post(config, log, requestParams);
            try {
                GradeItemsResponse response = getMapper().readValue(decryptedMessage, GradeItemsResponse.class);
                return response.getGradeItems();
            } catch (@SuppressWarnings("unused") JsonMappingException e) {
                throw new MoodleException(getMapper().readValue(decryptedMessage, ErrorResponse.class));
            }
        } catch (MoodleException e) {
            throw e;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    public Map<Long, List<Grade>> getGradesByItemId(Config config, LogContext log, String idcode, Long courseId, 
            List<String> academicianIds, List<Long> gradeItemIds, List<String> studentIds) {
        try {
            final String method = "getGradesByItemId";
            log.setQueryName(method);
            MoodleRequestParams requestParams = new MoodleRequestParams(method, idcode, courseId, academicianIds);
            requestParams.appendGradeItems(gradeItemIds);
            requestParams.appendStudents(studentIds);
            String decryptedMessage = post(config, log, requestParams);
            try {
                GradesByItemIdResponse response = getMapper().readValue(decryptedMessage, GradesByItemIdResponse.class);
                return response.getGrades();
            } catch (@SuppressWarnings("unused") JsonMappingException e) {
                throw new MoodleException(getMapper().readValue(decryptedMessage, ErrorResponse.class));
            }
        } catch (MoodleException e) {
            throw e;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    private Cipher getCipher() {
        if (cipherInstance == null) {
            try {
                cipherInstance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (GeneralSecurityException e) {
                throw new MoodleException(e);
            }
        }
        return cipherInstance;
    }
    
    private Key getSalt(Config config) {
        if (salt == null) {
            byte[] saltInput = config.getSalt().getBytes(StandardCharsets.UTF_8);
            byte[] key256 = new byte[32];
            System.arraycopy(saltInput, 0, key256, 0, key256.length);
            salt = new SecretKeySpec(key256, "AES");
        }
        return salt;
    }
    
    private IvParameterSpec getVector(Config config) {
        if (vector == null) {
            vector = new IvParameterSpec(config.getVector().getBytes(StandardCharsets.UTF_8));
        }
        return vector;
    }
    
    private Signature getSignature() {
        if (signatureInstance == null) {
            try {
                signatureInstance = Signature.getInstance("SHA1withRSA");
            } catch (NoSuchAlgorithmException e) {
                throw new MoodleException(e);
            }
        }
        return signatureInstance;
    }
    
    private PrivateKey getPrivateKey(Config config) {
        if (privateKey == null) {
            try {
                privateKey = KeyFactory.getInstance("RSA")
                        .generatePrivate(new PKCS8EncodedKeySpec(readPemKey(config.getPrivateKey())));
            } catch (Exception e) {
                throw new MoodleException(e);
            }
        }
        return privateKey;
    }
    
    private PublicKey getPublicKey(Config config) {
        if (publicKey == null) {
            try (InputStream is = new FileInputStream(config.getCertificate())) {
                publicKey = CertificateFactory.getInstance("X.509")
                        .generateCertificate(is)
                        .getPublicKey();
            } catch (Exception e) {
                throw new MoodleException(e);
            }
        }
        return publicKey;
    }
    
    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }
    
    private ObjectMapper getMapper() {
        if (mapperInstance == null) {
            mapperInstance = new ObjectMapper();
        }
        return mapperInstance;
    }

    private String post(Config config, LogContext log, MoodleRequestParams requestParams) throws Exception {
        String requestMessage = requestParams.getRequestMessage(config);
        log.setRequest(requestMessage);
        MoodleResponse response = post(config, requestMessage);
        String decryptedMessage = decrypt(config, response);
        log.setResponse(decryptedMessage);
        return decryptedMessage;
    }

    private MoodleResponse post(Config config, String requestMessage) throws Exception {
        MoodleRequest request = new MoodleRequest();
        request.setMessage(Base64.getEncoder().encodeToString(
                crypt(config, Cipher.ENCRYPT_MODE, requestMessage.getBytes(StandardCharsets.UTF_8))));
        request.setSignature(createSignature(config, requestMessage));
        
        ResponseEntity<String> response = getRestTemplate().postForEntity(location(config), request, String.class);
        
        if (HttpStatus.OK.value() != response.getStatusCodeValue()) {
            throw new MoodleException("Moodle response status code: " + response.getStatusCodeValue());
        }
        ObjectMapper mapper = getMapper();
        MoodleResponse result = mapper.readValue(mapper.readValue(response.getBody(), String.class), MoodleResponse.class);
        if (result.getMessage() == null) {
            throw new MoodleException("Moodle response message: null");
        }
        return result;
    }

    private String decrypt(Config config, MoodleResponse response) throws Exception {
        if (response.getSignature() == null) {
            throw new MoodleException("Moodle response message is missing signature");
        }
        String decryptedMessage = new String(crypt(config, Cipher.DECRYPT_MODE, 
                Base64.getDecoder().decode(response.getMessage().getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        if (!validateSignature(config, decryptedMessage, response.getSignature())) {
            throw new MoodleException("Moodle response message signature is not valid");
        }
        return decryptedMessage;
    }

    private byte[] crypt(Config config, int opmode, byte[] input) throws GeneralSecurityException {
        Cipher cipher = getCipher();
        cipher.init(opmode, getSalt(config), getVector(config));
        return cipher.doFinal(input);
    }

    private String createSignature(Config config, String message) throws Exception {
        Signature dsa = getSignature();
        dsa.initSign(getPrivateKey(config));
        dsa.update(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(dsa.sign());
    }

    private boolean validateSignature(Config config, String message, String signature) throws Exception {
        Signature dsa = getSignature();
        dsa.initVerify(getPublicKey(config));
        dsa.update(message.getBytes(StandardCharsets.UTF_8));
        return dsa.verify(Base64.getDecoder().decode(signature));
    }

    private static byte[] readPemKey(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        lines.remove(0);
        lines.remove(lines.size() - 1);
        StringBuilder builder = new StringBuilder(1024);
        for (String line : lines) {
            builder.append(line);
        }
        return Base64.getDecoder().decode(builder.toString().getBytes(StandardCharsets.UTF_8));
    }
    
    private static String location(Config config) {
        StringBuilder url = new StringBuilder(config.getAddress());
        String path = config.getPluginPath();
        if (hasText(path)) {
            url.append(path);
        }
        String name = config.getPluginName();
        if (hasText(name)) {
            url.append(name);
        }
        return url.toString();
    }

    private static boolean hasText(String text) {
        return text != null && text.trim().length() > 0;
    }
}
