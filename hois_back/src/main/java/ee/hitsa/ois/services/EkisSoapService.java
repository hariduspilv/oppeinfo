package ee.hitsa.ois.services;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Supplier;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.persistence.EntityManager;

import ee.hitsa.ois.service.DirectiveConfirmInternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.DirectiveConfirmService;
import ee.hitsa.ois.service.JobService;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.soap.ekis.service.generated.EkisTahvelPort;
import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceContractResponse;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.EnforceDirectiveResponse;
import ee.hois.soap.ekis.service.generated.RejectDirective;
import ee.hois.soap.ekis.service.generated.RejectDirectiveResponse;

@HandlerChain(file="/cxf-ekis-handler-chain.xml")
@Component
@WebService(
        serviceName = "EkisTahvel",
        portName = "EkisTahvelSOAP",
        targetNamespace = "http://tahvel.hois.ee/ekis",
        wsdlLocation = "wsdl/ekis/ekis.wsdl")
public class EkisSoapService implements EkisTahvelPort {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String SYSTEM_FAULT = "süsteemi viga";
    private static final Authentication EKIS = new UsernamePasswordAuthenticationToken("EKIS", null,
            Collections.singletonList((GrantedAuthority)(() -> "ROLE_EKIS")));

    @Autowired
    private EntityManager em;
    @Autowired
    private ContractService contractService;
    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private DirectiveConfirmInternalService directiveConfirmInternalService;
    @Autowired
    private JobService jobService;

    @Override
    public EnforceContractResponse enforceContract(EnforceContract request) {
        log.info("EkisSoapService: enforceContract");
        LocalDate contractDate = DateUtils.toLocalDate(request.getContractDate());
        Contract contract = withExceptionHandler(() -> {
            Contract c = contractService.confirmedByEkis(request.getOisContractId(),
                    request.getContractNumber(), contractDate, request.getWdContractId(),
                    request.getSchoolId() != null ? request.getSchoolId() : 0);
            jobService.contractConfirmed(EntityUtil.getId(c));
            return c;
        });
        EnforceContractResponse response = new EnforceContractResponse();
        response.setOisContractId(request.getOisContractId());
        response.setWdContractId(request.getWdContractId());
        response.setStatus(status(contract.getStatus()));
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
        LocalDate directiveDate = DateUtils.toLocalDate(request.getDirectiveDate());
        Directive directive = withExceptionHandler(() -> {
            Directive d = directiveConfirmInternalService.confirmedByEkis(request.getOisDirectiveId(),
                    request.getDirectiveNumber(), directiveDate, request.getPreamble(), request.getWdDirectiveId(),
                    request.getSignerIDCode(), request.getSignerName(),
                    request.getSchoolId() != null ? request.getSchoolId() : 0);
            jobService.directiveConfirmed(EntityUtil.getId(d));
            return d;
        });

        EnforceDirectiveResponse response = new EnforceDirectiveResponse();
        response.setOisDirectiveId(request.getOisDirectiveId());
        response.setWdDirectiveId(request.getWdDirectiveId());
        response.setStatus(status(directive.getStatus()));
        return response;
    }

    @Override
    public RejectDirectiveResponse rejectDirective(RejectDirective request) {
        log.info("EkisSoapService: rejectDirective");
        Directive directive = withExceptionHandler(() ->
            directiveConfirmService.rejectByEkis(request.getOisDirectiveId(), request.getRejectComment(),
                    request.getPreamble(), request.getWdDirectiveId(),
                    request.getSchoolId() != null ? request.getSchoolId() : 0));

        RejectDirectiveResponse response = new RejectDirectiveResponse();
        response.setOisDirectiveId(request.getOisDirectiveId());
        response.setWdDirectiveId(request.getWdDirectiveId());
        response.setStatus(status(directive.getStatus()));
        return response;
    }

    private String status(Classifier classifier) {
        return em.find(Classifier.class, EntityUtil.getCode(classifier)).getNameEt();
    }

    private static <T> T withExceptionHandler(Supplier<T> supplier) {
        try {
            // set authentication to get audit log fields filled
            Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(EKIS);
            try {
                return supplier.get();
            } finally {
                SecurityContextHolder.getContext().setAuthentication(oldAuthentication);
            }
        } catch (HoisException e) {
            throw e;
        } catch (Throwable e) {
            log.error(SYSTEM_FAULT, e);
            throw new HoisException(SYSTEM_FAULT);
        }
    }
}
