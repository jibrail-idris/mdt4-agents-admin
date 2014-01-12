package com.mt4agents.web.controllers.json.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.proxies.http.rest.utilities.RestUtils;
import com.base.proxies.response.RestResponse;
import com.mt4agents.dto.UserDTO;
import com.mt4agents.services.UserService;

@Controller
@RequestMapping("/rest/profile")
public class RestProfileController {

	@Autowired
	private UserService userService;

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/password/change", method = RequestMethod.POST, produces = "application/json;UTF-8")
	public RestResponse<Object> passwordChange(
			@RequestParam String currentPassword,
			@RequestParam String newPassword1,
			@RequestParam String newPassword2, Principal principal) {
		RestResponse<Object> resp = new RestResponse<Object>();
		try {
			UserDTO passwordChange = new UserDTO();
			passwordChange.setUsername(principal.getName());
			passwordChange.setPassword(currentPassword);
			passwordChange.setNewPassword1(newPassword1);
			passwordChange.setNewPassword2(newPassword2);
			userService.saveUser(passwordChange);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}
}
