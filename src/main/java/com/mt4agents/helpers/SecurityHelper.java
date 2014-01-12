package com.mt4agents.helpers;

import java.security.Principal;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.mt4agents.entities.users.AdminUser;
import com.mt4agents.entities.users.AgentUser;
import com.mt4agents.exceptions.RestAgentClientControllerException;

public class SecurityHelper {
	
	@Autowired
	private MessageSource messageSource;
	
	public boolean isAgentUserAuthorisedForResource(Integer agentId,
			Principal principal) {
		UsernamePasswordAuthenticationToken usernamePasswordPrincipal = (UsernamePasswordAuthenticationToken) principal;
		Collection<GrantedAuthority> authorities = usernamePasswordPrincipal
				.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			String role = authority.getAuthority();
			if (new StringBuilder(AgentUser.AGENT_USER_ROLE_PREFIX)
					.append(agentId).toString().equals(role)
					|| role.equals(AdminUser.ROLE)) {
				return true;
			}
		}
		return false;
	}
	
	public void raiseExceptionIfNotAuthorised(Integer agentId,
			Principal principal) throws RestAgentClientControllerException {
		if (!isAgentUserAuthorisedForResource(agentId, principal)) {
			throw new RestAgentClientControllerException(
					messageSource.getMessage(
							"mt4agents.exception.security.notauthorised", null,
							Locale.US));
		}
	}
}
