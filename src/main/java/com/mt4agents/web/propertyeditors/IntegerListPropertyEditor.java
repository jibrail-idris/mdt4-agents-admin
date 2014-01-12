package com.mt4agents.web.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class IntegerListPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		if (StringUtils.hasLength(text)) {
			// setValue(Integer.parseInt(text));
			String[] elements = StringUtils.delimitedListToStringArray(text,
					",");
			List<Integer> ints = new ArrayList<Integer>();
			for (String element : elements) {
				ints.add(Integer.parseInt(element));
			}
			setValue(ints);
		}
	}
}
