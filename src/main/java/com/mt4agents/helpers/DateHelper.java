package com.mt4agents.helpers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.mt4agents.formatters.DateFormatter;

public class DateHelper {
	public Map<String, Date> makeRangeDates(String startRangeOpenTime,
			String endRangeOpenTime, String startRangeCloseTime,
			String endRangeCloseTime) throws ParseException {

		Date startOpenTime = null;
		Date endOpenTime = null;
		Date startCloseTime = null;
		Date endCloseTime = null;
		if (startRangeOpenTime != null && endRangeOpenTime != null
				&& StringUtils.hasLength(startRangeOpenTime)
				&& StringUtils.hasLength(endRangeOpenTime)) {
			startOpenTime = DateFormatter.parse(DateFormatter.Type.GENERAL,
					new StringBuilder(startRangeOpenTime).append(" 00:00:00")
							.toString());
			endOpenTime = DateFormatter.parse(DateFormatter.Type.GENERAL,
					new StringBuilder(endRangeOpenTime).append(" 23:59:59")
							.toString());
		}
		if (startRangeCloseTime != null && endRangeCloseTime != null
				&& StringUtils.hasLength(startRangeCloseTime)
				&& StringUtils.hasLength(endRangeCloseTime)) {
			startCloseTime = DateFormatter.parse(DateFormatter.Type.GENERAL,
					new StringBuilder(startRangeCloseTime).append(" 00:00:00")
							.toString());
			endCloseTime = DateFormatter.parse(DateFormatter.Type.GENERAL,
					new StringBuilder(endRangeCloseTime).append(" 23:59:59")
							.toString());
		}

		Map<String, Date> dates = new HashMap<String, Date>();
		dates.put("startOpenTime", startOpenTime);
		dates.put("endOpenTime", endOpenTime);
		dates.put("startCloseTime", startCloseTime);
		dates.put("endCloseTime", endCloseTime);
		return dates;

	}
}
