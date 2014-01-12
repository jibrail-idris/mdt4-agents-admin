package com.mt4agents.web.controllers.json.dt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mt4agents.dto.AgentClientDTO;
import com.mt4agents.dto.AgentDTO;
import com.mt4agents.dto.MT4UserDTO;
import com.mt4agents.services.AgentService;
import com.mt4agents.transformers.AgentToAgentDTO;
import com.mt4agents.web.response.DTResponse;

@Controller
@RequestMapping("/dt/agents")
public class DTAgentsController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentToAgentDTO agentToAgentDTO;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse agents(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0) {

		DTResponse resp = new DTResponse();
		resp.setsEcho(sEcho);
		Integer agentsCount = agentService.getAgentsCount(sSearch);
		resp.setiTotalDisplayRecords(agentsCount);
		resp.setiTotalRecords(agentsCount);

		List<List<?>> aaData = new ArrayList<List<?>>();
		Map<Integer, AgentDTO> agents = agentService.findAgentDTOs(sSearch,
				iDisplayStart, iDisplayLength);
		for (AgentDTO agent : agents.values()) {
			MT4UserDTO mt4User = agent.getMt4User();
			Map<String, Object> manageMap = new HashMap<String, Object>();
			Map<Integer, AgentClientDTO> clients = agent.getClientsByLogin();
			manageMap.put("clients", clients.keySet());
			manageMap.put("agent", agent);
			if (mt4User != null) {
				aaData.add(Arrays.asList(mt4User.getLogin(),
						agent.getParentAgentLabel(), mt4User.getGroup(),
						mt4User.getEnable(), mt4User.getEnableChangePass(),
						mt4User.getEnableReadOnly(),
						mt4User.getPasswordPhone(), mt4User.getName(),
						mt4User.getCountry(), mt4User.getCity(),
						mt4User.getState(), mt4User.getZipcode(),
						mt4User.getAddress(), mt4User.getPhone(),
						mt4User.getEmail(), mt4User.getComment(),
						mt4User.getRegDate(), mt4User.getLastDate(),
						mt4User.getLeverage(), clients.size(),
						agent.getCommission(), manageMap));
			}
		}
		resp.setAaData(aaData);
		return resp;
	}
}
