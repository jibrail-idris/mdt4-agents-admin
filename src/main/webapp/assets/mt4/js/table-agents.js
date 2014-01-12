$(document).ready(function() {
	
	App.blockUI(jQuery("#tables-container"));
	oAgentsTable = $('#table-agents').dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"aoColumns" : [
		               { "bSortable": false }, //LOGIN
		               { "bSortable": false }, //UPLINE
		               { "bSortable": false }, //GROUP
		               { "bSortable": false, "bVisible": false }, //ENABLE
		               { "bSortable": false, "bVisible": false }, //ENABLE_CHANGE_PASS
		               { "bSortable": false, "bVisible": false }, //ENABLE_READONLY
		               { "bSortable": false, "bVisible": false }, //PASSWORD_PHONE
		               { "bSortable": false }, //NAME
		               { "bSortable": false }, //COUNTRY
		               { "bSortable": false }, //CITY
		               { "bSortable": false }, //STATE
		               { "bSortable": false, "bVisible": false }, //ZIPCODE
		               { "bSortable": false, "bVisible": false }, //ADDRESS
		               { "bSortable": false }, //PHONE
		               { "bSortable": false }, //EMAIL
		               { "bSortable": false, "bVisible": false }, //COMMENT
		               { "bSortable": false, "bVisible": false }, //REGDATE
		               { "bSortable": false, "bVisible": false }, //LASTDATE
		               { "bSortable": false, "bVisible": false }, //LEVERAGE
		               { "bSortable": false }, //CLIENTS
		               { "bSortable": false }, //COMM
		               { "bSortable": false }
		               ],
		"sAjaxSource": "/json/dt/agents.json",
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-agents"));
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {fnCallback(data); App.unblockUI($("#table-agents"));},
                "cache": true
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			var last_column = $("td", nRow).length - 1;
			var last_cell = $("td:eq(" + last_column + ")", nRow);			
			var comm = $("td:eq(" + (last_column - 1) + ")", nRow);			
			var st = aData[aData.length - 1];
			
			var dropdown = $(".agent-clients-dropdown").clone();
			dropdown.removeClass("hide");
			dropdown.removeClass("agent-clients-dropdown");
			var view_clients = $(".view-clients", dropdown);
			var manage_clients_button = $(".manage-clients-btn", dropdown);
			var edit_button = $(".edit-agent-btn", dropdown);
			var reset_password_button = $(".reset-password-btn", dropdown);
			var delete_button = $(".delete-agent-btn", dropdown);
			
			view_clients.data("rowindex", iDisplayIndex);			
			view_clients.data("rowslength", $("td", nRow).length);
			view_clients.data("agentid", st.agent.agentId);
			view_clients.data("name", st.agent.label);			
			
			$(view_clients).click(function() {
				selectedAgentId = $(this).data("agentid");
				selectedAgentName = $(this).data("name");
				selectedAgentLogin = $(this).data("login");
				selectedAgentRow = $(this).data("rowindex");

				get_clients();
			});
			
			comm.formatCurrency();
			
			manage_clients_button.data("rowindex", iDisplayIndex);
			manage_clients_button.data("agentid", st.agent.agentId);
			manage_clients_button.data("login", st.agent.login);
			manage_clients_button.data("clients", st.agent.clients);
			
			edit_button.data("nRow", nRow);
			edit_button.data("agentid", st.agent.agentId);
			edit_button.data("login", st.agent.login);
			edit_button.data("commission", st.agent.commission);
			
			delete_button.data("agentid", st.agent.agentId);
			delete_button.data("login", st.agent.login);
			
			reset_password_button.data("agentid", st.agent.agentId);
			reset_password_button.data("login", st.agent.login);
			
			manage_clients_button.click(manage_clients_click);
			edit_button.click(edit_click);
			reset_password_button.click(reset_password_click);
			delete_button.click(function() {
				var agentId = $(this).data("agentid");
				selectedAgentDelete = iDisplayIndexFull;
				var form = $("#mt-agent-unassign").clone();
				$(".btn.save", form).click(function() {
					$.post("/json/rest/agents/delete.json", 
							{agentId: agentId}, 
							function(data) {
								handle_json_response(data, function() {
									$(form).modal("hide");
									delete_agent_success();
								}, form);
							});
				});
				$(form).modal();
			});
			
			last_cell.empty();
			last_cell.append(dropdown);
		}
	});
	
	$('#table-agents').removeClass("hide");
	App.unblockUI(jQuery("#tables-container"));
	
	oClientsTable = $("#table-clients").dataTable({
		"bProcessing": true,
		"iDisplayLength" : 10,
		"aoColumns" : [
		               null,
		               null,
		               {"bVisible": false},
		               {"bVisible": false}, 
		               {"bVisible": false},
		               {"bVisible": false}, 
		               {"bVisible": false},
		               {"bVisible": false},
		               null,
		               {"bVisible": false},
		               null,
		               null, 
		               null
		               ],
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			var client_login = $("td:eq(0)", nRow);
			var client_login_link = $("<a></a>");
			$(client_login_link).attr("href", "javascript:;");
			var client_data = client_login.text();
			$(client_login_link).text(client_data);
			$(client_login_link).data("agentClientId", client_data[1]);
			$(client_login_link).click(function() {
				selectedClient = agentClientsIdsMapping[$(this).text()];
				selectedClientLogin = $(this).text();
				$("#clients-open-trades-date-range span")
					.html(startDate.toString(dateFormatView) + ' - ' + endDate.toString(dateFormatView));
				$("#clients-close-trades-date-range span")
					.html(startDate.toString(dateFormatView) + ' - ' + endDate.toString(dateFormatView));
				get_open_trades();
				get_close_trades();
				get_balance_trades();
			});
			$("td:eq(0)", nRow).empty();
			$("td:eq(0)", nRow).html(client_login_link);
		}
	});
	
	oOpenTradesClientsTradesColumns = [
	                                 	["TICKET", true],
	                               		["LOGIN", true],
	                               		["SYMBOL", true],
	                               		["DIGITS", false],
	                               		["CMD", false],
	                               		["VOLUME", false],
	                               		["OPEN_TIME", true],
	                               		["OPEN_PRICE", true],
	                               		["SL", false],
	                               		["TP", false],
	                               		["TAXES", false],
	                               		["PROFIT", false],
	                               		["COMMENT", false],
	                               		["INTERNAL_ID", false],
	                               		["MARGIN_RATE", false],
	                               		["TIMESTAMP", false],
	                               		["MODIFY_TIME", false]
	                                 		];
	
	var oOpenTradesClientsTradesAOColumns = [];
  	for (var i in oOpenTradesClientsTradesColumns) {
  		var column = oOpenTradesClientsTradesColumns[i];
  		oOpenTradesClientsTradesAOColumns.push({"bVisible" : column[1]});
  		
  		var tr = $("<tr></tr>");
  		var td = $("<td></td>");
  		var cb = $("<input type='checkbox' />");
  		cb.addClass("column-toggle");
  		if ( column[1] ){
  			cb.prop("checked", true);
  		}
  		td.append(cb);
  		td.append("&nbsp;" + column[0]);
  		$(cb).uniform();
  		tr.append(td);
  		
  		$("#clients-open-trades-table-config .modal-body table tbody").append(tr);
  	}
  	
	oOpenTradesTable = $("#table-open-trades").dataTable({
		"bProcessing": true,
		"iDisplayLength" : 10,
		"aoColumns" : oOpenTradesClientsTradesAOColumns,
	});
	
	
	var oCloseTradesClientsTradesColumns = [
  	                                		["TICKET", true],
	  	                              		["LOGIN", true],
	  	                              		["SYMBOL", true],
	  	                              		["DIGITS", false],
	  	                              		["CMD", false],
	  	                              		["VOLUME", false],
	  	                              		["OPEN_TIME", true],
	  	                              		["OPEN_PRICE", true],
	  	                              		["SL", false],
	  	                              		["TP", false],
	  	                              		["CLOSE_TIME", true],
	  	                              		["CLOSE_PRICE", true],
	  	                              		["TAXES", false],
	  	                              		["PROFIT", false],
	  	                              		["COMMENT", false],
	  	                              		["INTERNAL_ID", false],
	  	                              		["MARGIN_RATE", false],
	  	                              		["TIMESTAMP", false],
	  	                              		["MODIFY_TIME", false]
  	                                		];
  	
  	var oCloseTradesClientsTradesAOColumns = [];
  	for (var i in oCloseTradesClientsTradesColumns) {
  		var column = oCloseTradesClientsTradesColumns[i];
  		oCloseTradesClientsTradesAOColumns.push({"bVisible" : column[1]});
  		
  		var tr = $("<tr></tr>");
  		var td = $("<td></td>");
  		var cb = $("<input type='checkbox' />");
  		cb.addClass("column-toggle");
  		if ( column[1] ){
  			cb.prop("checked", true);
  		}
  		td.append(cb);
  		td.append("&nbsp;" + column[0]);
  		$(cb).uniform();
  		tr.append(td);
  		
  		$("#clients-close-trades-table-config .modal-body table tbody").append(tr);
  	}
	
	
	oCloseTradesTable = $("#table-close-trades").dataTable({
		"bProcessing": true,
		"iDisplayLength" : 10,
		"aoColumns" : oCloseTradesClientsTradesAOColumns,
	});
	
	
	for (var i in oCloseTradesClientsTradesColumns) {
  		var column = oCloseTradesClientsTradesColumns[i];
  		
  		var tr = $("<tr></tr>");
  		var td = $("<td></td>");
  		var cb = $("<input type='checkbox' />");
  		cb.addClass("column-toggle");
  		if ( column[1] ){
  			cb.prop("checked", true);
  		}
  		td.append(cb);
  		td.append("&nbsp;" + column[0]);
  		$(cb).uniform();
  		tr.append(td);
  		
  		$("#clients-balance-table-config .modal-body table tbody").append(tr);
  	}
	oBalanceTable = $("#table-balance").dataTable({
		"bProcessing": true,
		"iDisplayLength" : 10,
		"aoColumns" : oCloseTradesClientsTradesAOColumns
	});
	
	init_columns_toggle();
	
	register_date_change_listener("clients-date-range", client_date_range_change);
	register_date_change_listener("clients-open-trades-date-range", open_trades_date_range);
	register_date_change_listener("clients-close-trades-date-range", close_trades_date_range);
	register_date_change_listener("clients-balance-date-range", balance_date_range);
	
	jQuery('#table_agents_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
	jQuery('#table_agents_wrapper .dataTables_length select').addClass("m-wrap xsmall"); // modify table per page dropdown
	
	$("#clients-open-excel-export").click(get_open_trades_excel);
	$("#clients-close-excel-export").click(get_close_trades_excel);
	$("#clients-balance-excel-export").click(get_balance_trades_excel);
});
	
var selectedAgentId;
var selectedAgentName;
var selectedAgentLogin;
var selectedAgentRow;
var selectedClient;
var selectedClientLogin;
var selectedAgentDelete;

var openStartDate = startDate;
var openEndDate = endDate;
var closeStartDate = startDate;
var closeEndDate = endDate;
var balanceStartDate = startDate;
var balanceEndDate = endDate;

var closeMode = true;
var openMode = false;

var agentClientsIdsMapping = {};

function init_columns_toggle() {
	var i = 0;
	$(".column-toggle", $("#table-agents-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oAgentsTable);
		$(this).click(table_column_toggle);
	});
	i = 0;
	$(".column-toggle", $("#clients-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oClientsTable);
		$(this).click(table_column_toggle);
	});
	i = 0;
	$(".column-toggle", $("#clients-close-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oCloseTradesTable);
		$(this).click(table_column_toggle);
	});
	i = 0;
	$(".column-toggle", $("#clients-open-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oOpenTradesTable);
		$(this).click(table_column_toggle);
	});
	i = 0;
	$(".column-toggle", $("#clients-balance-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oBalanceTable);
		$(this).click(table_column_toggle);
	});
}

function table_column_toggle() {
	var index = $(this).data("index");
	var is_checked = $(this).is(":checked");
	var datatable = $(this).data("datatable");
	datatable.fnSetColumnVis(index, is_checked);
}

function delete_agent_success() {
	//oAgentsTable.fnDeleteRow(selectedAgentDelete);
	document.location = "";
}

function get_clients() {
	App.blockUI(jQuery("#tables-container"));
	$.get("/json/rest/clients.json?ts=" + new Date().getTime(), 
	{
		agentId: selectedAgentId,
		startRangeCloseTime: startDate.toString(dateFormatJSON),
		endRangeCloseTime: endDate.toString(dateFormatJSON)
	}, 
	function(data) {
		handle_json_response(data, show_clients_table, null);
		App.unblockUI(jQuery("#tables-container"));
		$.gritter.add({
	        title: 'Trades',
	        text: 'Clients view updated.'
	    });
	});
}

function get_open_trades() {
	App.blockUI(jQuery("#tables-container"));
	$.get("/json/rest/clients/trades.json?ts=" + new Date().getTime(), 
			{
				agentClientId: selectedClient,
				agentId: selectedAgentId,
				startRangeOpenTime: openStartDate.toString(dateFormatJSON),
				endRangeOpenTime: openEndDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, show_open_trades_table, null);
				App.unblockUI(jQuery("#tables-container"));
				$.gritter.add({
			        title: 'Trades',
			        text: 'Open Trades view updated.'
			    });

			});
}

function get_close_trades() {
	App.blockUI(jQuery("#tables-container"));
	$.get("/json/rest/clients/trades.json?ts=" + new Date().getTime(), 
			{
				agentClientId: selectedClient,
				agentId: selectedAgentId,
				startRangeCloseTime: closeStartDate.toString(dateFormatJSON),
				endRangeCloseTime: closeEndDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, show_closed_trades_table, null);
				App.unblockUI(jQuery("#tables-container"));
				$.gritter.add({
			        title: 'Trades',
			        text: 'Close Trades view updated.'
			    });
			});
}

function get_balance_trades() {
	App.blockUI(jQuery("#tables-container"));
	$.get("/json/rest/clients/balance.json?ts=" + new Date().getTime(), 
			{
				agentClientId: selectedClient,
				agentId: selectedAgentId,
				startRangeCloseTime: balanceStartDate.toString(dateFormatJSON),
				endRangeCloseTime: balanceEndDate.toString(dateFormatJSON)
			}, 
			function(data) {
				console.log(data);
				handle_json_response(data, show_balance_trades_table, null);
				App.unblockUI(jQuery("#tables-container"));
				$.gritter.add({
			        title: 'Trades',
			        text: 'Balance view updated.'
			    });
			});
}

function get_open_trades_excel() {
	document.location = "/excel/clients/trades/o.xslx?ts=" + new Date().getTime() + 
		"&agentClientId=" + selectedClient + "&agentId=" + selectedAgentId + 
		"&startRangeOpenTime=" + openStartDate.toString(dateFormatJSON) + "&endRangeOpenTime=" + openEndDate.toString(dateFormatJSON);
}

function get_close_trades_excel() {
	document.location = "/excel/clients/trades/c.xslx?ts=" + new Date().getTime() + 
		"&agentClientId=" + selectedClient + "&agentId=" + selectedAgentId + 
		"&startRangeCloseTime=" + closeStartDate.toString(dateFormatJSON) + "&endRangeCloseTime=" + closeEndDate.toString(dateFormatJSON);
}

function get_balance_trades_excel() {
	document.location = "/excel/clients/trades/b.xslx?ts=" + new Date().getTime() + 
		"&agentClientId=" + selectedClient + "&agentId=" + selectedAgentId + 
		"&startRangeCloseTime=" + closeStartDate.toString(dateFormatJSON) + "&endRangeCloseTime=" + closeEndDate.toString(dateFormatJSON);
}

function show_agents_table(agents) {
	for (var i in agents) {
		var agent  = agents[i];
		
		var meta = {
			"login" : agent.login,
			"clients" : agent.clients,
			"agentId" : agent.agentId
		}
		
		oAgentsTable.fnAddData([
		                        agent.login,
		                        agent.parentAgentLabel,
		                        agent.group,
		                        agent.name,
		                        agent.country,
		                        agent.city,
		                        agent.state,
		                        agent.zipcode,
		                        agent.address,
		                        agent.phone,
		                        agent.email,
		                        agent.comment,
		                        agent.regDate,
		                        agent.lastDate,
		                        0,
		                        0,
		                        meta
		                        ]);

	}
	$.gritter.add({
        title: 'Agents',
        text: 'Agents updated.'
    });
}

function show_clients_table(clients) {
	
	agentClientsIdsMapping = {};	
	
	oClientsTable.fnClearTable();
	
	var grandTotalVolume = 0.00;
	var grandTotalCommission = 0.00;
	
	for (var c in clients) {
		var client = clients[c];
		var commissionDTO = client.commissionDTO;
		var totalVolume = 0.00;
		var totalCommission = 0.00;
		if (commissionDTO != null) {
			totalVolume = commissionDTO.totalVolume;
			totalCommission = commissionDTO.commission;
		}
		grandTotalVolume += totalVolume;
		grandTotalCommission += totalCommission;
		totalVolume = (totalVolume / 100).toFixed(2);
		totalCommission = totalCommission.toFixed(2);
		agentClientsIdsMapping[client.login] = client.agentClientId;
		oClientsTable.fnAddData([
		                         client.login,
		                         client.name,
		                         client.country,
		                         client.city,
		                         client.state,
		                         client.zipcode,
		                         client.address,
		                         client.phone,
		                         client.email,
		                         client.comment,
		                         client.registrationDate,
		                         totalVolume,
		                         totalCommission
		                         ]);
	}
	grandTotalVolume = (grandTotalVolume / 100).toFixed(2);
	grandTotalCommission = grandTotalCommission.toFixed(2);
	$(".grand-figures #total-vol").text(grandTotalVolume);
	$(".grand-figures #total-comm").text(grandTotalCommission);
	
	$(".grand-figures #total-comm").formatCurrency();
	
	$("#table-view-clients").removeClass("hide");
	
	$("#table-view-clients h4").text("Clients - " + selectedAgentName);
}
	
function show_open_trades_table(trades) {
	$("#table-view-trades").removeClass("hide");
	
	oOpenTradesTable.fnClearTable();
	
	for (var t in trades) {
		var trade = trades[t];
		var volume = trade.volume;
		oOpenTradesTable.fnAddData([
				        		 	trade.ticket,
				        		 	/*trade.agentClientDTO.login,*/
				        		 	null,
				        		 	trade.symbol,
				        		 	trade.digits,
				        		 	trade.cmdLabel,
				        		 	(volume / 100).toFixed(2),
				        		 	new Date(trade.openTime).toString(fullDateFormatJSON),
				        		 	trade.openPrice,
				        		 	trade.sl,
				        		 	trade.tp,
				        			trade.taxes,
				        			trade.profit,
				        			trade.comment,
				        			trade.internalID,
				        			trade.marginRate,
				        			new Date(trade.timestamp).toString(fullDateFormatJSON),
				        			new Date(trade.modifyTime).toString(fullDateFormatJSON)
				        			]);
	}
	
	$("#table-view-trades h4").text("Trades - " + selectedClientLogin);
}

function show_closed_trades_table(trades) {
	$("#table-view-trades").removeClass("hide");
	
	oCloseTradesTable.fnClearTable();
	
	for (var t in trades) {
		var trade = trades[t];
		var volume = trade.volume;
		oCloseTradesTable.fnAddData([
		        		 	trade.ticket,
		        		 	trade.login,
		        		 	trade.symbol,
		        		 	trade.digits,
		        		 	trade.cmdLabel,
		        		 	(volume / 100).toFixed(2),
		        		 	new Date(trade.openTime).toString(fullDateFormatJSON),
		        		 	trade.openPrice,
		        		 	trade.sl,
		        		 	trade.tp,
		        			new Date(trade.closeTime).toString(fullDateFormatJSON),
		        			trade.closePrice,
		        			trade.taxes,
		        			trade.profit,
		        			trade.comment,
		        			trade.internalID,
		        			trade.marginRate,
		        			new Date(trade.timestamp).toString(fullDateFormatJSON),
		        			new Date(trade.modifyTime).toString(fullDateFormatJSON) ]);
	}
	
	$("#table-view-trades h4").text("Trades - " + selectedClientLogin);
}

function show_balance_trades_table(trades) {
	$("#table-view-trades").removeClass("hide");
	
	oBalanceTable.fnClearTable();
	
	for (var t in trades) {
		var trade = trades[t];
		var volume = trade.volume;
		oBalanceTable.fnAddData([
		        		 	trade.ticket,
		        		 	trade.login,
		        		 	trade.symbol,
		        		 	trade.digits,
		        		 	trade.cmdLabel,
		        		 	(volume / 100).toFixed(2),
		        		 	new Date(trade.openTime).toString(fullDateFormatJSON),
		        		 	trade.openPrice,
		        		 	trade.sl,
		        		 	trade.tp,
		        			new Date(trade.closeTime).toString(fullDateFormatJSON),
		        			trade.closePrice,
		        			trade.taxes,
		        			trade.profit,
		        			trade.comment,
		        			trade.internalID,
		        			trade.marginRate,
		        			new Date(trade.timestamp).toString(fullDateFormatJSON),
		        			new Date(trade.modifyTime).toString(fullDateFormatJSON) ]);
	}
	
	$("#table-view-trades h4").text("Trades - " + selectedClientLogin);
}


var managed_users;
var managed_agents;
var managed_clientids;
var managed_agent_clientids;

function manage_clients_click(e) {
	var agentId = $(this).data("agentid");
	// 1. get all users
	// 2. get all agents
	// 3. get all clients
	// 4. get agent's clients
	
	//App.blockUI(jQuery("#tables-container"));
	
	selectedAgentRow = $(this).data("rowindex");
	$.gritter.add({
        title: 'Users',
        text: 'Loading clients data.'
    });
	$.get("/json/rest/agents.json", function(data) {
		handle_json_response(data, 
		function(agents) {			
			managed_agents = agents;			
			update_managed_data(agentId, function() {
				show_manage_clients_window(agentId);
				$.gritter.add({
			        title: 'Manage Clients',
			        text: 'Clients data loaded.'
			    });
			});
		});
	});
}

function show_manage_clients_window(agentId) {
	manage_client_form = $("#portlet-manage-clients-form").clone();
	var managed_clients_table = $("#managed-clients", manage_client_form);
	var btn_save = $(".btn.save", manage_client_form);
	var btn_cancel = $(".btn.cancel", manage_client_form);
	oManagedClientsTable = $(managed_clients_table).dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"sAjaxSource": "/json/dt/users.json?ts=" + new Date().getTime(),
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			var cells = $("td", nRow);
			var login_cell = $(cells[0]);
			var badge_cell = $(cells[cells.length - 1]);
			var user_login = aData[0];
			// for each user, i need to determine if the user is an agent, a client or a client managed under the selected agent.
			// how do I know if the user is an agent?
			var is_agent = is_user_agent(user_login);
			// if user isn't agent, is he a client?
			var is_client = false;
			if (!is_agent) {
				is_client = is_user_client(user_login);
			}
			//if user is a client, is he a client assigned to this agent?
			var is_agent_client = false;
			if (is_client) {
				is_agent_client = is_user_client_managed_by_agent(user_login, agentId);
			}
			
			var decisions_params = {
					agentId:agentId, user_login: user_login, is_agent: is_agent, is_client:is_client, is_agent_client:is_agent_client	
			};

			var badge = decide_on_badge(decisions_params);
			badge_cell.empty();
			badge_cell.append(badge);
			
			login_cell.empty();
			login_cell.append(aData[0] + " - " + aData[6]);
		}
	});
	
//	if (managed_users !== undefined) {
//		for (var i = 0; i < managed_users.length; i++) {
//			var user = managed_users[i];
//			oManagedClientsTable.fnAddData([user.login + " - " + user.name, user.login]);
//		}
//	}
	
	$(manage_client_form).modal();
}

function is_user_agent(user) {
	for (var a = 0; a < managed_agents.length; a++) {
		var agent = managed_agents[a];
		if (agent.login == user) {
			return true;
		}
	}
	return false;
}

function is_user_client(user) {
	for (var c = 0; c < managed_clientids.length; c++) {
		var client = managed_clientids[c];
		if (client == user) {
			return true;
		}
	}
	return false;
}

function is_user_client_managed_by_agent(user) {
	for (var m = 0; m < managed_agent_clientids.length; m++) {
		var managed_client = managed_agent_clientids[m];
		if (managed_client == user) {
			return true;
		}
	}
	return false;
}

function decide_on_badge(params) {
	var agentId = params.agentId;
	var user_login = params.user_login;
	var is_agent = params.is_agent;
	var is_client = params.is_client;
	var is_agent_client = params.is_agent_client;
	
	// decisions:
	// if agent, show agent badge.
	// if client and doesnt belong to agent, show client badge.
	// if client and belongs to agent, show unassigned button.
	// if not agent and not client, show assign button.
	
	var assign_button = $("<button class='mini btn blue'>Assign As Client</button>");
	assign_button.data("agentid", agentId);
	assign_button.data("clientlogin", user_login);
	assign_button.click(assign_as_client_click);
	
	var unassign_button = $("<button class='mini btn red'>Unassign As Client</button>");
	unassign_button.data("agentid", agentId);
	unassign_button.data("clientlogin", user_login);
	unassign_button.click(unassign_as_client_click);
	
	assign_button.data("unassignbutton", unassign_button);
	unassign_button.data("assignbutton", assign_button);
	
	if (is_agent) {
		return $("<span class='label label-success'>Agent</span>");
	} else {
		if (is_client && !is_agent_client) {
			return $("<span class='label label-success'>Client</span>")
		} else if(is_client && is_agent_client) {
			return unassign_button;
		} else {
			return assign_button;
		}
	}
}

function edit_click(e) {
	var nRow = $(this).data("nRow");
	var agentId = $(this).data("agentid");
	var commission = $(this).data("commission");
	var form = $("#portlet-update-agent-form").clone().modal();
	var commission_input = $("input[name=commission]", form);
//	var upline = $("select[name=upline]", form);
	var save_btn = $(".btn.save", form);
	var cancel_btn = $(".btn.cancel", form);
//	$(upline).uniform();
	$(commission_input).val(commission);
//	$.get("/json/rest/agents.json", function(data) {
//		$(upline).empty();
//		handle_json_response(data, function(agents) {
//			for(var a in agents) {
//				var agent = agents[a];
//				$(upline).append("<option value='" + agent.agentId + "'>" + agent.label + "</option>");
//			}
//			$.uniform.update(upline);
//		}, null);
//	});
	save_btn.data("nRow", nRow);
	save_btn.data("agentid", agentId);
	save_btn.data("form", form);
	save_btn.data("commission_input", commission_input);
	save_btn.click(agent_update_click);
}

function agent_update_click() {
	var nRow = $(this).data("nRow");
	var agentId = $(this).data("agentid");
	var commission_input = $(this).data("commission_input");
	var form = $(this).data("form");
	var last_column = $("td", nRow).length - 1;
	var comm = $("td:eq(" + (last_column - 1) + ")", nRow);
	if (commission_input.val().match('^[0-9]*\.[0-9]*$')) {
		$("div.alert-error", form).addClass("hide");
		$.gritter.add({
	        title: 'Commission',
	        text: 'Updating commission.'
	    });
		$.post("/json/rest/agents/update.json", {
			agentId: agentId,
			commission: commission_input.val()
		}, function(data) {
			handle_json_response(data, function() {
				comm.text(commission_input.val());
				comm.formatCurrency();
				$.gritter.add({
			        title: 'Commission',
			        text: 'Commission updated.'
			    });
			});
		});
	} else {
		$("div.alert-error", form).text("Commission is not a number.");
		$("div.alert-error", form).removeClass("hide");
	}
}

function reset_password_click() {
	var agentId = $(this).data("agentid");
	var form = $("#portlet-reset-password-form").clone();
	form = $(form).modal();
	var password_input = $("input[name=password]", form);
	var generate_random_password_button = $("button.generate-random-password", form);
	var reset_password_button = $("button.reset-password", form);
	
	generate_random_password_button.click(function() {
		password_input.val(generatepass(8));
	});
	
	reset_password_button.click(function() {
		$.gritter.add({
	        title: 'Password Reset',
	        text: 'Resetting password.'
	    });
		$.post("/json/rest/users/password/reset.json", {
			agentId: agentId,
			newPassword: password_input.val()
		}, function(data) {
			handle_json_response(data, function() {
				$.gritter.add({
			        title: 'Password Reset',
			        text: 'Password reset successful.'
			    });
			});
		});
	});
}

function assign_as_client_click() {
	App.blockUI($("#managed-clients", manage_client_form));
	
	var button = $(this);
	var agentId = button.data("agentid");
	var clientlogin = button.data("clientlogin");
	var unassign_button = button.data("unassignbutton");
	var post_params = {
		agentId: agentId,
		login: clientlogin
	};
	$.post("/json/rest/agents/clients/add.json", post_params, 
			function(data) {
				handle_json_response(data, function() {
					button.addClass("hide");
					unassign_button.removeClass("hide");
					button.replaceWith(unassign_button);
					update_clients_count(agentId);
					update_managed_data(agentId);
					App.unblockUI($("#managed-clients", manage_client_form));
				}, null);
			});
	
}

function unassign_as_client_click() {
	App.blockUI($("#managed-clients", manage_client_form));
	
	var button = $(this);
	var agentId = button.data("agentid");
	var clientlogin = button.data("clientlogin");
	var assign_button = button.data("assignbutton");
	var post_params = {
			agentClientLogin: clientlogin
		};
		$.post("/json/rest/agents/clients/delete.json", post_params, 
				function(data) {
					handle_json_response(data, function() {
						button.addClass("hide");
						assign_button.removeClass("hide");
						button.replaceWith(assign_button);
						update_clients_count(agentId);
						update_managed_data(agentId);
						App.unblockUI($("#managed-clients", manage_client_form));
					}, null);
				});
}

function update_clients_count(agentId) {
	
	$.get("/json/rest/clients/count.json?ts=" + new Date().getTime(), 
			{agentId: agentId}, 
			function(data) {
				handle_json_response(data, update_count);
			});
	
}

function update_count(count) {
	setTimeout(function() {
		console.log(count);
		console.log(selectedAgentRow);
		//oAgentsTable.fnUpdate(count, selectedAgentRow, 19, false, false);
	}, 1000);
}

function update_managed_data(agentId, callback) {
	$.get("/json/rest/clients/ids.json?ts=" + new Date().getTime(), function(data) {
		handle_json_response(data,
		function(clientids) {
			
			managed_clientids = clientids;
			
			$.get("/json/rest/clients/managed/ids.json", {agentId: agentId}, function(data) {
				handle_json_response(data, 
				function(agentclientids) {					
					managed_agent_clientids = agentclientids;
					if (callback) {
						callback();
					}
					//show_manage_clients_window(agentId);
					//App.unblockUI($("tables-container"));
				}, null);
			});
		}, null);
	});	
}

function client_date_range_change(start, end) {
	startDate = start;
	endDate = end;
	openStartDate = startDate;
	openEndDate = endDate;
	closeStartDate = startDate;
	closeEndDate = endDate;
	balanceStartDate = startDate;
	balanceEndDate = endDate;
	get_clients();
	if (selectedClientLogin !== undefined) {
		get_open_trades();
		get_close_trades();
		get_balance_trades();
		update_date_label("clients-open-trades-date-range", openStartDate, openEndDate);
		update_date_label("clients-close-trades-date-range", closeStartDate, balanceStartDate);
		update_date_label("clients-balance-date-range", balanceStartDate, balanceEndDate);
	}
}

function open_trades_date_range(start, end) {
	openStartDate = start;
	openEndDate = end;
	get_open_trades();
}

function close_trades_date_range(start, end) {
	closeStartDate = start;
	closeEndDate = end;
	get_close_trades();
}

function balance_date_range(start, end) {
	balanceStartDate = start;
	balanceEndDate = end;
	get_balance_trades();
}