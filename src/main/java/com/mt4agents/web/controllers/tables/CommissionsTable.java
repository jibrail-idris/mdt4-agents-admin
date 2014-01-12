package com.mt4agents.web.controllers.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mt4agents.services.AgentService;

@Controller
@RequestMapping("/commission")
public class CommissionsTable {
	@Autowired
	private AgentService agentService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String index() {
		return "commission/all";
	}
}
