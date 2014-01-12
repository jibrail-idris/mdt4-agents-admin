package com.mt4agents.services.install;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mt4agents.dto.UserDTO;
import com.mt4agents.entities.users.User;
import com.mt4agents.exceptions.UserServiceException;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentRelationshipService;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.UserService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class InstallService {

	private static final Logger logger = Logger.getLogger(InstallService.class);

	/**
	 * Attempts to get admin user, if not admin user is not present create with
	 * username admin and password admin.
	 * 
	 * @param userService
	 * @throws Exception
	 */
	public InstallService(UserService userService, AgentService agentService,
			AgentClientService agentClientService,
			AgentRelationshipService agentRelationshipService) throws Exception {
		install(userService, agentService, agentClientService,
				agentRelationshipService);
	}

	private void install(UserService userService, AgentService agentService,
			AgentClientService agentClientService,
			AgentRelationshipService agentRelationshipService)
			throws UserServiceException, Exception {
		User user = userService.getUserByUsername("admin");
		if (user == null) {

			logger.info("Admin/admin is not found. Creating admin account.");

			UserDTO userDTO = new UserDTO();
			userDTO.setUsername("admin");
			userDTO.setPassword("admin");
			userDTO.setNewPassword1("admin");
			userDTO.setNewPassword2("admin");
			userDTO.setRole("admin");

			userService.saveUser(userDTO);
			logger.info("Admin account created.");
		}
		
//		logger.info("WARNING : TEST DATA INCLUDED IN BUILD!!");
//		Integer agentMt4Login = 110;
//		Integer agentDownline1Mt4Login = 111;
//		Integer agentDownline2Mt4Login = 112;
//		
//		Integer agentDownline11Mt4Login = 113;
//		Integer agentDownline12Mt4Login = 115;
//		Integer agentDownline13Mt4Login = 116;
//		
//		Integer agentDownline21Mt4Login = 118;
//		Integer agentDownline22Mt4Login = 151;
//		Integer agentDownline23Mt4Login = 152;
//		Integer agentDownline24Mt4Login = 153;
//
//		if (!agentService.checkExistsByMT4Login(agentMt4Login)) {
//
//			AgentDTO agentDTO = new AgentDTO();
//			agentDTO.setLogin(agentMt4Login);
//			agentDTO.setCommission(20.0);
//
//			AgentDTO agentDTO1 = new AgentDTO();
//			agentDTO1.setLogin(agentDownline1Mt4Login);
//			agentDTO1.setCommission(19.0);
//
//			AgentDTO agentDTO2 = new AgentDTO();
//			agentDTO2.setLogin(agentDownline2Mt4Login);
//			agentDTO2.setCommission(18.0);
//			
//			
//			/* - Agent1 Downline */
//			AgentDTO agentDTO11 = new AgentDTO();
//			agentDTO11.setLogin(agentDownline11Mt4Login);
//			agentDTO11.setCommission(17.0);
//			
//			AgentDTO agentDTO12 = new AgentDTO();
//			agentDTO12.setLogin(agentDownline12Mt4Login);
//			agentDTO12.setCommission(16.0);
//			
//			AgentDTO agentDTO13 = new AgentDTO();
//			agentDTO13.setLogin(agentDownline13Mt4Login);
//			agentDTO13.setCommission(15.0);
//			
//			
//			/* - Agent2 Downline */
//			AgentDTO agentDTO21 = new AgentDTO();
//			agentDTO21.setLogin(agentDownline21Mt4Login);
//			agentDTO21.setCommission(14.0);
//			
//			AgentDTO agentDTO22 = new AgentDTO();
//			agentDTO22.setLogin(agentDownline22Mt4Login);
//			agentDTO22.setCommission(13.0);
//			
//			AgentDTO agentDTO23 = new AgentDTO();
//			agentDTO23.setLogin(agentDownline23Mt4Login);
//			agentDTO23.setCommission(12.0);
//			
//			AgentDTO agentDTO24 = new AgentDTO();
//			agentDTO24.setLogin(agentDownline24Mt4Login);
//			agentDTO24.setCommission(11.0);
//
//			logger.info("Creating agent " + agentMt4Login + ".");
//			Agent agent = agentService.saveAgent(agentDTO);
//			logger.info("Creating agent " + agentDownline1Mt4Login + ".");
//			Agent downline1 = agentService.saveAgent(agentDTO1);
//			logger.info("Creating agent " + agentDownline2Mt4Login + ".");
//			Agent downline2 = agentService.saveAgent(agentDTO2);
//			
//			logger.info("Creating agent " + agentDownline11Mt4Login + ".");
//			Agent downline11 = agentService.saveAgent(agentDTO11);
//			logger.info("Creating agent " + agentDownline12Mt4Login + ".");
//			Agent downline12 = agentService.saveAgent(agentDTO12);
//			logger.info("Creating agent " + agentDownline13Mt4Login + ".");
//			Agent downline13 = agentService.saveAgent(agentDTO13);
//			
//			logger.info("Creating agent " + agentDownline21Mt4Login + ".");
//			Agent downline21 = agentService.saveAgent(agentDTO21);
//			logger.info("Creating agent " + agentDownline22Mt4Login + ".");
//			Agent downline22 = agentService.saveAgent(agentDTO22);
//			logger.info("Creating agent " + agentDownline23Mt4Login + ".");
//			Agent downline23 = agentService.saveAgent(agentDTO23);
//			logger.info("Creating agent " + agentDownline24Mt4Login + ".");
//			Agent downline24 = agentService.saveAgent(agentDTO24);
//
//			agentRelationshipService.saveRelationship(agent.getId(),
//					downline1.getId());
//			agentRelationshipService.saveRelationship(agent.getId(),
//					downline2.getId());
//			
//			agentRelationshipService.saveRelationship(downline1.getId(), downline11.getId());
//			agentRelationshipService.saveRelationship(downline1.getId(), downline12.getId());
//			agentRelationshipService.saveRelationship(downline1.getId(), downline13.getId());
//			
//			agentRelationshipService.saveRelationship(downline2.getId(), downline21.getId());
//			agentRelationshipService.saveRelationship(downline2.getId(), downline22.getId());
//			agentRelationshipService.saveRelationship(downline2.getId(), downline23.getId());
//			agentRelationshipService.saveRelationship(downline2.getId(), downline24.getId());
//
//			agentDTO.setAgentId(agent.getId());
//
//			UserDTO userDTO = new UserDTO();
//			userDTO.setUsername(agentDTO.getLogin().toString());
//			userDTO.setPassword(agentDTO.getLogin().toString());
//			userDTO.setNewPassword1(agentDTO.getLogin().toString());
//			userDTO.setNewPassword2(agentDTO.getLogin().toString());
//			userDTO.setAgentDTO(agentDTO);
//			userDTO.setRole("agent");
//
//			userService.saveUser(userDTO);
//			logger.info("Agent " + agentMt4Login + " created.");
//			
//			agentDTO1.setAgentId(downline1.getId());
//			
//			userDTO = new UserDTO();
//			userDTO.setUsername(agentDTO1.getLogin().toString());
//			userDTO.setPassword(agentDTO1.getLogin().toString());
//			userDTO.setNewPassword1(agentDTO1.getLogin().toString());
//			userDTO.setNewPassword2(agentDTO1.getLogin().toString());
//			userDTO.setAgentDTO(agentDTO1);
//			userDTO.setRole("agent");
//
//			userService.saveUser(userDTO);
//			logger.info("Agent " + agentDownline1Mt4Login + " created.");
//
//			AgentClientDTO clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307902);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(14);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(15);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300709);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307283);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6302610);
//			clientDTO.setAgentId(agent.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6301939);
//			clientDTO.setAgentId(downline1.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6303867);
//			clientDTO.setAgentId(downline1.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300276);
//			clientDTO.setAgentId(downline1.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300336);
//			clientDTO.setAgentId(downline2.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307098);
//			clientDTO.setAgentId(downline2.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307027);
//			clientDTO.setAgentId(downline2.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			/////////////////////////////////////////////////
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6303012);
//			clientDTO.setAgentId(downline11.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300328);
//			clientDTO.setAgentId(downline11.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307221);
//			clientDTO.setAgentId(downline11.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300087);
//			clientDTO.setAgentId(downline11.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6300306);
//			clientDTO.setAgentId(downline11.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			/////////////////////////////////////////////////
//			
//			/////////////////////////////////////////////////
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307026);
//			clientDTO.setAgentId(downline12.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307375);
//			clientDTO.setAgentId(downline12.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6303635);
//			clientDTO.setAgentId(downline12.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			/////////////////////////////////////////////////
//			
//			/////////////////////////////////////////////////
//			clientDTO = new AgentClientDTO();
//			clientDTO.setLogin(6307059);
//			clientDTO.setAgentId(downline13.getId());
//			clientDTO.setRegistrationDate(new Date());
//			agentClientService.saveClient(clientDTO);
//			/////////////////////////////////////////////////
//		}
	}
}
