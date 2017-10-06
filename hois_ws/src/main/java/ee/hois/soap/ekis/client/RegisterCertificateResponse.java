package ee.hois.soap.ekis.client;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.soap.ekis.client.generated.Answer;

public class RegisterCertificateResponse extends LogResult<Answer> {

    public RegisterCertificateResponse(LogContext log, Answer result) {
        super(log, result);
    }
}
