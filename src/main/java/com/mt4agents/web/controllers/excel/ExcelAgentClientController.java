package com.mt4agents.web.controllers.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mt4agents.dto.MT4TradeDTO;
import com.mt4agents.entities.Agent;
import com.mt4agents.entities.AgentClient;
import com.mt4agents.exceptions.RestAgentControllerException;
import com.mt4agents.formatters.DateFormatter;
import com.mt4agents.helpers.DateHelper;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.ClientTradesService;
import com.mt4agents.services.MT4RemoteService.TradeType;
import com.mt4agents.web.views.excel.MT4TradeExcelView;
import com.mt4agents.web.views.excel.clients.AgentClientsCloseTradesExcelView;
import com.mt4agents.web.views.excel.clients.AgentClientsOpenTradesExcelView;

@Controller
@RequestMapping("/clients")
public class ExcelAgentClientController {

	private static final Logger logger = Logger
			.getLogger(ExcelAgentClientController.class);

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClientService agentClientService;

	@Autowired
	private ClientTradesService clientTradesService;

	@Autowired
	private DateHelper dateHelper;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/trades/{type}", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public ModelAndView getClientsTrades(@PathVariable String type,
			@RequestParam Integer agentClientId,
			@RequestParam(required = false) Integer agentId,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			HttpServletResponse response) throws Exception {

		Map<String, Date> dates = dateHelper.makeRangeDates(startRangeOpenTime,
				endRangeOpenTime, startRangeCloseTime, endRangeCloseTime);
		Date startOpenTime = dates.get("startOpenTime");
		Date endOpenTime = dates.get("endOpenTime");
		Date startCloseTime = dates.get("startCloseTime");
		Date endCloseTime = dates.get("endCloseTime");
		AgentClient agentClient = agentClientService
				.getClientById(agentClientId);
		if (agentClient == null) {
			throw new RestAgentControllerException(messageSource.getMessage(
					"mt4agents.exception.agentclient.invalidid",
					new Object[] { agentClientId }, Locale.US));
		}

		// This allows an upline to calculate the commissions
		// correct by getting the upline's agent commission
		// and apply it to commissions calculations.
		Agent agent;
		if (agentId != null) {
			agent = agentService.getAgentById(agentId);
		} else {
			agent = agentClient.getAgent();
		}

		logger.info("type=" + type);

		TradeType tradeType = null;
		List<MT4TradeDTO> clientTrades;
		Integer clientMt4Login = agentClient.getMt4Login();
		String filename = null;
		MT4TradeExcelView view = null;
		if (type.equals("o")) {
			logger.info("Get Clients Close Trades for agent " + agentId);
			clientTrades = clientTradesService.getClientOpenTrades(
					clientMt4Login, agent.getCommission(), startOpenTime,
					endOpenTime);
			tradeType = TradeType.OPEN;
			filename = formatExcelFilename(startOpenTime, endOpenTime,
					tradeType, clientMt4Login);
			view = new AgentClientsOpenTradesExcelView();
		} else if (type.equals("c")) {
			logger.info("Get Clients Open Trades for agent " + agentId);
			clientTrades = clientTradesService.getClientCloseTrades(
					clientMt4Login, agent.getCommission(), startCloseTime,
					endCloseTime);
			tradeType = TradeType.CLOSE;
			filename = formatExcelFilename(startCloseTime, endCloseTime,
					tradeType, clientMt4Login);
			view = new AgentClientsCloseTradesExcelView();
		} else if (type.equals("b")) {
			logger.info("Get Clients Balance Trades for agent " + agentId);
			clientTrades = clientTradesService.getClientBalanceTrades(agentId,
					startCloseTime, endCloseTime);
			tradeType = TradeType.BALANCE;
			filename = formatExcelFilename(startCloseTime, endCloseTime,
					tradeType, clientMt4Login);
			view = new AgentClientsCloseTradesExcelView();
		} else {
			clientTrades = new ArrayList<MT4TradeDTO>();
			filename = "undefined_trade_type";
		}

		response.setHeader("Content-Disposition", filename);

		ModelAndView mav = new ModelAndView();
		mav.addObject("agent", agent);
		mav.addObject("clientTrades", clientTrades);
		mav.addObject("tradeType", tradeType);
		mav.setView(view);
		return mav;
	}

	private String formatExcelFilename(Date startTime, Date endTime,
			TradeType tradeType, Integer clientMt4Login) {
		return new StringBuilder("attachment; filename=Client-")
				.append(clientMt4Login)
				.append("-")
				.append(tradeType)
				.append("-")
				.append(DateFormatter.parse(
						DateFormatter.Type.YEARFIRSTNODASHDAY, startTime))
				.append("-")
				.append(DateFormatter.parse(
						DateFormatter.Type.YEARFIRSTNODASHDAY, endTime))
				.append(".xlsx").toString();
	}

}
