package com.mt4agents.web.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.mt4agents.services.MT4RemoteService.TradeType;

public class TradeTypePropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		if (StringUtils.hasLength(text) && text.startsWith("tt:")) {
			String[] tokens = text.split(":");
			if (tokens.length == 2) {
				String tradeTypeString = tokens[1];
				if (tradeTypeString.equals("c")) {
					setValue(TradeType.CLOSE);
				} else if (tradeTypeString.equals("o")) {
					setValue(TradeType.OPEN);
				} else if (tradeTypeString.equals("b")) {
					setValue(TradeType.BALANCE);
				}
			}
		}
	}
}
