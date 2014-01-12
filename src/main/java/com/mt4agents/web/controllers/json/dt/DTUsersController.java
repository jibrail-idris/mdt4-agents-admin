package com.mt4agents.web.controllers.json.dt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mt4agents.dto.AgentDTO;
import com.mt4agents.dto.MT4UserDTO;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.MT4RemoteService;
import com.mt4agents.web.response.DTResponse;

@Controller
@RequestMapping("/dt/users")
public class DTUsersController {

	@Autowired
	private MT4RemoteService mt4RemoteService;

	@Autowired
	private AgentService agentService;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse index(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0) {

		// For now, only allow sorting by one column.
		DTResponse resp = new DTResponse();
		resp.setsEcho(sEcho);
		Integer usersCount = mt4RemoteService.getUsersCount(sSearch);
		resp.setiTotalDisplayRecords(usersCount);
		resp.setiTotalRecords(usersCount);
		List<List<?>> aaData = new ArrayList<List<?>>();
		List<MT4UserDTO> users = mt4RemoteService.getUsers(iDisplayStart,
				iDisplayLength, sSearch, iSortCol_0 + 1, sSortDir_0);
		Map<Integer, AgentDTO> agentDTOs = agentService
				.getAgentDTOsOrganisedByLogin();
		// Match up any agent assignment against a user.
		for (MT4UserDTO userDTO : users) {
			userDTO.setAssignment(agentDTOs.get(userDTO.getLogin()));
		}
		for (MT4UserDTO user : users) {

			String parentAgentLabel = "";
			AgentDTO agent = user.getAssignment();
			if (agent != null && agent.getParentAgentId() != null) {
				parentAgentLabel = agent.getParentAgentLabel();
			}

			aaData.add(Arrays.asList(user.getLogin(), user.getGroup(),
					user.getEnable(), user.getEnableChangePass(),
					user.getEnableReadOnly(), user.getPasswordPhone(),
					user.getName(), user.getCountry(), user.getCity(),
					user.getState(), user.getZipcode(), user.getAddress(),
					user.getPhone(), user.getEmail(), user.getComment(),
					user.getId(), user.getStatus(), user.getRegDate(),
					user.getLastDate(), user.getLeverage(),
					user.getAgentAccount(), user.getTimestamp(),
					user.getBalance(), user.getPrevMonthBalance(),
					user.getPrevBalance(), user.getCredit(),
					user.getInterestRate(), user.getTaxes(),
					user.getSendReports(), user.getUserColor(),
					user.getEquity(), user.getMargin(), user.getMarginLevel(),
					user.getMarginFree(), user.getModifyTime(),
					parentAgentLabel, ""));
		}
		resp.setAaData(aaData);
		return resp;
	}
}
