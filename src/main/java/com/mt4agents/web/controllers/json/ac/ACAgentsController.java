package com.mt4agents.web.controllers.json.ac;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mt4agents.dto.AgentDTO;
import com.mt4agents.services.AgentService;
import com.mt4agents.transformers.AgentToAgentDTO;
import com.mt4agents.web.response.ACResponse;

@Controller
@RequestMapping("/ac/agents/search")
public class ACAgentsController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentToAgentDTO agentToAgentDTO;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public List<ACResponse> index(
			@RequestParam(value = "term", required = false) String term) {
		List<ACResponse> resp = new ArrayList<ACResponse>();
		List<AgentDTO> agents = agentService.getAgentDTOs(term);
		for (AgentDTO agent : agents) {
			ACResponse acResp = new ACResponse();
			Integer agentId = agent.getAgentId();
			acResp.setId(agentId.toString());
			String label = agent.getLabel();
			acResp.setLabel(label);
			acResp.setValue(agentId.toString());
			resp.add(acResp);
		}
		return resp;
	}
}
