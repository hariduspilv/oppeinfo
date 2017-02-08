package ee.hitsa.ois;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.service.ClassifierService;

/**
 * TODO: add this to configureJackson2ObjectMapperBuilder in Application.java
 * and remove @JsonDeserialize(using = ClassifierJsonDeserializer.class)
 *
 * classifier in json may be represented as string "classifier code" or as classifier object {"code": "classifier code"}
 */
public class ClassifierJsonDeserializer extends StdDeserializer<Classifier> {
    private static final long serialVersionUID = -8646816637823835600L;

    @Autowired
    private ClassifierService classifierService;

    public ClassifierJsonDeserializer() {
        super(Classifier.class);
    }

    @Override
    public Classifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String classifierCode = null;

        if(p.currentToken().isScalarValue()) {
            classifierCode = p.getText();
        } else {
            classifierCode = ((TextNode) p.readValueAsTree().get("code")).asText();
        }

        if(classifierService != null && classifierCode != null) {
           return classifierService.findOne(classifierCode);
        }

        return null;
    }

}
