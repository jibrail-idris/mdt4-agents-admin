package com.mt4agents.web.controllers.tabs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agent/my")
public class MyAgentTab {
	
	@RequestMapping
	public String index(Model model) {
		return "agent/my";
	}
}
