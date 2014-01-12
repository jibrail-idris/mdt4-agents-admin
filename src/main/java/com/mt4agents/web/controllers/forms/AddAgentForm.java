package com.mt4agents.web.controllers.forms;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mt4agents.dto.AgentDTO;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.MT4RemoteService;
import com.mt4agents.transformers.AgentToAgentDTO;
import com.mt4agents.web.propertyeditors.StrictNumberPropertyEditor;
import com.mt4agents.web.validation.AgentDTOValidator;

@Deprecated
@Controller
@RequestMapping("/agent")
@SessionAttributes(value = "agent", types = AgentDTO.class)
public class AddAgentForm {

	private static final Logger logger = Logger.getLogger(AddAgentForm.class);

	@Autowired
	private AgentService agentService;
	@Autowired
	private MT4RemoteService mt4RemoteService;

	@Autowired
	private AgentToAgentDTO agentHelper;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.registerCustomEditor(Float.class,
				new StrictNumberPropertyEditor());
	}

	@ModelAttribute(value = "agent")
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public AgentDTO index(Model model) {

		populateModel(model);

		AgentDTO agentDTO = new AgentDTO();
		model.addAttribute("agent", agentDTO);

		return agentDTO;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@ModelAttribute(value = "agent") AgentDTO agent,
			BindingResult result, SessionStatus status, Model model)
			throws Exception {

		new AgentDTOValidator().validate(agent, result);

		if (result.hasErrors()) {
			populateModel(model);
			return "agent/new";
		} else {
			agentService.saveAgent(agent);
			status.setComplete();
			return "redirect:new";
		}
	}

	private void populateModel(Model model) {
//		List<Agent> agents = agentService.getAgents();
//		List<AgentDTO> agentDTOs = agentHelper.transformMany(agents);
//		model.addAttribute("agents", agentDTOs);
//
//		List<MT4UserDTO> mt4Users = mt4RemoteService.getUsers();
//		model.addAttribute("mt4Users", mt4Users);
	}
}
