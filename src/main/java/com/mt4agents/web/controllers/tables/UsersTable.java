package com.mt4agents.web.controllers.tables;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UsersTable {
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String index(Model model) {
		return "user/all";
	}
}
