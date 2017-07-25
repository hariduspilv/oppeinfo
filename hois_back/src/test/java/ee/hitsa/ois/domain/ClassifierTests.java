package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClassifierTests {
    
    @Test
    public void testEqualsAndHashCode() {
        
        final String COMMON_CODE = "code";
        Classifier classifier1 = new Classifier();
        classifier1.setCode(COMMON_CODE);
        Classifier classifier2 = new Classifier();
        classifier2.setCode(COMMON_CODE);
        Assert.assertTrue(classifier1.equals(classifier1));
        Assert.assertTrue(classifier1.equals(classifier2));
        
        Set<Classifier> set = new HashSet<>();
        set.add(classifier1);
        set.add(classifier1);
        set.add(classifier2);
        Assert.assertEquals(1, set.size());
           
        // newly created objects with no id must be not equal
        Classifier classifier3 = new Classifier();
        Classifier classifier4 = new Classifier();
        Assert.assertFalse(classifier3.equals(classifier4));
        
        Set<Classifier> set2 = new HashSet<>();
        set2.add(classifier3);
        set2.add(classifier4);
        set2.add(new Classifier());
        Assert.assertEquals(3, set2.size());
    }

}
