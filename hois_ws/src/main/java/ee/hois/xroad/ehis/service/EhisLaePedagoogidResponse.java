package ee.hois.xroad.ehis.service;

import java.math.BigInteger;
import java.util.List;

import ee.hois.soap.LogContext;

public class EhisLaePedagoogidResponse extends EhisResponse<List<String>> {

    public EhisLaePedagoogidResponse(LogContext log, List<String> result, String teade, BigInteger kood) {
        super(log, result, teade, kood);
    }
}
