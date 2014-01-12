package com.mt4agents.web.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mt4agents.dto.AgentDTO;

public class AgentDTOValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return AgentDTO.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commission",
				"mt4agents.exception.agent.commission.notset");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
				"mt4agents.exception.agent.mt4login.notassigned");
	}
}
