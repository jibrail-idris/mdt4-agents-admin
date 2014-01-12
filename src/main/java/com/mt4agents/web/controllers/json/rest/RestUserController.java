package com.mt4agents.web.controllers.json.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.proxies.http.rest.utilities.RestUtils;
import com.base.proxies.response.RestResponse;
import com.mt4agents.services.UserService;

@Controller
@RequestMapping("/rest/users")
public class RestUserController {

	@Autowired
	private UserService userService;

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/password/reset", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> reset(@RequestParam Integer agentId,
			@RequestParam String newPassword) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			userService.resetAgentPassword(agentId, newPassword);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}
}
