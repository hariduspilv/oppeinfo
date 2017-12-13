package ee.hois.xroad.kutseregister.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.kutseregister.generated.MuutunudKutsestandardidVastus;

public class MuutunudKutsestandardidResponse extends LogResult<MuutunudKutsestandardidVastus> {

    public MuutunudKutsestandardidResponse(LogContext log, MuutunudKutsestandardidVastus result) {
        super(log, result);
    }
}
