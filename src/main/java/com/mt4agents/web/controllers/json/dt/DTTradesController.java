package com.mt4agents.web.controllers.json.dt;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mt4agents.dto.MT4TradeDTO;
import com.mt4agents.exceptions.MT4RemoteServiceException;
import com.mt4agents.exceptions.RestAgentClientControllerException;
import com.mt4agents.formatters.DateFormatter;
import com.mt4agents.formatters.NumberFormatter;
import com.mt4agents.formatters.DateFormatter.Type;
import com.mt4agents.helpers.DateHelper;
import com.mt4agents.helpers.SecurityHelper;
import com.mt4agents.services.ClientTradesService;
import com.mt4agents.web.response.DTResponse;

@Controller
@RequestMapping("/dt/trades")
public class DTTradesController {

	@Autowired
	private ClientTradesService clientTradesService;

	@Autowired
	private SecurityHelper securityHelper;

	@Autowired
	private DateHelper dateHelper;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{agentId}/clients/open", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse open(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeOpenTime,
			@RequestParam String endRangeOpenTime, Principal principal)
			throws RestAgentClientControllerException, ParseException,
			MT4RemoteServiceException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(startRangeOpenTime,
				endRangeOpenTime, null, null);
		Date startOpenTime = dates.get("startOpenTime");
		Date endOpenTime = dates.get("endOpenTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService.getClientsOpenTradesCount(
				agentId, startOpenTime, endOpenTime, sSearch);
		List<MT4TradeDTO> trades = clientTradesService.getClientsOpenTrades(
				agentId, startOpenTime, endOpenTime, iDisplayStart,
				iDisplayLength, sSearch, iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
						trade.getTicket(), 
						trade.getLogin(), 
						trade.getSymbol(), 
						trade.getDigits(), 
						trade.getCmdLabel(),
						NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100), DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
						trade.getOpenPrice(), 
						trade.getSl(), 
						trade.getTp(),
						trade.getTaxes(), 
						trade.getProfit(), 
						trade.getComment(),
						trade.getInternalID(), 
						trade.getMarginRate(),
						DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
						trade.getModifyTime()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{agentId}/clients/close", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse close(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeCloseTime,
			@RequestParam String endRangeCloseTime, Principal principal)
			throws RestAgentClientControllerException, ParseException,
			MT4RemoteServiceException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(null, null,
				startRangeCloseTime, endRangeCloseTime);
		Date startCloseTime = dates.get("startCloseTime");
		Date endCloseTime = dates.get("endCloseTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService.getClientsCloseTradesCount(
				agentId, startCloseTime, endCloseTime, sSearch);

		List<MT4TradeDTO> trades = clientTradesService.getClientsCloseTrades(
				agentId, startCloseTime, endCloseTime, iDisplayStart,
				iDisplayLength, sSearch, iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
					trade.getTicket(), 
					trade.getLogin(), 
					trade.getSymbol(), 
					trade.getDigits(), 
					trade.getCmdLabel(),
					NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100), 
					DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
					trade.getOpenPrice(), 
					trade.getSl(), 
					trade.getTp(),
					DateFormatter.parse(Type.MYSQL, trade.getCloseTime()),
					trade.getClosePrice(), 
					trade.getTaxes(), 
					trade.getProfit(),
					trade.getComment(), 
					trade.getInternalID(), 
					trade.getMarginRate(), 
					DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
					trade.getModifyTime(), 
					trade.getCommission()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{agentId}/clients/balance", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse balance(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeCloseTime,
			@RequestParam String endRangeCloseTime, Principal principal)
			throws RestAgentClientControllerException, ParseException,
			MT4RemoteServiceException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(null, null,
				startRangeCloseTime, endRangeCloseTime);
		Date startCloseTime = dates.get("startCloseTime");
		Date endCloseTime = dates.get("endCloseTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService.getClientsBalanceTradesCount(
				agentId, startCloseTime, endCloseTime, sSearch);

		List<MT4TradeDTO> trades = clientTradesService.getClientsBalanceTrades(
				agentId, startCloseTime, endCloseTime, iDisplayStart,
				iDisplayLength, sSearch, iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
						trade.getTicket(), 
						trade.getLogin(), 
						trade.getSymbol(), 
						trade.getDigits(), 
						trade.getCmdLabel(),
						NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100), 
						DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
						trade.getOpenPrice(), 
						trade.getSl(), 
						trade.getTp(),
						DateFormatter.parse(Type.MYSQL, trade.getCloseTime()),
						trade.getClosePrice(), 
						trade.getTaxes(), 
						trade.getProfit(),
						trade.getComment(), 
						trade.getInternalID(), 
						trade.getMarginRate(), 
						DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
						trade.getModifyTime()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downlines/{agentId}/clients/open", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse downlinesOpen(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeOpenTime,
			@RequestParam String endRangeOpenTime, Principal principal)
			throws RestAgentClientControllerException, ParseException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(startRangeOpenTime,
				endRangeOpenTime, null, null);
		Date startOpenTime = dates.get("startOpenTime");
		Date endOpenTime = dates.get("endOpenTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService
				.getDownlineClientsTradesCount(agentId, startOpenTime,
						endOpenTime, null, null, sSearch);

		List<MT4TradeDTO> trades = clientTradesService
				.getDownlineClientsTrades(agentId, startOpenTime, endOpenTime,
						null, null, iDisplayStart, iDisplayLength, sSearch,
						iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
					trade.getAgentDTO().getLogin(), 
					trade.getTicket(), 
					trade.getLogin(), 
					trade.getSymbol(), 
					trade.getDigits(), 
					trade.getCmdLabel(), 
					NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100),
					DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
					trade.getOpenPrice(), 
					trade.getSl(), 
					trade.getTp(),
					trade.getTaxes(), 
					trade.getProfit(), 
					trade.getComment(),
					trade.getInternalID(), 
					trade.getMarginRate(),
					DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
					trade.getModifyTime()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downlines/{agentId}/clients/close", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse downlinesClose(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeCloseTime,
			@RequestParam String endRangeCloseTime, Principal principal)
			throws RestAgentClientControllerException, ParseException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(null, null,
				startRangeCloseTime, endRangeCloseTime);
		Date startCloseTime = dates.get("startCloseTime");
		Date endCloseTime = dates.get("endCloseTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService
				.getDownlineClientsTradesCount(agentId, null, null,
						startCloseTime, endCloseTime, sSearch);

		List<MT4TradeDTO> trades = clientTradesService
				.getDownlineClientsTrades(agentId, null, null, startCloseTime,
						endCloseTime, iDisplayStart, iDisplayLength, sSearch,
						iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
					trade.getAgentDTO().getLogin(), 
					trade.getTicket(), 
					trade.getLogin(), 
					trade.getSymbol(), 
					trade.getDigits(), 
					trade.getCmdLabel(), 
					NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100),
					DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
					trade.getOpenPrice(), 
					trade.getSl(), 
					trade.getTp(),
					DateFormatter.parse(Type.MYSQL, trade.getCloseTime()),
					trade.getClosePrice(), 
					trade.getTaxes(), 
					trade.getProfit(),
					trade.getComment(), 
					trade.getInternalID(), 
					trade.getMarginRate(), 
					DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
					trade.getModifyTime()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downlines/{agentId}/clients/balance", method = RequestMethod.GET, produces = "application/json;UTF-8")
	@ResponseBody
	public DTResponse downlinesBalance(@RequestParam Integer sEcho,
			@RequestParam Integer iDisplayStart,
			@RequestParam Integer iDisplayLength,
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false) Integer iSortCol_0,
			@RequestParam(required = false) String sSortDir_0,
			@PathVariable Integer agentId,
			@RequestParam String startRangeCloseTime,
			@RequestParam String endRangeCloseTime, Principal principal)
			throws RestAgentClientControllerException, ParseException {
		DTResponse resp = new DTResponse();
		// only the admin and the logged in agent can call this method.
		securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
		Map<String, Date> dates = dateHelper.makeRangeDates(
				startRangeCloseTime, endRangeCloseTime, null, null);
		Date startCloseTime = dates.get("startCloseTime");
		Date endCloseTime = dates.get("endCloseTime");
		resp.setsEcho(sEcho);
		Integer tradesCount = clientTradesService
				.getDownlineClientsBalanceTradesCount(agentId, null, null,
						startCloseTime, endCloseTime, sSearch);

		List<MT4TradeDTO> trades = clientTradesService
				.getDownlineClientsBalanceTrades(agentId, null, null,
						startCloseTime, endCloseTime, iDisplayStart,
						iDisplayLength, sSearch, iSortCol_0, sSortDir_0);

		List<List<?>> aaData = new ArrayList<List<?>>();

		for (MT4TradeDTO trade : trades) {
			aaData.add(Arrays.asList(
					trade.getAgentDTO().getLogin(), 
					trade.getTicket(), 
					trade.getLogin(), 
					trade.getSymbol(), 
					trade.getDigits(), 
					trade.getCmdLabel(), 
					NumberFormatter.parseDouble(NumberFormatter.Type.CURRENCY, trade.getVolume() / 100), DateFormatter.parse(Type.MYSQL, trade.getOpenTime()), 
					trade.getOpenPrice(), 
					trade.getSl(), trade.getTp(),
					DateFormatter.parse(Type.MYSQL, trade.getCloseTime()),
					trade.getClosePrice(), 
					trade.getTaxes(), 
					trade.getProfit(),
					trade.getComment(), 
					trade.getInternalID(), 
					trade.getMarginRate(), 
					DateFormatter.parse(Type._24HOUR, new Date(trade.getTimestamp())), 
					trade.getModifyTime()));
		}

		resp.setiTotalDisplayRecords(tradesCount);
		resp.setiTotalRecords(tradesCount);
		resp.setAaData(aaData);

		return resp;
	}
}
