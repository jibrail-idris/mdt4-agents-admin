package com.mt4agents.web.controllers.json.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebDelegatingSmartContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.base.proxies.response.RestResponse;
import com.mt4agents.dto.AgentClientDTO;
import com.mt4agents.entities.Agent;
import com.mt4agents.entities.AgentClient;
import com.mt4agents.entities.users.AgentUser;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentRelationshipService;
import com.mt4agents.test.WebBaseTest;

/**
 * 
 * @author Jibrail Idris
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/securityApplicationContext.xml",
		"file:src/main/webapp/WEB-INF/json-servlet.xml",
		"file:src/main/webapp/WEB-INF/beans.xml" }, loader = WebDelegatingSmartContextLoader.class)
public class RestAgentClientControllerTest extends WebBaseTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Autowired
	private AgentClientService agentClientService;

	@Autowired
	private AgentRelationshipService agentRelationshipService;

	@Autowired
	private MessageSource messageSource;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
		// .addFilter(getSecurityFilterChain()).build();
	}

//	@Test
//	@Transactional
	// https://github.com/SpringSource/spring-test-mvc/blob/master/src/test/java/org/springframework/test/web/server/samples/context/SpringSecurityTests.java
	public void agentUserAuthenticates() throws Exception {

		final Integer agentMT4Login = 110;

		createRandomAgentUser(agentMT4Login);

		mockMvc.perform(
				post("/j_spring_security_check").param("j_username",
						agentMT4Login.toString()).param("j_password",
						agentMT4Login.toString())).andExpect(
				new ResultMatcher() {
					@Override
					public void match(MvcResult mvcResult) throws Exception {
						HttpSession session = mvcResult.getRequest()
								.getSession();
						SecurityContext securityContext = (SecurityContext) session
								.getAttribute(SEC_CONTEXT_ATTR);
						Assert.assertEquals(securityContext.getAuthentication()
								.getName(), agentMT4Login.toString());
					}
				});
	}

	@Test
	@Transactional
	public void getClientsOneAgent() throws Exception {

		Integer agentMT4Login = 110;

		AgentUser agentUser = createRandomAgentUser(agentMT4Login);
		Agent agent = agentUser.getAgent();

		MockHttpSession session = login(agentMT4Login.toString());

		List<AgentClientDTO> clients = new ArrayList<AgentClientDTO>();

		for (int i = 0; i < 3; i++) {
			AgentClientDTO clientDTO = new AgentClientDTO();
			Integer clientLogin = mt4Logins.poll();
			clientDTO.setLogin(clientLogin);
			clientDTO.setAgentId(agent.getId());
			clientDTO.setRegistrationDate(new Date());
			clients.add(clientDTO);
		}

		agentClientService.saveClients(agent.getId(), clients);

		System.out.println(this.mockMvc
				.perform(
						get("/rest/clients.json").session(session)
								.param("agentId", agent.getId().toString())
								.param("startRangeCloseTime", "01-02-2013")
								.param("endRangeCloseTime", "31-02-2013"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString());
//				.andExpect(
//						jsonPath("$.response.status").value(
//								RestResponse.Status.OK.toString()))
//				.andExpect(jsonPath("$.response.payload").isArray());
	}

	@Test
	@Transactional
	public void getClientsUnauthorised() throws Exception {
		Integer agentMT4Login = 110;

		createRandomAgentUser(agentMT4Login);

		MockHttpSession session = login(agentMT4Login.toString());

		this.mockMvc
				.perform(
						get("/rest/clients.json").session(session)
								.param("agentId", new Integer(999).toString())
								.param("startRangeCloseTime", "01-02-2013")
								.param("endRangeCloseTime", "31-02-2013"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.response.status").value(
								RestResponse.Status.ERR.toString()))
				.andExpect(
						jsonPath("$.response.errorMessage")
								.value(messageSource
										.getMessage(
												"mt4agents.exception.security.notauthorised",
												null, Locale.US)));
	}

	@Test
	@Transactional
	public void getClientTrades_6307376() throws Exception {

		Integer agentMT4Login = 112;

		AgentUser agentUser = createRandomAgentUser(agentMT4Login);
		Agent agent = agentUser.getAgent();
		AgentClientDTO clientDTO = new AgentClientDTO();
		clientDTO.setLogin(6307376);
		clientDTO.setAgentId(agent.getId());
		clientDTO.setRegistrationDate(new Date());
		AgentClient agentClient = agentClientService.saveClient(clientDTO);

		String startDateCloseTime = "15-10-2012";
		String endDateCloseTime = "15-10-2012";

		MockHttpSession session = login(agentMT4Login.toString());

		this.mockMvc
				.perform(
						get("/rest/clients/trades.json")
								.session(session)
								.param("agentClientId",
										agentClient.getId().toString())
								.param("startRangeCloseTime",
										startDateCloseTime)
								.param("endRangeCloseTime", endDateCloseTime))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.response.status").value(
								RestResponse.Status.OK.toString()))
				.andExpect(jsonPath("$.response.payload").isArray());

	}

	@Test
	@Transactional
	public void getDownlineClientsTrade() throws Exception {
		AgentUser agentUser = createRandomAgentUser(110);
		Agent agent = agentUser.getAgent();

		Agent level2Agent1 = createRandomAgent(2);
		Agent level2Agent2 = createRandomAgent(3);

		agentRelationshipService.saveRelationship(agent.getId(),
				level2Agent1.getId());
		agentRelationshipService.saveRelationship(agent.getId(),
				level2Agent2.getId());

		AgentClientDTO clientDTO1 = new AgentClientDTO();
		clientDTO1.setLogin(mt4Logins.poll());
		clientDTO1.setAgentId(level2Agent1.getId());
		clientDTO1.setRegistrationDate(new Date());
		agentClientService.saveClient(clientDTO1);

		AgentClientDTO clientDTO2 = new AgentClientDTO();
		clientDTO2.setLogin(mt4Logins.poll());
		clientDTO2.setAgentId(level2Agent1.getId());
		clientDTO2.setRegistrationDate(new Date());
		agentClientService.saveClient(clientDTO2);

		AgentClientDTO clientDTO3 = new AgentClientDTO();
		clientDTO3.setLogin(mt4Logins.poll());
		clientDTO3.setAgentId(level2Agent2.getId());
		clientDTO3.setRegistrationDate(new Date());
		agentClientService.saveClient(clientDTO3);

		AgentClientDTO clientDTO4 = new AgentClientDTO();
		clientDTO4.setLogin(mt4Logins.poll());
		clientDTO4.setAgentId(level2Agent2.getId());
		clientDTO4.setRegistrationDate(new Date());
		agentClientService.saveClient(clientDTO4);

		MockHttpSession session = login(agent.getMt4Login().toString());

		this.mockMvc
				.perform(
						get(
								"/rest/clients/trades/downlines/"
										+ agent.getId() + ".json")
								.session(session)
								.param("agentId", agent.getId().toString())
								.param("startRangeCloseTime", "06-02-2013")
								.param("endRangeCloseTime", "06-02-2013"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.response.status").value(
								RestResponse.Status.OK.toString()))
				.andExpect(jsonPath("$.response.payload").isArray());
	}
}
