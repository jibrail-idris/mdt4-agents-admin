package com.mt4agents.web.controllers.json.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.proxies.http.rest.utilities.RestUtils;
import com.base.proxies.response.RestResponse;
import com.mt4agents.dto.MT4UserDTO;
import com.mt4agents.services.MT4RemoteService;

/**
 * 
 * @author Jibrail Idris
 *
 */
@Controller
@RequestMapping("/rest/mt4")
public class RestMT4RemoteController {

	@Autowired
	private MT4RemoteService mt4RemoteService;
	
	@ModelAttribute(value = "response")
	@RequestMapping(value="/users", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4UserDTO>> users() {
		RestResponse<List<MT4UserDTO>> resp = new RestResponse<List<MT4UserDTO>>();
		try {
//			List<MT4UserDTO> users = mt4RemoteService.getUsersSimple();
//			resp.setPayload(users);
//			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}
}
