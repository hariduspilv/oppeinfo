package ee.hois.xroad.ehis.service;

import java.math.BigInteger;
import java.util.List;

import ee.hois.soap.LogContext;

public class EhisLaeKorgharidusedResponse extends EhisResponse<List<String>> {

    public EhisLaeKorgharidusedResponse(LogContext log, List<String> result, String teade, BigInteger kood) {
        super(log, result, teade, kood);
    }
}
