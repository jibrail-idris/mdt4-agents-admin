package com.mt4agents.web.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class IntegerPropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		if (StringUtils.hasLength(text)) {
			setValue(Integer.parseInt(text));
		}
	}
}
