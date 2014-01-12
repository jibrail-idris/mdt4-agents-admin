package com.mt4agents.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/i8ln")
public class I8LNController {
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	public String index() {
		return "i8ln";
	}
}
