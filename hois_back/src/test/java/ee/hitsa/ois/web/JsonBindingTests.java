package ee.hitsa.ois.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.SchoolDepartment;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class JsonBindingTests {

    @Autowired
    ObjectMapper objectMapper;
    private JacksonTester<School> json;
    private JacksonTester<SchoolDepartment> schoolDepartmentJson;
    private final Comparator<Number> longComparator = new LongComparator();


    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void serializeSchoolJson() throws IOException {
        School school = new School();
        school.setNameEt("Nimetus eesti keeles");
        school.setNameEn("Name in english");
        school.setEmail("a@b.c");
        school.setCode("school code");
        school.setInserted(LocalDateTime.now());
        school.setChanged(LocalDateTime.now());
        school.setVersion(Long.valueOf(2));
        Classifier ehisSchool = new Classifier();
        ehisSchool.setCode("EHIS_SCHOOL_1");
        school.setEhisSchool(ehisSchool);

        JsonContent<School> content = json.write(school);
        // System.out.println(content.getJson());

        assertThat(content).hasJsonPathStringValue("@.nameEt");
        assertThat(content).hasJsonPathStringValue("@.nameEn");
        assertThat(content).hasJsonPathStringValue("@.email");
        assertThat(content).hasJsonPathStringValue("@.code");
        assertThat(content).hasJsonPathStringValue("@.inserted");
        assertThat(content).hasJsonPathStringValue("@.changed");
        assertThat(content).hasJsonPathNumberValue("@.version");
        //TODO: assertThat(content).hasJsonPathStringValue("@.ehisSchool.code");
        // verify that serialized fields are same
        assertThat(content).extractingJsonPathStringValue("@.nameEt").isEqualTo(school.getNameEt());
        assertThat(content).extractingJsonPathStringValue("@.nameEn").isEqualTo(school.getNameEn());
        assertThat(content).extractingJsonPathStringValue("@.email").isEqualTo(school.getEmail());
        assertThat(content).extractingJsonPathStringValue("@.code").isEqualTo(school.getCode());
        // verify that inserted and changed properties get serialized in iso format
        assertThat(content).extractingJsonPathStringValue("@.inserted").isEqualTo(school.getInserted().toInstant(ZoneOffset.UTC).toString());
        assertThat(content).extractingJsonPathStringValue("@.changed").isEqualTo(school.getChanged().toInstant(ZoneOffset.UTC).toString());
        // verify that version property is serialized
        assertThat(content).extractingJsonPathNumberValue("@.version").usingComparator(longComparator).isEqualTo(school.getVersion());
    }

    @Test
    public void deserializeSchoolJson() throws IOException {
        School school = new School();
        school.setNameEt("Nimetus eesti keeles");
        school.setNameEn("Name in english");
        school.setEmail("a@b.c");
        school.setCode("school code");
        school.setInserted(LocalDateTime.now());
        school.setChanged(LocalDateTime.now());
        school.setVersion(Long.valueOf(2));
        Classifier ehisSchool = new Classifier();
        ehisSchool.setCode("EHIS_SCHOOL_1");
        school.setEhisSchool(ehisSchool);

        String content = json.write(school).getJson();
        School parsedSchool = json.parseObject(content);

        // verify that deserialized fields have correct value
        assertThat(parsedSchool.getNameEt()).isEqualTo(school.getNameEt());
        assertThat(parsedSchool.getNameEn()).isEqualTo(school.getNameEn());
        assertThat(parsedSchool.getEmail()).isEqualTo(school.getEmail());
        assertThat(parsedSchool.getCode()).isEqualTo(school.getCode());
        //TODO: assertThat(parsedSchool.getEhisSchool().getCode()).isEqualTo(school.getEhisSchool().getCode());

        // verify that inserted, changed and version properties are not deserialized
        //FIXME: BaseEntity JsonIgnoreProperties
//        assertThat(parsedSchool.getInserted()).isNull();
//        assertThat(parsedSchool.getChanged()).isNull();
//        assertThat(parsedSchool.getVersion()).isNull();
    }

    @Test
    public void serializeSchoolDepartmentJson() throws IOException {
        School school = new School();
        school.setId(Long.valueOf(1));
        school.setVersion(Long.valueOf(2));
        SchoolDepartment schoolDepartment = new SchoolDepartment();
        schoolDepartment.setSchool(school);

        json.write(school);
    }

    @Test
    public void deserializeUtcDate() throws IOException {
        String content = "{\"validFrom\":\"2016-12-31T22:00:00.000Z\"}";
        SchoolDepartment parsedSchoolDepartment = schoolDepartmentJson.parseObject(content);
        assertThat(parsedSchoolDepartment.getValidFrom().getDayOfMonth()).isEqualTo(1);
    }

    static class LongComparator implements Comparator<Number> {

        @Override
        public int compare(Number o1, Number o2) {
            if(o1.longValue() < o2.longValue()) {
                return -1;
            }
            if(o1.longValue() > o2.longValue()) {
                return 1;
            }
            return 0;
        }
    }
}
