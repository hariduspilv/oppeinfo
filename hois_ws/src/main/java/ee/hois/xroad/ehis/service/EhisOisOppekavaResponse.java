package ee.hois.xroad.ehis.service;

import java.util.List;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.ehis.generated.OisInfoteade;

public class EhisOisOppekavaResponse extends LogResult<List<OisInfoteade>> {

    public EhisOisOppekavaResponse(LogContext log, List<OisInfoteade> result) {
        super(log, result);
    }
}