package com.mt4agents.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.web.WebDelegatingSmartContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mt4agents.dao.AgentDAO;
import com.mt4agents.dto.AgentDTO;
import com.mt4agents.dto.UserDTO;
import com.mt4agents.entities.Agent;
import com.mt4agents.entities.users.AgentUser;
import com.mt4agents.exceptions.UserServiceException;
import com.mt4agents.services.UserService;

/**
 * 
 * @author Jibrail Idris
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" }, loader = WebDelegatingSmartContextLoader.class)
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class WebBaseTest {

	public static final String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm:ss");

	@Autowired
	private WebApplicationContext webApplicationContext;
	protected MockMvc mockMvc;

	@Autowired
	private AgentDAO agentDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	protected PriorityQueue<Integer> mt4Logins;

	public WebBaseTest() {
		initMt4Logins();
	}

	public FilterChainProxy getSecurityFilterChain() {
		return springSecurityFilterChain;
	}

	// http://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication
	protected UsernamePasswordAuthenticationToken getPrincipal(String username) {

		UserDetails user = userService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, user.getPassword(), user.getAuthorities());

		return authentication;
	}

	protected MockHttpSession login(String username) {
		final UsernamePasswordAuthenticationToken principal = getPrincipal(username);
		MockHttpSession session = new MockHttpSession();
		// http://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication
		session.setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				new SecurityContext() {

					private static final long serialVersionUID = 8799692294437044050L;

					@Override
					public Authentication getAuthentication() {
						return principal;
					}

					@Override
					public void setAuthentication(Authentication authentication) {
					}
				});
		return session;
	}

	protected Agent createRandomAgent() {
		Random rnd = new Random();
		Agent randomAgent = new Agent();
		randomAgent.setCommission(rnd.nextDouble());
		randomAgent.setMt4Login(mt4Logins.poll());
		agentDAO.save(randomAgent);
		return randomAgent;
	}

	protected Agent createRandomAgent(Integer mt4Login) {
		Random rnd = new Random();
		Agent randomAgent = new Agent();
		randomAgent.setCommission(rnd.nextDouble());
		randomAgent.setMt4Login(mt4Login);
		agentDAO.save(randomAgent);
		return randomAgent;
	}

	protected Agent createRandomAgent(Integer mt4Login, Double commission) {
		Agent randomAgent = new Agent();
		randomAgent.setCommission(commission);
		randomAgent.setMt4Login(mt4Login);
		agentDAO.save(randomAgent);
		return randomAgent;
	}

	protected AgentUser createRandomAgentUser() throws UserServiceException {
		Agent agent = createRandomAgent();
		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setAgentId(agent.getId());
		UserDTO agentUserDTO = new UserDTO();
		agentUserDTO.setAgentDTO(agentDTO);
		String mt4Login = agent.getMt4Login().toString();
		agentUserDTO.setUsername(mt4Login);
		agentUserDTO.setPassword(mt4Login);
		agentUserDTO.setRole(AgentUser.ROLE);
		return (AgentUser) userService.saveUser(agentUserDTO);
	}

	protected AgentUser createRandomAgentUser(Integer mt4Login)
			throws UserServiceException {
		Agent agent = createRandomAgent(mt4Login);
		AgentDTO agentDTO = new AgentDTO();
		agentDTO.setAgentId(agent.getId());
		UserDTO agentUserDTO = new UserDTO();
		agentUserDTO.setAgentDTO(agentDTO);
		agentUserDTO.setUsername(mt4Login.toString());
		agentUserDTO.setPassword(mt4Login.toString());
		agentUserDTO.setRole(AgentUser.ROLE);
		return (AgentUser) userService.saveUser(agentUserDTO);
	}

	protected Date getDate(String dateString) throws ParseException {
		return simpleDateFormat.parse(dateString);
	}

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
	}

	private void initMt4Logins() {
		mt4Logins = new PriorityQueue<Integer>();
		mt4Logins.add(6307376); // 1283 trades count
		mt4Logins.add(6300986); // 1221 trades count
		mt4Logins.add(6300759); // 1219 trades count
		mt4Logins.add(6307510); // 1090 trades count
		mt4Logins.add(6306793); // 1086 trades count
		mt4Logins.add(6300280); // 1028 trades count
		mt4Logins.add(6307950); // 905 trades count
		mt4Logins.add(6307021); // 880 trades count
		mt4Logins.add(6307060); // 868 trades count
		mt4Logins.add(6303520); // 851 trades count
		mt4Logins.add(6305687); // 850 trades count
	}

	@Test
	public void dummy() {
	}
}
