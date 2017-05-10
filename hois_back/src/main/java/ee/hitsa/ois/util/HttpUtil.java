package ee.hitsa.ois.util;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ee.hitsa.ois.domain.BaseEntityWithId;

public class HttpUtil {

    public static CreatedResponse created(BaseEntityWithId entity) {
        return new CreatedResponse(EntityUtil.getId(entity));
    }

    public static class CreatedResponse extends ResponseEntity<Map<String, ?>> {
        public CreatedResponse(Long id) {
            super(Collections.singletonMap("id", id), HttpStatus.CREATED);
        }
    }
}
