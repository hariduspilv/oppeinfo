package ee.hitsa.ois;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.service.ClassifierService;


public class ClassifierSetJsonDeserializer extends StdDeserializer<Set<Classifier>> {

    @Autowired
    private ClassifierService classifierService;

    public ClassifierSetJsonDeserializer() {
        super(Set.class);
    }

    @Override
    public Set<Classifier> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if(p.currentToken() == JsonToken.START_ARRAY) {
            Set<Classifier> classifiers = new HashSet<>();
            ArrayNode array = p.readValueAsTree();
            for (JsonNode jsonNode : array) {
                Classifier classifier = classifierService.findOne(((TextNode) jsonNode.get("code")).asText());
                if(classifier != null) {
                    classifiers.add(classifier);
                }
            }
            return classifiers;
        }
        return null;
    }

}
