package ee.hitsa.ois.auth;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.idp.authn.AbstractUsernamePasswordValidationAction;
import net.shibboleth.idp.authn.AuthnEventIds;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.idp.profile.ActionSupport;

public class UsernamePasswordValidationAction extends AbstractUsernamePasswordValidationAction {

	@Nonnull private final Logger log = LoggerFactory.getLogger(UsernamePasswordValidationAction.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doExecute(
			ProfileRequestContext profileRequestContext, 
			AuthenticationContext authenticationContext) {
		String username = getUsernamePasswordContext().getUsername();
		try {
			log.debug("{} attempting to authenticate user '{}'", getLogPrefix(), username);
			authenticate();
			log.debug("{} login by '{}' succeeded", getLogPrefix(), username);
			buildAuthenticationResult(profileRequestContext, authenticationContext);
			ActionSupport.buildProceedEvent(profileRequestContext);
			return;
		} catch (LoginException e) {
			log.debug(getLogPrefix() + " login by '" + username + "' failed", e);
			handleError(profileRequestContext, authenticationContext, e, AuthnEventIds.INVALID_CREDENTIALS);
		} catch (Exception e) {
			log.warn(getLogPrefix() + " login by '" + username + "' produced exception", e);
			handleError(profileRequestContext, authenticationContext, e, AuthnEventIds.AUTHN_EXCEPTION);
		}
	}

	private void authenticate() throws LoginException {
		// TODO implement real authentication
		if (!"parool".equals(getUsernamePasswordContext().getPassword())) {
			throw new LoginException();
		}
	}
}
