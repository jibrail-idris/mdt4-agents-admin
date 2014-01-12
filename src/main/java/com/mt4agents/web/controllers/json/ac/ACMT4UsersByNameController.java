package com.mt4agents.web.controllers.json.ac;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mt4agents.dto.MT4UserDTO;
import com.mt4agents.services.MT4RemoteService;
import com.mt4agents.web.response.ACResponse;

@Controller
@RequestMapping("/ac/mt4users/search")
public class ACMT4UsersByNameController {

	@Autowired
	private MT4RemoteService mt4remoteService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public List<ACResponse> index(
			@RequestParam(value = "term", required = false) String term) {
		List<ACResponse> resp = new ArrayList<ACResponse>();
		List<MT4UserDTO> users = mt4remoteService.getUsersByName(term);
		for (MT4UserDTO user : users) {
			ACResponse acResp = new ACResponse();
			Integer login = user.getLogin();
			acResp.setId(login.toString());
			String label = new StringBuilder(user.getName()).append(" [")
					.append(login).append("]").toString();
			acResp.setLabel(label);
			acResp.setValue(login.toString());
			resp.add(acResp);
		}
		return resp;
	}
}
