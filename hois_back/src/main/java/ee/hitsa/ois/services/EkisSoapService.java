package ee.hitsa.ois.services;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.function.Supplier;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.DirectiveConfirmService;
import ee.hitsa.ois.service.JobService;
import ee.hois.soap.ekis.service.generated.EkisTahvelPort;
import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceContractResponse;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.EnforceDirectiveResponse;
import ee.hois.soap.ekis.service.generated.RejectDirective;
import ee.hois.soap.ekis.service.generated.RejectDirectiveResponse;

@Component
@WebService(
        serviceName = "EkisTahvel",
        portName = "EkisTahvelSOAP",
        targetNamespace = "http://tahvel.hois.ee/ekis",
        wsdlLocation = "wsdl/ekis/ekis.wsdl")
public class EkisSoapService implements EkisTahvelPort {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String SYSTEM_FAULT = "süsteemi viga";

    @Autowired
    private ContractService contractService;
    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private JobService jobService;

    @Override
    public EnforceContractResponse enforceContract(EnforceContract request) {
        log.info("EkisSoapService: enforceContract");
        LocalDate contractDate = toLocalDate(request.getContractDate());
        Contract contract = withExceptionHandler(() -> {
            Contract c = contractService.confirmedByEkis(request.getOisContractId(), request.getContractNumber(), contractDate, request.getWdContractId());
            jobService.contractConfirmed(c);
            return c;
        });
        EnforceContractResponse response = new EnforceContractResponse();
        response.setOisContractId(request.getOisContractId());
        response.setWdContractId(request.getWdContractId());
        response.setStatus(contract.getStatus().getNameEt());
        return response;
    }

    /**
     * TODO
     * Peale staatuse muutmist ÕISis sõltuvalt käskkirja liigist muudetakse automaatselt õppurite andmed (vt HOIS_analyys_avaldused_kaskkirjad.docx ).
     * Peale andmete õnnestunud muutmist ja EKISele vastuse saatmist, saadetakse õppurile automaatteade käskkirja kinnitamise kohta
     * (vt HOIS_analyys_avaldused_kaskkirjad.docx). Viimase sammuna saadetakse vastavad muudatused EHISesse (hetkel see protsess veel kirjeldamata).
     */

    @Override
    public EnforceDirectiveResponse enforceDirective(EnforceDirective request) {
        log.info("EkisSoapService: enforceDirective");
        LocalDate directiveDate = toLocalDate(request.getDirectiveDate());
        Directive directive = withExceptionHandler(() -> {
            Directive d = directiveConfirmService.confirmedByEkis(request.getOisDirectiveId(), request.getDirectiveNumber(), directiveDate,
                    request.getPreamble(), request.getWdDirectiveId(), request.getSignerIDCode(), request.getSignerName());
            jobService.directiveConfirmed(d);
            return d;
        });

        EnforceDirectiveResponse response = new EnforceDirectiveResponse();
        response.setOisDirectiveId(request.getOisDirectiveId());
        response.setWdDirectiveId(request.getWdDirectiveId());
        response.setStatus(directive.getStatus().getNameEt());
        return response;
    }

    @Override
    public RejectDirectiveResponse rejectDirective(RejectDirective request) {
        log.info("EkisSoapService: rejectDirective");
        Directive directive = withExceptionHandler(() ->
            directiveConfirmService.rejectByEkis(request.getOisDirectiveId(), request.getRejectComment(),
                    request.getPreamble(), request.getWdDirectiveId()));

        RejectDirectiveResponse response = new RejectDirectiveResponse();
        response.setOisDirectiveId(request.getOisDirectiveId());
        response.setWdDirectiveId(request.getWdDirectiveId());
        response.setStatus(directive.getStatus().getNameEt());
        return response;
    }

    private static LocalDate toLocalDate(XMLGregorianCalendar date) {
        return date.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }

    private static <T> T withExceptionHandler(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HoisException e) {
            throw e;
        } catch (Throwable e) {
            log.error(SYSTEM_FAULT, e);
            throw new HoisException(SYSTEM_FAULT);
        }
    }
}
