package ee.hitsa.ois.service.fotobox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.hitsa.ois.domain.WsPhotoLog;
import ee.hitsa.ois.service.fotobox.FotoBoxService.FotoBoxRequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional(Transactional.TxType.REQUIRES_NEW)
@Service
public class FotoBoxLogService {

    @Autowired
    protected EntityManager em;
    @Autowired
    private ObjectMapper objectMapper;

    public void insertLog(WsPhotoLog logRecord, FotoBoxRequestParams requestParams, Map<String, List<Long>> result,
            Exception error) {
        try {
            logRecord.setRequest(objectMapper.writeValueAsString(requestParams));
            if (error != null) {
                logRecord.setResponse(error.getMessage());
            } else {
                logRecord.setLogTxt(objectMapper.writeValueAsString(result));
            }
        } catch (JsonProcessingException e) {
            logRecord.setResponse(e.getMessage());
        } finally {
            em.persist(logRecord);
        }
    }
}
