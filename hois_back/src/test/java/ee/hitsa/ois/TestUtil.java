package ee.hitsa.ois;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class TestUtil {

    public static <T> ResponseEntity<T> getParameterizedEntity(TestRestTemplate restTemplate, 
            String url, Class<?> methodClass, String methodName, Class<?>... parameterTypes) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, ParameterizedTypeReference.forType(
                    methodClass.getMethod(methodName, parameterTypes).getGenericReturnType()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
