package ee.hitsa.ois.web.commandobject;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.domain.Classifier;

public class TeacherSearchCommand {

    private String name;
    private String idcode;

    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Classifier getSchool() {
        return school;
    }

    public void setSchool(Classifier school) {
        this.school = school;
    }
}
