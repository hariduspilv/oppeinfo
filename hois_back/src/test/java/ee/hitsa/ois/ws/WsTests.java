package ee.hitsa.ois.ws;

import ee.hois.Library;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsTests {
    @Test
    public void accessTest() {
        Library library = new Library();
        Assert.assertTrue(library.someLibraryMethod());
    }
}
