package com.mt4agents.web.controllers.forms;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mt4agents.dto.MT4UserDTO;
import com.mt4agents.services.MT4RemoteService;
import com.mt4agents.services.MT4RemoteService.TradeType;
import com.mt4agents.util.DataGenerator;
import com.mt4agents.web.propertyeditors.TradeTypePropertyEditor;

@Controller
@RequestMapping("/datagenerator")
public class DataGeneratorForm {

	@Autowired
	private MT4RemoteService mt4RemoteService;

	@Autowired
	private DataGenerator dataGenerator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(TradeType.class,
				new TradeTypePropertyEditor());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		List<MT4UserDTO> users = mt4RemoteService.getUsers(0, 50, "", null,
				null);
		model.addAttribute("users", users);
		return "datagenerator";
	}

	@RequestMapping(value = "/create/login", method = RequestMethod.POST)
	public String createLogin(@RequestParam(required = false) Integer login,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String country, Model model) {

		if (login == null && !StringUtils.hasLength(name)
				&& !StringUtils.hasLength(country)) {
			dataGenerator.createRandomMT4User();
		} else if (login != null && StringUtils.hasLength(country)) {
			dataGenerator.createRandomMT4User(login, country);
		} else if (login != null) {
			dataGenerator.createRandomMT4User(login);
		}

		return "redirect:/datagenerator";
	}

	@RequestMapping(value = "/create/login/bulk", method = RequestMethod.POST)
	public String createLoginBulk(@RequestParam Integer count) {
		dataGenerator.createRandomMT4Users(count);
		return "redirect:/datagenerator";
	}

	@RequestMapping(value = "/create/trades", method = RequestMethod.POST)
	public String createTrades(@RequestParam Integer login,
			@RequestParam Integer cmd, @RequestParam TradeType tradeType,
			@RequestParam Integer noOfTrades, @RequestParam String baseDate)
			throws ParseException {
		dataGenerator.createRandomMT4Trades(login, cmd, tradeType, noOfTrades,
				baseDate);
		return "redirect:/datagenerator";
	}

	@RequestMapping(value = "/create/tree", method = RequestMethod.POST)
	public String createAgentTree(@RequestParam Integer depth,
			@RequestParam Integer childrenCountPerNode,
			@RequestParam Integer noOfClients,
			@RequestParam Integer noOfTradesPerClient) throws Exception {
		dataGenerator.constructAgentsTree(depth, childrenCountPerNode,
				noOfClients, noOfTradesPerClient);
		return "redirect:/datagenerator";
	}
}
