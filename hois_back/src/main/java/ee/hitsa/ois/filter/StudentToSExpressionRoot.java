package ee.hitsa.ois.filter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

public class StudentToSExpressionRoot extends WebSecurityExpressionRoot {

    private HoisUserDetailsService userDetailsService;
    
    public StudentToSExpressionRoot(Authentication a, FilterInvocation fi, HoisUserDetailsService userDetailsService) {
        super(a, fi);
        this.userDetailsService = userDetailsService;
    }
    
    public boolean hasToConfirmToS() {
        if (authentication instanceof AnonymousAuthenticationToken || !(authentication.getDetails() instanceof HoisUserDetails)) {
            return false;
        }
        HoisUserDetails userDetails = (HoisUserDetails) authentication.getDetails();
        if (userDetailsService.checkIfContractAgreementNeeds(userDetails)) {
            return true;
        }
        return false;
    }
    
}
