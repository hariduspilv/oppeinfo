package ee.hitsa.ois.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ee.hitsa.ois.config.HoisJwtProperties;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import io.jsonwebtoken.Jwts;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private HoisJwtProperties hoisJwtProperties;
    private HoisUserDetailsService hoisUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, HoisUserDetailsService hoisUserDetailsService, HoisJwtProperties jwtProperties) {
        super(authenticationManager);
        this.hoisUserDetailsService = hoisUserDetailsService;
        this.hoisJwtProperties = jwtProperties;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(hoisJwtProperties.getHeader());

        if (header == null || !header.startsWith(hoisJwtProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String username = getUsername(request);
        HoisUserDetails hoisUserDetails = hoisUserDetailsService.loadUserByUsername(username);
        if (hoisUserDetails != null) {
            PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(username, username);
            token.setDetails(hoisUserDetailsService.loadUserByUsername(username));
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        chain.doFilter(request, response);
    }

    /**
     * This method throws exception when token is invalid (expired, tampered etc)
     */
    private String getUsername(HttpServletRequest request) {
        String token = request.getHeader(hoisJwtProperties.getHeader());
        if (token != null) {
            return Jwts.parser()
                    .setSigningKey(hoisJwtProperties.getSecret())
                    .parseClaimsJws(token.replace(hoisJwtProperties.getTokenPrefix(), ""))
                    .getBody()
                    .getSubject();
        }
        return null;
    }

}
