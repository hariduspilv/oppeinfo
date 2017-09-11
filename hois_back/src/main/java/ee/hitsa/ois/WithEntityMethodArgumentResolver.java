package ee.hitsa.ois;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;

public class WithEntityMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final EntityManager em;

    public WithEntityMethodArgumentResolver(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(WithEntity.class) || parameter.hasParameterAnnotation(WithVersionedEntity.class);
    }

    /**
     * Convert argument value to entity using conversionService.
     * @throws EntityNotFoundException if entity is not found
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String idParameterName;
        WithEntity we = parameter.getParameterAnnotation(WithEntity.class);
        if(we != null) {
            idParameterName = we.value();
        } else {
            idParameterName = parameter.getParameterAnnotation(WithVersionedEntity.class).value();
        }
        Map<String, String> uriTemplateVars = getUriTemplateVars(webRequest);
        String id = uriTemplateVars != null ? uriTemplateVars.get(idParameterName) : null;

        return loadEntity(parameter.getParameterType(), id);
    }

    protected Object loadEntity(Class<?> entityClass, String id) {
        Object entity = null;
        if(StringUtils.hasText(id)) {
            if(BaseEntityWithId.class.isAssignableFrom(entityClass)) {
                entity = em.find(entityClass, Long.valueOf(id));
            } else if(Classifier.class.isAssignableFrom(entityClass)) {
                entity = em.find(entityClass, id);
            }
        }
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, String> getUriTemplateVars(NativeWebRequest webRequest) {
        return (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }
}
