package com.mt4agents.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mt4agents.entities.users.AdminUser;
import com.mt4agents.entities.users.AgentUser;
import com.mt4agents.exceptions.AgentException;
import com.mt4agents.services.AgentService;

public class MT4AuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	private static final Logger logger = Logger
			.getLogger(MT4AuthenticationSuccessHandler.class);

	@Autowired
	private AgentService agentService;

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {

		User user = (User) authentication.getPrincipal();
		logger.info("Authentication successful for " + user.getUsername() + ".");
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			String role = authority.getAuthority();
			logger.info("Inspecting role " + role);
			if (role.equals(AdminUser.ROLE)) {
				logger.info("Redirecting to /agent/all");
				response.sendRedirect("/agent/all");
			} else {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(-1);
				if (role.equals(AgentUser.ROLE)) {
					try {
						session.setAttribute("agent", agentService
								.getAgentDTOByUsername(user.getUsername()));
					} catch (AgentException e) {
						throw new ServletException(e);
					}
					logger.info("Redirecting to /agent/my");
					response.sendRedirect("/agent/my");
				} else {
					session.invalidate();
				}
			}
		}
	}
}
