package ee.hitsa.ois.util;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ee.hitsa.ois.domain.BaseEntityWithId;

public class HttpUtil {

    public static ResponseEntity<Map<String, ?>> created(BaseEntityWithId entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("id", EntityUtil.getId(entity)));
    }
}
