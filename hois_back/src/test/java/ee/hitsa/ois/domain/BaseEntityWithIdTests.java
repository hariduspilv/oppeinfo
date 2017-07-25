package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.curriculum.Curriculum;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BaseEntityWithIdTests {

    @Test
    public void testEqualsAndHashCode() {

        final Long COMMON_ID = Long.valueOf(1);
        BaseEntityWithId curriculum1 = new Curriculum();
        curriculum1.setId(COMMON_ID);
        BaseEntityWithId curriculum2 = new Curriculum();
        curriculum2.setId(COMMON_ID);
        Assert.assertTrue(curriculum1.equals(curriculum1));
        Assert.assertTrue(curriculum1.equals(curriculum2));

        Set<BaseEntityWithId> set = new HashSet<>();
        set.add(curriculum1);
        set.add(curriculum1);
        set.add(curriculum2);
        Assert.assertEquals(1, set.size());

        // newly created objects with no id must be not equal
        BaseEntityWithId curriculum3 = new Curriculum();
        BaseEntityWithId curriculum4 = new Curriculum();
        Assert.assertFalse(curriculum3.equals(curriculum4));

        Set<BaseEntityWithId> set2 = new HashSet<>();
        set2.add(curriculum3);
        set2.add(curriculum4);
        set2.add(new Curriculum());
        Assert.assertEquals(3, set2.size());
    }

}
