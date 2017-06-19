package ee.hitsa.ois.util;

import java.io.ByteArrayInputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ee.hitsa.ois.domain.BaseEntityWithId;

public class HttpUtil {

    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_XLS = "application/vnd.ms-excel";
    public static final String TEXT_CSV_UTF8 = "text/csv; Charset=UTF-8";
    private static final byte[] UTF8_BOM = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public static CreatedResponse created(BaseEntityWithId entity) {
        return new CreatedResponse(EntityUtil.getId(entity));
    }

    public static InputStreamResource csvUtf8WithBom(String csv) {
        return new SequenceInputStreamResource(UTF8_BOM, csv.getBytes(StandardCharsets.UTF_8));
    }

    public static class CreatedResponse extends ResponseEntity<Map<String, ?>> {
        public CreatedResponse(Long id) {
            super(Collections.singletonMap("id", id), HttpStatus.CREATED);
        }
    }

    public static class SequenceInputStreamResource extends InputStreamResource {

        private final long contentLength;

        public SequenceInputStreamResource(byte[] buf1, byte[] buf2) {
            super(new SequenceInputStream(new ByteArrayInputStream(buf1), new ByteArrayInputStream(buf2)));

            contentLength = buf1.length + buf2.length;
        }

        @Override
        public long contentLength() {
            return contentLength;
        }
    }
}
