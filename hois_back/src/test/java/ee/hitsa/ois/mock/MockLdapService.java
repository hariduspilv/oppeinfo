package ee.hitsa.ois.mock;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.service.security.LdapService;

public class MockLdapService extends LdapService {

    @Override
    public String getIdCode(Long schoolId, String username, String password) {
        return TestConfiguration.USER_ID;
    }
}
