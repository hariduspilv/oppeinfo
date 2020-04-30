package ee.hitsa.ois;

import ee.hitsa.ois.domain.WsJuhanLog;
import ee.hitsa.ois.services.JuhanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Transactional
@Component
public class JuhanLogRequestFilter extends OncePerRequestFilter {

    private JuhanService juhanService;
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public JuhanLogRequestFilter(JuhanService juhanService) {
        this.juhanService = juhanService;
    }

    @Transactional
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // request and response need to be cached so that they can be read more than once
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            String requestBody = new String(wrappedRequest.getContentAsByteArray());
            String responseBody = new String(wrappedResponse.getContentAsByteArray());
            wrappedResponse.copyBodyToResponse();

            WsJuhanLog logRecord = new WsJuhanLog();
            logRecord.setRequestUrl(request.getRequestURI());
            logRecord.setRequestParam(HttpMethod.GET.name().equals(request.getMethod())
                    ? request.getQueryString() : requestBody);
            logRecord.setResponse(responseBody);

            boolean hasErrors = HttpStatus.OK.value() != wrappedResponse.getStatus();
            logRecord.setHasErrors(Boolean.valueOf(hasErrors));
            logRecord.setLogTxt(hasErrors ? String.valueOf(wrappedResponse.getStatus()) : null);

            String uri = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            log.info("REQUEST: {}", uri);
            log.info("RESPONSE: {}", responseBody);

            juhanService.saveJuhanLog(logRecord);
        }
    }
}