package ee.hitsa.ois.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ee.hitsa.ois.domain.BaseEntityWithId;

/**
 * Utility functions for working with http responses
 */
public abstract class HttpUtil {

    private static final Pattern INVALID_FILENAME_SYMBOLS = Pattern.compile("[\\/:*?\"<>|]");

    public static final NoContentResponse NO_CONTENT_RESPONSE = new NoContentResponse();
    
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_XLS = "application/vnd.ms-excel";
    public static final String TEXT_CSV_UTF8 = "text/csv; Charset=UTF-8";
    public static final String APPLICATION_ZIP = "application/zip";
    
    private static final byte[] UTF8_BOM = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public static CreatedResponse created(BaseEntityWithId entity) {
        return new CreatedResponse(EntityUtil.getId(entity));
    }

    public static void csvUtf8WithBom(HttpServletResponse response, String filename, String csv) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            byte[] csvBytes = csv.getBytes(StandardCharsets.UTF_8);
            response.setContentType(TEXT_CSV_UTF8);
            response.setContentLength(UTF8_BOM.length + csvBytes.length);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + toValidFilename(filename));
            bos.write(UTF8_BOM);
            bos.write(csvBytes);
        }
    }

    public static void pdf(HttpServletResponse response, String filename, byte[] pdf) throws IOException {
        file(response, filename, APPLICATION_PDF, pdf);
    }

    public static void xls(HttpServletResponse response, String filename, byte[] xls) throws IOException {
        file(response, filename, APPLICATION_XLS, xls);
    }

    public static void xml(HttpServletResponse response, String filename, byte[] xml) throws IOException {
        file(response, filename, APPLICATION_XML, xml);
    }
    
    /**
     * NB! Response output stream should be written AFTER.
     * Call it before closing output stream.
     * 
     * @param response
     * @param filename
     * @throws IOException
     */
    public static void zip(HttpServletResponse response, String filename) throws IOException {
        file(response, filename, APPLICATION_ZIP);
    }
    
    public static void file(HttpServletResponse response, String filename, String filetype) {
        response.setContentType(filetype);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + toValidFilename(filename) + "\"");
    }

    public static void file(HttpServletResponse response, String filename, String filetype, byte[] data) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            response.setContentType(filetype);
            response.setContentLength(data.length);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + toValidFilename(filename));
            bos.write(data);
        }
    }

    private static String toValidFilename(String filename) {
        return INVALID_FILENAME_SYMBOLS.matcher(filename).replaceAll("");
    }

    public static String getIpAddress(HttpServletRequest request) {
        String forwarded = request.getHeader("X-FORWARDED-FOR");
        return forwarded != null ? forwarded : request.getRemoteAddr();
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static class CreatedResponse extends ResponseEntity<Map<String, ?>> {
        public CreatedResponse(Long id) {
            super(Collections.singletonMap("id", id), HttpStatus.CREATED);
        }
    }

    public static class NoContentResponse extends ResponseEntity<Void> {
        public NoContentResponse() {
            super(HttpStatus.NO_CONTENT);
        }
    }
}
