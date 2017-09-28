package ee.hitsa.ois.mock;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.service.ekis.EkisService;

public class MockEkisService extends EkisService {

    @Override
    protected <T extends BaseEntityWithId> T save(T entity) {
        // do not save
        return entity;
    }
}
