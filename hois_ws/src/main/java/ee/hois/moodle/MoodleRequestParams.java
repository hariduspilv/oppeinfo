package ee.hois.moodle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MoodleRequestParams {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private StringBuilder messageBuilder = new StringBuilder(256);
    private StringBuilder keyBuilder = new StringBuilder(64);
    
    public MoodleRequestParams(String method, String idcode, Long courseId, List<String> academicianIds) {
        messageBuilder.append("do=").append(method);
        keyBuilder.append(method);
        append("date", LocalDateTime.now().format(DATE_FORMAT));
        append("identifiers[idnumber]", idcode);
        append("courseId", String.valueOf(courseId));
        for (String academicianId : academicianIds) {
            append("academicianIds[]", academicianId);
        }
    }

    public void append(String key, String value) {
        messageBuilder.append("&").append(urlEncode(key)).append("=").append(urlEncode(value));
        keyBuilder.append(value);
    }
    
    public String getRequestMessage(Config config) {
        return messageBuilder.toString() + "&key=" + sha256Hex(keyBuilder.toString() + config.getSalt());
    }
    
    private MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new MoodleException(e);
        }
    }
    
    private String sha256Hex(final String data) {
        byte[] encodedhash = getDigest().digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MoodleException(e);
        }
    }
    
}
