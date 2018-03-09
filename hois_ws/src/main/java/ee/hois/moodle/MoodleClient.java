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
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MoodleClient {
    
    private static final String VALID = "VALID";

    private Cipher cipher;
    private Key salt;
    private IvParameterSpec vector;
    private Signature signature;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    public boolean courseLinkPossible(Config config, String idcode, Long courseId, List<String> academicianIds) {
        try {
            MoodleRequestParams requestParams = new MoodleRequestParams("courseLinkPossible", idcode, courseId, academicianIds);
            MoodleResponse response = post(config, requestParams);
            if (response.getSignature() == null) {
                return false;
            }
            String decryptedMessage = decrypt(config, response);
            return VALID.equals(getMapper().readValue(decryptedMessage, Map.class).get("message"));
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    public EnrollResponse enrollStudents(Config config, String idcode, Long courseId, List<String> academicianIds, List<String> studentIds) {
        try {
            MoodleRequestParams requestParams = new MoodleRequestParams("enrollStudents", idcode, courseId, academicianIds);
            for (String studentId : studentIds) {
                requestParams.append("studentIds[]", studentId);
            }
            MoodleResponse response = post(config, requestParams);
            String decryptedMessage = decrypt(config, response);
            return getMapper().readValue(decryptedMessage, EnrollResponse.class);
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getEnrolledUsers(Config config, String idcode, Long courseId, List<String> academicianIds) {
        try {
            MoodleRequestParams requestParams = new MoodleRequestParams("getEnrolledUsers", idcode, courseId, academicianIds);
            MoodleResponse response = post(config, requestParams);
            String decryptedMessage = decrypt(config, response);
            List<Map<String, String>> responseList = getMapper().readValue(decryptedMessage, List.class);
            List<String> result = new ArrayList<>();
            for (Map<String, String> user : responseList) {
                result.add(user.get("idnumber"));
            }
            return result;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<GradeItem> getGradeItems(Config config, String idcode, Long courseId, List<String> academicianIds) {
        try {
            MoodleRequestParams requestParams = new MoodleRequestParams("getGradeItems", idcode, courseId, academicianIds);
            MoodleResponse response = post(config, requestParams);
            String decryptedMessage = decrypt(config, response);
            Map<String, List<Map<String, String>>> responseMap = getMapper().readValue(decryptedMessage, Map.class);
            List<GradeItem> result = new ArrayList<>();
            for (Map<String, String> itemMap : responseMap.get("gradeitems")) {
                GradeItem item = new GradeItem();
                item.setId(Long.parseLong(itemMap.get("id")));
                item.setType(itemMap.get("itemtype"));
                item.setName(itemMap.get("itemname"));
                item.setPass(Double.parseDouble(itemMap.get("gradepass")));
                item.setMax(Double.parseDouble(itemMap.get("grademax")));
                result.add(item);
            }
            return result;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<Long, List<Grade>> getGradesByItemId(Config config, String idcode, Long courseId, List<String> academicianIds, 
            List<Long> gradeItemIds, List<String> studentIds) {
        try {
            MoodleRequestParams requestParams = new MoodleRequestParams("getGradesByItemId", idcode, courseId, academicianIds);
            for (Long gradeItemId : gradeItemIds) {
                requestParams.append("gradeItemIds[]", String.valueOf(gradeItemId));
            }
            for (String studentId : studentIds) {
                requestParams.append("studentIds[]", studentId);
            }
            MoodleResponse response = post(config, requestParams);
            String decryptedMessage = decrypt(config, response);
            Map<String, Map<String, List<Map<String, Object>>>> responseMap = getMapper().readValue(decryptedMessage, Map.class);
            Map<Long, List<Grade>> result = new HashMap<>();
            for (Entry<String, List<Map<String, Object>>> entry : responseMap.get("grades").entrySet()) {
                List<Grade> grades = new ArrayList<>();
                for (Map<String, Object> gradeMap : entry.getValue()) {
                    Grade grade = new Grade();
                    grade.setPoints(gradeMap.get("points"));
                    grade.setStudent((String) gradeMap.get("identity"));
                    grade.setComment((String) gradeMap.get("comment"));
                    grades.add(grade);
                }
                result.put(Long.valueOf(entry.getKey()), grades);
            }
            return result;
        } catch (Exception e) {
            throw new MoodleException(e);
        }
    }

    private Cipher getCipher() {
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (GeneralSecurityException e) {
                throw new MoodleException(e);
            }
        }
        return cipher;
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
        if (signature == null) {
            try {
                signature = Signature.getInstance("SHA1withRSA");
            } catch (NoSuchAlgorithmException e) {
                throw new MoodleException(e);
            }
        }
        return signature;
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
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    private MoodleResponse post(Config config, MoodleRequestParams requestParams) throws Exception {
        String requestMessage = requestParams.getRequestMessage(config);
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
