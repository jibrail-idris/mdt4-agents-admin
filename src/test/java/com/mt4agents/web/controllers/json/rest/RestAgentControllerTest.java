package com.mt4agents.web.controllers.json.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.web.WebDelegatingSmartContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.base.proxies.response.RestResponse;
import com.mt4agents.dao.AgentDAO;
import com.mt4agents.entities.Agent;
import com.mt4agents.entities.AgentClient;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentService;

/**
 * 
 * @author Jibrail Idris
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/json-servlet.xml" }, loader = WebDelegatingSmartContextLoader.class)
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class RestAgentControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClientService agentClientService;

	@Autowired
	private AgentDAO agentDAO;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
	}

	@Test
	@Transactional
	public void addClientOne() throws Exception {
		Integer agent1Login = 1;

		Double agent1Commission = 13.0;

		Agent agent1 = new Agent();
		agent1.setMt4Login(agent1Login);
		agent1.setCommission(agent1Commission);

		agentDAO.save(agent1);

		Integer client1Login = 6307376;

		String clientLogins = String.format("%d", client1Login);

		this.mockMvc
				.perform(
						post("/rest/agents/clients/add.json").param("agentId",
								agent1.getId().toString()).param("logins",
								clientLogins))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.response.status").value(
								RestResponse.Status.OK.toString()));
		Agent agent = agentService.getAgentById(agent1.getId());
		AgentClient client = agent.getClientByLogin(client1Login);

		Assert.assertNotNull(agent);
		Assert.assertNotNull(client);
		Assert.assertEquals(client1Login, client.getMt4Login());
	}

	@Test
	@Transactional
	public void addOneNewClientUpdateOneExistingClient() {
		Integer agent1Login = 6307376;

		Double agent1Commission = 13.0;

		Agent agent1 = new Agent();
		agent1.setMt4Login(agent1Login);
		agent1.setCommission(agent1Commission);

		agentDAO.save(agent1);
	}

	// @Test
	// @Transactional
	public void commissions() throws Exception {

		Integer agent1Login = 6307376;
		Integer agent2Login = 6300986;

		Double agent1Commission = 13.0;
		Double agent2Commission = 14.0;

		Agent agent1 = new Agent();
		agent1.setMt4Login(agent1Login);
		agent1.setCommission(agent1Commission);

		Agent agent2 = new Agent();
		agent2.setMt4Login(agent2Login);
		agent2.setCommission(agent2Commission);

		agentDAO.save(agent1);
		agentDAO.save(agent2);

		String startRangeDate = "06-02-2013";
		String endRangeDate = "06-02-2013";

		this.mockMvc
				.perform(
						get("/rest/agents/commissions.json")
								.param("agentIds",
										agent1.getId() + "," + agent2.getId())
								.param("startRangeCloseTime", startRangeDate)
								.param("endRangeCloseTime", endRangeDate))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.response.status").value(
								RestResponse.Status.OK.toString()))
				.andExpect(jsonPath("$.response.payload").exists());
	}
}
