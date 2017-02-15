package ee.hitsa.ois;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;

import ee.hitsa.ois.domain.BaseEntityWithId;

public class EntityWithIdJsonDeserializer extends StdDeserializer<BaseEntityWithId> implements ContextualDeserializer {

    @Autowired
    private EntityManager entityManager;

    private Class<?> domainClass;

    public EntityWithIdJsonDeserializer() {
        super(BaseEntityWithId.class);
    }

    @Override
    public BaseEntityWithId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        long id = ((NumericNode) p.readValueAsTree().get("id")).asLong();
        return (BaseEntityWithId) entityManager.find(domainClass, Long.valueOf(id));
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        this.domainClass  = ctxt.getContextualType().getRawClass();
        return this;
    }
}
