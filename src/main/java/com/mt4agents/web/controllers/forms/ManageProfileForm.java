package com.mt4agents.web.controllers.forms;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mt4agents.dto.UserDTO;
import com.mt4agents.entities.users.User;
import com.mt4agents.services.UserService;
import com.mt4agents.transformers.UserToUserDTO;

@Controller
@RequestMapping("/profile")
@SessionAttributes(value = "profile", types = UserDTO.class)
public class ManageProfileForm {

	@Autowired
	private UserService userService;

	@Autowired
	private UserToUserDTO userToUserDTO;

	@ModelAttribute(value = "profile")
	@RequestMapping(method = RequestMethod.GET)
	public UserDTO index(Model model, Principal principal) {
		return userService.getUserDTOByUsername(principal.getName());
	}

	@RequestMapping(value = "/password/submit", method = RequestMethod.POST)
	public String passwordChange(
			@ModelAttribute(value = "profile") UserDTO profile,
			BindingResult result, SessionStatus status) {

		return null;
	}
}
