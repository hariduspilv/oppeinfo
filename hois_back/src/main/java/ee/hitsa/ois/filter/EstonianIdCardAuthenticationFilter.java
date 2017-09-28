package ee.hitsa.ois.filter;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ee.hitsa.ois.auth.EstonianIdCardAuthenticationToken;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.util.CertificateUtil;

public class EstonianIdCardAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Pattern IDCODE_PATTERN = Pattern.compile("(([1-6])(\\d{2})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d))");
    private BdocService bdocService;

    public EstonianIdCardAuthenticationFilter(AuthenticationManager authenticationManager, BdocService bdocService) {
        super(authenticationManager);
        this.bdocService = bdocService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        X509Certificate certificate = CertificateUtil.getCertificateFromRequest(request);
        if (certificate != null) {
            if (!bdocService.isValidEstonianIdCardCertificate(certificate)) {
                throw new HoisException("idcard.certificate.not.valid");
            }

            String personCode = getPersonCodeFormCertificate(certificate);
            if (personCode != null) {
                EstonianIdCardAuthenticationToken token = new EstonianIdCardAuthenticationToken(personCode, certificate);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private static String getPersonCodeFormCertificate(X509Certificate certificate) {
        String subjectDn = certificate.getSubjectDN().getName();
        Matcher matcher = IDCODE_PATTERN.matcher(subjectDn);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }



}
