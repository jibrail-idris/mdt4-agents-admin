package com.mt4agents.web.controllers.json.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.proxies.http.rest.utilities.RestUtils;
import com.base.proxies.response.RestResponse;
import com.mt4agents.dto.AgentClientDTO;
import com.mt4agents.dto.AgentDTO;
import com.mt4agents.dto.MT4CommissionDTO;
import com.mt4agents.dto.MT4TradeDTO;
import com.mt4agents.entities.Agent;
import com.mt4agents.entities.AgentClient;
import com.mt4agents.exceptions.RestAgentControllerException;
import com.mt4agents.helpers.DateHelper;
import com.mt4agents.helpers.SecurityHelper;
import com.mt4agents.services.AgentClientService;
import com.mt4agents.services.AgentService;
import com.mt4agents.services.ClientTradesService;

/**
 * 
 * @author Jibrail Idris
 * 
 */
@Controller
@RequestMapping("/rest/clients")
public class RestAgentClientController {

	private static final Logger logger = Logger
			.getLogger(RestAgentClientController.class);

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClientService agentClientService;

	@Autowired
	private ClientTradesService clientTradesService;

	@Autowired
	private SecurityHelper securityHelper;

	@Autowired
	private DateHelper dateHelper;

	@Autowired
	private MessageSource messageSource;

	@ModelAttribute(value = "response")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<AgentClientDTO>> index(
			@RequestParam Integer agentId,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			Principal principal) {
		RestResponse<List<AgentClientDTO>> resp = new RestResponse<List<AgentClientDTO>>();
		try {

			securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			List<AgentClientDTO> clients = agentClientService
					.getClientsWithCommission(agentId, startOpenTime,
							endOpenTime, startCloseTime, endCloseTime);
			resp.setPayload(clients);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/ids", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<Integer>> getClientsIds() {
		RestResponse<List<Integer>> resp = new RestResponse<List<Integer>>();
		try {
			resp.setPayload(agentClientService.getAllClientsLogins());
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<Integer> getClientsCount(@RequestParam Integer agentId) {
		RestResponse<Integer> resp = new RestResponse<Integer>();
		try {
			resp.setPayload(agentClientService.getClientsIds(agentId).size());
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/managed/ids", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<Integer>> getManagedClientsIds(
			@RequestParam Integer agentId) {
		RestResponse<List<Integer>> resp = new RestResponse<List<Integer>>();
		try {
			resp.setPayload(agentClientService.getClientsLogins(agentId));
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/trades/{type}", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4TradeDTO>> getClientsTrades(
			@PathVariable String type,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			HttpSession session) {
		RestResponse<List<MT4TradeDTO>> resp = new RestResponse<List<MT4TradeDTO>>();
		try {

			AgentDTO agent = (AgentDTO) session.getAttribute("agent");
			Integer agentId = agent.getAgentId();
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			if (type.equals("c")) {
				logger.info("Get Clients Close Trades for agent " + agentId);
				resp.setPayload(clientTradesService.getClientsCloseTrades(
						agentId, startCloseTime, endCloseTime));
			} else if (type.equals("o")) {
				logger.info("Get Clients Open Trades for agent " + agentId);
				resp.setPayload(clientTradesService.getClientsOpenTrades(
						agentId, startOpenTime, endOpenTime));
			} else if (type.equals("b")) {
				logger.info("Get Clients Balance Trades for agent " + agentId);
				resp.setPayload(clientTradesService.getClientBalanceTrades(
						agentId, startCloseTime, endCloseTime));
			}
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/openvolume", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<MT4CommissionDTO> getVolumeFromOpenTrades(
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			HttpSession session) {
		RestResponse<MT4CommissionDTO> resp = new RestResponse<MT4CommissionDTO>();
		try {
			AgentDTO agent = (AgentDTO) session.getAttribute("agent");
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, null, null);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			MT4CommissionDTO volume = clientTradesService
					.getTotalVolumeFromOpenTrades(agent.getAgentId(),
							startOpenTime, endOpenTime);
			resp.setPayload(volume);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/commissions", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<MT4CommissionDTO> getCommissionFromCloseTrades(
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			HttpSession session) {
		RestResponse<MT4CommissionDTO> resp = new RestResponse<MT4CommissionDTO>();
		try {
			AgentDTO agent = (AgentDTO) session.getAttribute("agent");
			Map<String, Date> dates = dateHelper.makeRangeDates(null, null,
					startRangeCloseTime, endRangeCloseTime);
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			MT4CommissionDTO commissionVol = clientTradesService
					.getCommissionsEarnedFromCloseTrades(agent.getAgentId(),
							startCloseTime, endCloseTime);
			resp.setPayload(commissionVol);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/trades/downlines/{agentId}", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4TradeDTO>> getDownlineClientsTrades(
			@PathVariable Integer agentId,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			Principal principal) {
		RestResponse<List<MT4TradeDTO>> resp = new RestResponse<List<MT4TradeDTO>>();
		try {
			// only the admin and the logged in agent can call this method.
			securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			List<MT4TradeDTO> clientsTrades = clientTradesService
					.getDownlineClientsTrades(agentId, startOpenTime,
							endOpenTime, startCloseTime, endCloseTime);
			resp.setPayload(clientsTrades);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/balance/downlines/{agentId}", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4TradeDTO>> getDownlineClientsBalance(
			@PathVariable Integer agentId,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			Principal principal) {
		RestResponse<List<MT4TradeDTO>> resp = new RestResponse<List<MT4TradeDTO>>();
		try {
			// only the admin and the logged in agent can call this method.
			securityHelper.raiseExceptionIfNotAuthorised(agentId, principal);
			Map<String, Date> dates = dateHelper.makeRangeDates(null, null,
					startRangeCloseTime, endRangeCloseTime);
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			List<MT4TradeDTO> clientsTrades = clientTradesService
					.getDownlineClientsBalanceTrades(agentId, null, null,
							startCloseTime, endCloseTime);
			resp.setPayload(clientsTrades);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/commissions/downlines", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4CommissionDTO>> getCommissionsEarnedFromDownlines(
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime,
			HttpSession session) {
		RestResponse<List<MT4CommissionDTO>> resp = new RestResponse<List<MT4CommissionDTO>>();
		try {
			AgentDTO agent = (AgentDTO) session.getAttribute("agent");
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			resp.setPayload(clientTradesService
					.getCommissionsEarnedFromDownline(agent.getAgentId(),
							startOpenTime, endOpenTime, startCloseTime,
							endCloseTime));
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/trades", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4TradeDTO>> getClientTrades(
			@RequestParam Integer agentClientId,
			@RequestParam(required = false) Integer agentId,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime) {
		RestResponse<List<MT4TradeDTO>> resp = new RestResponse<List<MT4TradeDTO>>();
		try {
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startOpenTime = dates.get("startOpenTime");
			Date endOpenTime = dates.get("endOpenTime");
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			AgentClient agentClient = agentClientService
					.getClientById(agentClientId);
			if (agentClient == null) {
				throw new RestAgentControllerException(
						messageSource.getMessage(
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

			List<MT4TradeDTO> clientTrades;
			if (startOpenTime != null && endOpenTime != null) {
				clientTrades = clientTradesService.getClientOpenTrades(
						agentClient.getMt4Login(), agent.getCommission(),
						startOpenTime, endOpenTime);
			} else if (startCloseTime != null && endCloseTime != null) {
				clientTrades = clientTradesService.getClientCloseTrades(
						agentClient.getMt4Login(), agent.getCommission(),
						startCloseTime, endCloseTime);
			} else {
				clientTrades = new ArrayList<MT4TradeDTO>();
			}

			resp.setPayload(clientTrades);
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}

	@ModelAttribute(value = "response")
	@RequestMapping(value = "/balance", method = RequestMethod.GET, produces = "application/json;UTF-8")
	public RestResponse<List<MT4TradeDTO>> getBalanceTrades(
			@RequestParam Integer agentClientId,
			@RequestParam(required = false) Integer agentId,
			@RequestParam(required = false) String startRangeOpenTime,
			@RequestParam(required = false) String endRangeOpenTime,
			@RequestParam(required = false) String startRangeCloseTime,
			@RequestParam(required = false) String endRangeCloseTime) {
		RestResponse<List<MT4TradeDTO>> resp = new RestResponse<List<MT4TradeDTO>>();
		try {
			Map<String, Date> dates = dateHelper.makeRangeDates(
					startRangeOpenTime, endRangeOpenTime, startRangeCloseTime,
					endRangeCloseTime);
			Date startCloseTime = dates.get("startCloseTime");
			Date endCloseTime = dates.get("endCloseTime");
			AgentClient agentClient = agentClientService
					.getClientById(agentClientId);
			if (agentClient == null) {
				throw new RestAgentControllerException(
						messageSource.getMessage(
								"mt4agents.exception.agentclient.invalidid",
								new Object[] { agentClientId }, Locale.US));
			}
			resp.setPayload(clientTradesService.getClientBalanceTrades(
					agentClient.getMt4Login(), startCloseTime, endCloseTime));
			resp.setStatus(RestResponse.Status.OK);
		} catch (Exception ex) {
			RestUtils.handleException(resp, ex);
		}
		return resp;
	}
}
