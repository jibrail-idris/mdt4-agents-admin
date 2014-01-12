package com.mt4agents.web.controllers.json.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.proxies.http.rest.utilities.RestUtils;
import com.base.proxies.response.RestResponse;
import com.mt4agents.dto.AgentClientDTO;
import com.mt4agents.dto.AgentDTO;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.UserService;
import com.mt4agents.transformers.AgentClientToAgentClientDTO;
import com.mt4agents.transformers.AgentToAgentDTO;
import com.mt4agents.web.propertyeditors.IntegerListPropertyEditor;
import com.mt4agents.web.propertyeditors.IntegerPropertyEditor;

/**
 * 
 * @author Jibrail Idris
 * 
 */
@Controller
@RequestMapping("/rest/agents")
public class RestAgentController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClientService agentClientService;

	@Autowired
	private AgentToAgentDTO agentToAgentDTO;

	@Autowired
	private AgentClientToAgentClientDTO agentClientToAgentClientDTO;

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Long.class, new IntegerPropertyEditor());
		binder.registerCustomEditor(List.class, new IntegerListPropertyEditor());
	}

	@ModelAttribute(value = "response")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<AgentDTO>> index() {
		RestResponse<List<AgentDTO>> resp = new RestResponse<List<AgentDTO>>();
		try {
			List<AgentDTO> agentDTOs = agentService.getAgentDTOs();
			resp.setPayload(agentDTOs);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> save(
			@RequestParam(required = false) Integer agentId,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String password1,
			@RequestParam(required = false) String password2,
			@RequestParam(required = false) Integer mt4Login,
			@RequestParam(required = false) Double commission,
			@RequestParam(required = false) Integer upline) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setAgentId(agentId);
			agentDTO.setLogin(mt4Login);
			agentDTO.setCommission(commission);
			agentDTO.setParentAgentId(upline);
			agentService.saveAgentWithNewAgentUser(agentDTO, username,
					password1, password2);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> update(@RequestParam Integer agentId,
			@RequestParam(required = false) Double commission,
			@RequestParam(required = false) Integer upline) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setAgentId(agentId);
			agentDTO.setCommission(commission);
			agentDTO.setParentAgentId(upline);
			agentService.saveAgent(agentDTO);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> delete(@RequestParam Integer agentId) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			agentService.deleteAgent(agentId);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<AgentDTO> get(@RequestParam Integer mt4Login) {
		RestResponse<AgentDTO> resp = new RestResponse<AgentDTO>();
		try {
			resp.setPayload(agentService.getAgentDTOByLogin(mt4Login));
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/clients/add", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> addClient(@RequestParam Integer agentId,
			@RequestParam Integer login) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			AgentClientDTO client = new AgentClientDTO();
			client.setAgentId(agentId);
			client.setLogin(login);
			client.setRegistrationDate(new Date());
			resp.setPayload(agentClientToAgentClientDTO
					.transform(agentClientService.saveClient(client)));
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/clients/delete", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> removeClient(
			@RequestParam Integer agentClientLogin) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			agentClientService.deleteClientByLogin(agentClientLogin);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

}