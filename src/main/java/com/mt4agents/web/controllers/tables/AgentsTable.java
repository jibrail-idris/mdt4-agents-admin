package com.mt4agents.web.controllers.tables;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mt4agents.dto.AgentDTO;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.MT4RemoteService;
import com.mt4agents.transformers.AgentToAgentDTO;

@Controller
@RequestMapping("/agent")
public class AgentsTable {

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private AgentClientService agentClientService;
	
	@Autowired
	private MT4RemoteService m4RemoteService;

	@Autowired
	private AgentToAgentDTO agentToAgentDTO;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String index(Model model) {
		List<AgentDTO> agentDTOs = agentService.getAgentDTOs();		
		model.addAttribute("agents", agentDTOs);
		return "agent/all";
	}

	@RequestMapping(value = "js", method = RequestMethod.GET)
	public String js(Model model) {
		List<AgentDTO> agentDTOs = agentService.getAgentDTOs();
		model.addAttribute("agents", agentDTOs);
		return "agents/js";
	}
}
