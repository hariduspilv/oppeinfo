package ee.hois.xroad.ehis.service;

import java.math.BigInteger;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;

public abstract class EhisResponse<T> extends LogResult<T> {

    private final String teade;
    private final BigInteger kood;

    public EhisResponse(LogContext log, T result, String teade, BigInteger kood) {
        super(log, result);
        this.teade = teade;
        this.kood = kood;
    }

    public String getTeade() {
        return teade;
    }

    public BigInteger getKood() {
        return kood;
    }
}
