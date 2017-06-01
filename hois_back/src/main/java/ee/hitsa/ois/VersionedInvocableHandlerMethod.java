package ee.hitsa.ois;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.Versioned;
import ee.hitsa.ois.util.WithVersionedEntity;

public class VersionedInvocableHandlerMethod extends ServletInvocableHandlerMethod {

    public VersionedInvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    @Override
    protected Object doInvoke(Object... args) throws Exception {
        for(MethodParameter mp : getMethodParameters()) {
            WithVersionedEntity versioned = mp.getParameterAnnotation(WithVersionedEntity.class);
            if(versioned != null) {
                assertVersion(versioned, mp.getParameterIndex(), args);
            }
        }
        return super.doInvoke(args);
    }

    private void assertVersion(WithVersionedEntity definition, int index, Object[] args) {
        Class<? extends Annotation> annotation;
        String matchedValue;
        if(StringUtils.hasText(definition.versionPathVariable())) {
            annotation = PathVariable.class;
            matchedValue = definition.versionPathVariable();
        } else if(StringUtils.hasText(definition.versionRequestParam())) {
            annotation = RequestParam.class;
            matchedValue = definition.versionRequestParam();
        } else if(definition.versionRequestBody()) {
            annotation = RequestBody.class;
            matchedValue = null;
        } else {
            throw new IllegalStateException("Unable to determine entity version source");
        }

        MethodParameter versionParameter = Stream.of(getMethodParameters()).filter(mp -> {
            if(mp.getParameterIndex() != index) {
                Annotation a = mp.getParameterAnnotation(annotation);
                if(a != null) {
                    if(a instanceof PathVariable && ((PathVariable)a).value().equals(matchedValue)) {
                        return true;
                    }
                    if(a instanceof RequestParam && ((RequestParam)a).value().equals(matchedValue)) {
                        return true;
                    }
                    if(a instanceof RequestBody && args[mp.getParameterIndex()] instanceof Versioned) {
                        return true;
                    }
                }
            }
            return false;
        }).findFirst().orElseThrow(() -> new IllegalStateException("Unable to find entity version source"));

        Object entity = args[index];
        if(!(entity instanceof Versioned)) {
            throw new IllegalStateException("Unable to determine entity version");
        }

        Object versionValue = args[versionParameter.getParameterIndex()];
        if(RequestBody.class.isAssignableFrom(annotation)) {
            versionValue = ((Versioned)versionValue).getVersion();
            if(versionValue == null) {
                // client did not send version, bad request
                throw new IllegalArgumentException("Version value is missing");
            }
        }

        if(!(versionValue instanceof Long)) {
            throw new IllegalStateException("Version value should be Long");
        }

        EntityUtil.assertEntityVersion((Versioned)entity, (Long)versionValue);
    }
}
