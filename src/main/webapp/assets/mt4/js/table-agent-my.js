var openTradesStartDate = startDate;
var openTradesEndDate = endDate;
var closeTradesStartDate = startDate;
var closeTradesEndDate = endDate;
var balanceTradesStartDate = startDate;
var balanceTradesEndDate = endDate;
var downlinesOpenTradesStartDate = startDate;
var downlinesOpenTradesEndDate = endDate;
var downlinesCloseTradesStartDate = startDate;
var downlinesCloseTradesEndDate = endDate;
var downlinesBalanceTradesStartDate = startDate;
var downlinesBalanceTradesEndDate = endDate;

$(document).ready(function() {	
	init_open_trades_table();
	init_close_trades_table();
	init_balance_clients_table();
	init_downlines_open_trades_table();
	init_downlines_close_trades_table();
	init_downlines_balance_clients_table();
	
	init_columns_toggle();
	
	register_date_change_listener("clients-open-trades-date-range", client_open_date_range_change);
	register_date_change_listener("clients-close-trades-date-range", client_close_date_range_change);
	register_date_change_listener("clients-balance-date-range", client_balance_date_range_change);
	register_date_change_listener("downline-open-clients-date-range", downlines_clients_open_trades_date_range_change);
	register_date_change_listener("downline-clients-date-range", downlines_clients_date_range_change);
	register_date_change_listener("clients-downlines-balance-date-range", downlines_clients_balance_date_range_change);
	
	/*get_clients_open_trades();
	get_clients_close_trades();
	get_clients_balance_trades();
	get_downline_open_clients_trades();
	get_downline_clients_trades();
	get_downline_balance_clients_trades();*/
});


function init_open_trades_table() {
	var oOpenTradesClientsTradesColumns = [
  		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], false],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false]
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
  	
  	oOpenTradesTable = $("#clients-open-trades-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oOpenTradesClientsTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/" + agentId + "/clients/open.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeOpenTime", "value" : openTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeOpenTime", "value" : openTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	calculateOpenVolume();
                	/*$.gritter.add({
    		            title: 'Open Trades',
    		            text: 'Open Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		}
	});
	
	$(oOpenTradesTable).removeClass("hide");
}

function init_close_trades_table() {
	
	var oCloseTradesClientsTradesColumns = [
  		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], false],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.closetime"], true],
		[langMappings["label.closeprice"], true],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false],
  		[langMappings["label.commission"], true]
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
	
	oCloseTradesTable = $("#clients-close-trades-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oCloseTradesClientsTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/" + agentId + "/clients/close.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeCloseTime", "value" : closeTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeCloseTime", "value" : closeTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	calculateCommissions();
                	/*$.gritter.add({
    		            title: 'Close Trades',
    		            text: 'Close Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		}
	});
	
	$(oCloseTradesTable).removeClass("hide");
}

function init_balance_clients_table() {
	var oBalanceTradesClientsTradesColumns = [
  		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], false],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.closetime"], true],
		[langMappings["label.closeprice"], true],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false]
  		];
  	
  	var oBalanceTradesClientsTradesAOColumns = [];
  	for (var i in oBalanceTradesClientsTradesColumns) {
  		var column = oBalanceTradesClientsTradesColumns[i];
  		oBalanceTradesClientsTradesAOColumns.push({"bVisible" : column[1]});
  		
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
	
	oBalanceTradesTable = $("#clients-balance-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oBalanceTradesClientsTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/" + agentId + "/clients/balance.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeCloseTime", "value" : balanceTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeCloseTime", "value" : balanceTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	/*$.gritter.add({
    		            title: 'Balance Trades',
    		            text: 'Balance Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			//console.log(aData);
		}
	});
	
	$(oBalanceTradesTable).removeClass("hide");
}

function init_downlines_open_trades_table() {
	var oDownlinesClientsOpenTradesColumns = [
		[langMappings["label.downline"], true],
		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], false],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false]
		];
	var oDownlinesClientsOpenTradesAOColumns = [];
	for (var i in oDownlinesClientsOpenTradesColumns) {
		var column = oDownlinesClientsOpenTradesColumns[i];
		oDownlinesClientsOpenTradesAOColumns.push({"bVisible" : column[1]});
		
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

		$("#downline-clients-open-trades-table-config .modal-body table tbody").append(tr);
	}
	
	oDownlinesClientsOpenTradesTable = $("#downline-clients-open-trades-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oDownlinesClientsOpenTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/downlines/" + agentId + "/clients/open.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeOpenTime", "value" : downlinesOpenTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeOpenTime", "value" : downlinesOpenTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	/*$.gritter.add({
    		            title: 'Downlines Open Trades',
    		            text: 'Downline Open Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		}
	});
	
	$(oOpenTradesTable).removeClass("hide");
}

function init_downlines_close_trades_table() {
	var oDownlinesClientsTradesColumns = [
		[langMappings["label.downline"], true],
		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], true],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.closetime"], true],
		[langMappings["label.closeprice"], true],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false]
		];
	
	var oDownlinesClientsTradesAOColumns = [];
	for (var i in oDownlinesClientsTradesColumns) {
		var column = oDownlinesClientsTradesColumns[i];
		oDownlinesClientsTradesAOColumns.push({"bVisible" : column[1]});
		
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
		
		$("#downline-clients-trades-table-config .modal-body table tbody").append(tr);
	}	
	
	oDownlinesClientsTradesTable = $("#downline-clients-trades-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oDownlinesClientsTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/downlines/" + agentId + "/clients/close.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeCloseTime", "value" : downlinesCloseTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeCloseTime", "value" : downlinesCloseTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	calculateDownlinesCommissions();
                	/*$.gritter.add({
    		            title: 'Downlines Close Trades',
    		            text: 'Downline Close Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		}
	});
	
	$(oDownlinesClientsTradesTable).removeClass("hide");
}

function init_downlines_balance_clients_table() {
	var oDownlinesBalanceClientsTradesColumns = [
		[langMappings["label.downline"], true],
		[langMappings["label.ticket"], true],
		[langMappings["label.login"], true],
		[langMappings["label.symbol"], true],
		[langMappings["label.digits"], false],
		[langMappings["label.cmd"], false],
		[langMappings["label.volume"], true],
		[langMappings["label.opentime"], true],
		[langMappings["label.openprice"], true],
		[langMappings["label.sl"], false],
		[langMappings["label.tp"], false],
		[langMappings["label.closetime"], true],
		[langMappings["label.closeprice"], true],
		[langMappings["label.taxes"], false],
		[langMappings["label.profit"], false],
		[langMappings["label.comment"], false],
		[langMappings["label.internalid"], false],
		[langMappings["label.marginrate"], false],
		[langMappings["label.timestamp"], false],
		[langMappings["label.modifytime"], false]
		];
	
	var oDownlinesBalanceClientsTradesAOColumns = [];
	for (var i in oDownlinesBalanceClientsTradesColumns) {
		var column = oDownlinesBalanceClientsTradesColumns[i];
		oDownlinesBalanceClientsTradesAOColumns.push({"bVisible" : column[1]});
		
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
		
		$("#clients-downlines-balance-table-config .modal-body table tbody").append(tr);
	}
	oDownlinesBalanceClientsTradesTable = $("#clients-downlines-balance-table").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"iDisplayLength" : 10,
		"aoColumns" : oDownlinesBalanceClientsTradesAOColumns,
		//agentId is a global variable, declared in javascripts-table-agent-my.jsp
		"sAjaxSource": "/json/dt/trades/downlines/" + agentId + "/clients/balance.json",
		"fnServerParams": function(aoData) {			
			aoData.push( {"name": "startRangeCloseTime", "value" : downlinesBalanceTradesStartDate.toString(dateFormatJSON) } );
			aoData.push( {"name": "endRangeCloseTime", "value" : downlinesBalanceTradesEndDate.toString(dateFormatJSON) } );
		},
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			for(var paramIndex in aoData) {
				var param = aoData[paramIndex];
				if (param.name == "sSearch" && (param.value == "undefined" || param.value === undefined)) {
					param.value = "";
				}
			}
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {
                	fnCallback(data); 
                	App.unblockUI($("#table-mt4users"));
                	/*$.gritter.add({
    		            title: 'Downlines Balance Trades',
    		            text: 'Downline Balance Trades updated.'
    		        });*/
                },
                "cache": false
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			//console.log(aData);
		}
	});
	
	$(oDownlinesBalanceClientsTradesTable).removeClass("hide");
}

function init_columns_toggle() {
	var i = 0;
	$(".column-toggle", $("#clients-close-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oCloseTradesTable);
		$(this).click(table_column_toggle);
	});
	var i = 0;
	$(".column-toggle", $("#clients-open-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oOpenTradesTable);
		$(this).click(table_column_toggle);
	});
	var i = 0;
	$(".column-toggle", $("#downline-clients-open-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oDownlinesClientsOpenTradesTable);
		$(this).click(table_column_toggle);
	});
	var i = 0;
	$(".column-toggle", $("#downline-clients-trades-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oDownlinesClientsTradesTable);
		$(this).click(table_column_toggle);
	});
	var i = 0;
	$(".column-toggle", $("#clients-balance-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oBalanceTradesTable);
		$(this).click(table_column_toggle);
	});
	var i = 0;
	$(".column-toggle", $("#clients-downlines-balance-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oDownlinesBalanceClientsTradesTable);
		$(this).click(table_column_toggle);
	});
}

function table_column_toggle() {
	var index = $(this).data("index");
	var is_checked = $(this).is(":checked");
	var datatable = $(this).data("datatable");
	datatable.fnSetColumnVis(index, is_checked);
}

function client_open_date_range_change(start, end) {
	openTradesStartDate = start;
	openTradesEndDate = end;
	//get_clients_open_trades();
	$('#clients-open-trades-date-range span')
		.html(start.toString(dateFormatView) + ' - ' + end.toString(dateFormatView));
	oOpenTradesTable.fnFilter();
}

function client_close_date_range_change(start, end) {
	closeTradesStartDate = start;
	closeTradesEndDate = end;
	//get_clients_close_trades();
	$('#clients-close-trades-date-range span')
		.html(start.toString(dateFormatView) + ' - ' + end.toString(dateFormatView));
	oCloseTradesTable.fnFilter();
}

function client_balance_date_range_change(start, end) {
	balanceTradesStartDate = start;
	balanceTradesEndDate = end;
	//get_clients_balance_trades();
	$('#clients-balance-date-range span')
		.html(start.toString(dateFormatView) + ' - ' + end.toString(dateFormatView));
	oBalanceTradesTable.fnFilter();
}

function downlines_clients_open_trades_date_range_change(start, end) {
	downlinesOpenTradesStartDate = start;
	downlinesOpenTradesEndDate = end;
	//get_downline_open_clients_trades();
	$('#downline-open-clients-date-range span')
		.html(downlinesOpenTradesStartDate.toString(dateFormatView) + ' - ' + downlinesOpenTradesEndDate.toString(dateFormatView));
	oDownlinesClientsOpenTradesTable.fnFilter();
}

function downlines_clients_date_range_change(start, end) {
	downlinesCloseTradesStartDate = start;
	downlinesCloseTradesEndDate = end;
	//get_downline_clients_trades();
	$('#downline-clients-date-range span')
		.html(downlinesCloseTradesStartDate.toString(dateFormatView) + ' - ' + downlinesCloseTradesEndDate.toString(dateFormatView));
	
	oDownlinesClientsTradesTable.fnFilter();
}

function downlines_clients_balance_date_range_change(start, end) {
	downlinesBalanceTradesStartDate = start;
	downlinesBalanceTradesEndDate = end;
	//get_downline_balance_clients_trades();
	$('#clients-downlines-balance-date-range span')
		.html(downlinesBalanceTradesStartDate.toString(dateFormatView) + ' - ' + downlinesBalanceTradesEndDate.toString(dateFormatView));
	
	oDownlinesBalanceClientsTradesTable.fnFilter();
}

function get_clients_open_trades() {
	App.blockUI(jQuery("#clients-open-trades-table"));
	// agentId is a global variable. Declared in javascripts.jsp.
	$.get("/json/rest/clients/trades/o.json", 
			{
				startRangeOpenTime: startDate.toString(dateFormatJSON),
				endRangeOpenTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_clients_open_trades_table, null);
				App.unblockUI(jQuery("#clients-open-trades-table"));
				$.gritter.add({
		            title: 'Clients\' Trades',
		            text: 'My Clients\' Latest Trades updated.'
		        });
			});
}

function get_clients_close_trades() {
	App.blockUI(jQuery("#clients-close-trades-table"));
	$.get("/json/rest/clients/trades/c.json", 
			{
				startRangeCloseTime: startDate.toString(dateFormatJSON),
				endRangeCloseTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_clients_close_trades_table, null);
				App.unblockUI(jQuery("#clients-close-trades-table"));
				$.gritter.add({
		            title: 'Clients\' Close Trades',
		            text: 'My Clients\' Close Trades updated.'
		        });
			});
}

function get_clients_balance_trades() {
	/*App.blockUI(jQuery("#clients-balance-table"));
	$.get("/json/rest/clients/trades/b.json", 
			{
				startRangeCloseTime: startDate.toString(dateFormatJSON),
				endRangeCloseTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_clients_balance_trades_table, null);
				App.unblockUI(jQuery("#clients-balance-table"));
				$.gritter.add({
		            title: 'Clients\' Balance Trades',
		            text: 'My Clients\' Balance Trades updated.'
		        });
			});*/
}

function get_downline_open_clients_trades() {
	/*App.blockUI(jQuery("#downline-clients-open-trades-table"));
	$.get("/json/rest/clients/trades/downlines/" + agentId + ".json", 
			{
				ts: new Date().getTime(),
				startRangeOpenTime: startDate.toString(dateFormatJSON),
				endRangeOpenTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_downlines_clients_open_trades_table, null);
				App.unblockUI(jQuery("#downline-clients-open-trades-table"));
				$.gritter.add({
		            title: 'Downlines\' Clients\' Open Trades',
		            text: 'My Downlines\' Clients\' Open Trades updated.'
		        });
			});*/
}

function get_downline_clients_trades() {
	/*App.blockUI(jQuery("#downline-clients-trades-table"));
	// agentId is a global variable. Declared in javascripts.jsp.
	$.get("/json/rest/clients/trades/downlines/" + agentId + ".json", 
			{
				ts: new Date().getTime(),
				startRangeCloseTime: startDate.toString(dateFormatJSON),
				endRangeCloseTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_downlines_clients_trades_table, null);
				App.unblockUI(jQuery("#downline-clients-trades-table"));
				$.gritter.add({
		            title: 'Downlines\' Clients\' Trades',
		            text: 'My Downlines\' Clients\' Trades updated.'
		        });
			});*/
}

function get_downline_balance_clients_trades() {
	/*App.blockUI(jQuery("#clients-downlines-balance-table"));
	// agentId is a global variable. Declared in javascripts.jsp.
	$.get("/json/rest/clients/balance/downlines/" + agentId + ".json", 
			{
				ts: new Date().getTime(),
				startRangeCloseTime: startDate.toString(dateFormatJSON),
				endRangeCloseTime: endDate.toString(dateFormatJSON)
			}, 
			function(data) {
				handle_json_response(data, update_downlines_balance_clients_trades_table, null);
				App.unblockUI(jQuery("#clients-downlines-balance-table"));
				$.gritter.add({
		            title: 'Downlines\' Clients\' Balance Trades',
		            text: 'My Downlines\' Clients\' Balance Trades updated.'
		        });
			});*/
}

function update_clients_open_trades_table(trades) {
	oOpenTradesTable.fnClearTable();
	
	var totalVolume = 0;
	//var totalCommission = 0.00;
	
	for (var i in trades) {
		
		var trade = trades[i];
		var volume = trade.volume;
		
		oOpenTradesTable.fnAddData([
				        		 	trade.ticket,
				        		 	/*trade.agentClientDTO.login,*/
				        		 	trade.login,
				        		 	trade.symbol,
				        		 	trade.digits,
				        		 	trade.cmd,
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
		
		totalVolume += trade.volume;
		//totalCommission += trade.commission;
	}
	
	$(oOpenTradesTable).removeClass("hide");
	
	$(".clients-open-trades-summary .volume").text((totalVolume / 100).toFixed(2));
	//$(".clients-open-trades-summary .commission").text(totalCommission);
	//$(".clients-open-trades-summary .commission").formatCurrency();
}

function update_clients_close_trades_table(trades) {
	oCloseTradesTable.fnClearTable();
	
	var totalVolume = 0;
	var totalCommission = 0.00;
	
	for (var i in trades) {
		
		var trade = trades[i];
		var volume = trade.volume;
		var commission = trade.commission;
		
		oCloseTradesTable.fnAddData([
		        		 	trade.ticket,
		        		 	trade.login,
		        		 	trade.symbol,
		        		 	trade.digits,
		        		 	trade.cmd,
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
		        			new Date(trade.modifyTime).toString(fullDateFormatJSON),
		        			commission.toFixed(2)
		        			]);
		
		if (trade.cmd == 0 || trade.cmd == 1) {
			totalVolume += trade.volume;
			totalCommission += trade.commission;
		}
		
	}
	
	$(oCloseTradesTable).removeClass("hide");
	
	$(".clients-close-trades-summary .volume").text((totalVolume / 100).toFixed(2));
	$(".clients-close-trades-summary .commission").text(totalCommission);
	$(".clients-close-trades-summary .commission").formatCurrency();
}

function update_clients_balance_trades_table(trades) {
	oBalanceTradesTable.fnClearTable();
	
	for (var i in trades) {
		
		var trade = trades[i];
		
		oBalanceTradesTable.fnAddData([
		        		 	trade.ticket,
		        		 	trade.login,
		        		 	trade.symbol,
		        		 	trade.digits,
		        		 	trade.cmd,
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
		        			new Date(trade.modifyTime).toString(fullDateFormatJSON),
		        			commission.toFixed(2)
		        			]);
	}
	
	$(oBalanceTradesTable).removeClass("hide");
}

function update_downlines_clients_open_trades_table(trades) {
	
	oDownlinesClientsOpenTradesTable.fnClearTable();
	
	var totalVolume = 0;
	//var totalCommission = 0.00;
	
	for (var i in trades) {
		
		var trade = trades[i];
		var volume = trade.volume;
		
		oDownlinesClientsOpenTradesTable.fnAddData([
		    trade.agentDTO.label,
		 	trade.ticket,
		 	trade.agentClientDTO.login,
		 	trade.symbol,
		 	trade.digits,
		 	trade.cmd,
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
		
		totalVolume += trade.volume;
		//totalCommission += trade.commission;
	}
	
	$(oOpenTradesTable).removeClass("hide");
	
	//$(".clients-open-trades-summary .volume").text((totalVolume / 100).toFixed(2));
	//$(".clients-open-trades-summary .commission").text(totalCommission);
	//$(".clients-open-trades-summary .commission").formatCurrency();
}

function update_downlines_clients_trades_table(trades) {
	
	oDownlinesClientsTradesTable.fnClearTable();
	
	var totalVolume = 0;
	var totalCommission = 0.00;
	
	for (var i in trades) {
		var trade = trades[i];
		
		var volume = trade.volume;
		var commission = trade.commission;
		
		oDownlinesClientsTradesTable.fnAddData(
		[
		 	trade.agentDTO.label,
		 	trade.ticket,
		 	trade.agentClientDTO.login,
		 	trade.symbol,
		 	trade.digits,
		 	trade.cmd,
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
			new Date(trade.modifyTime).toString(fullDateFormatJSON)
			]);
		
		if (trade.cmd == 0 || trade.cmd == 1) {
			totalVolume += trade.volume;
			totalCommission += trade.commission;
		}
	}
	
	$(oDownlinesClientsTradesTable).removeClass("hide");
	
	$(".downline-clients-trades-summary .volume").text((totalVolume / 100).toFixed(2));
	$(".downline-clients-trades-summary .commission").text(totalCommission);
	$(".downline-clients-trades-summary .commission").formatCurrency();
}

function update_downlines_balance_clients_trades_table(trades) {
	
	oDownlinesBalanceClientsTradesTable.fnClearTable();
	
	for (var i in trades) {
		var trade = trades[i];
		var volume = trade.volume;
		
		oDownlinesBalanceClientsTradesTable.fnAddData(
		[
		 	trade.agentDTO.label,
		 	trade.ticket,
		 	trade.agentClientDTO.login,
		 	trade.symbol,
		 	trade.digits,
		 	trade.cmd,
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
			new Date(trade.modifyTime).toString(fullDateFormatJSON)
			]);
		
	}
	
	$(oDownlinesBalanceClientsTradesTable).removeClass("hide");
}

function calculateOpenVolume() {
	$.ajax( {
    	"type" : "get",
        "url": "/json/rest/clients/openvolume.json",
        "data": {
        	startRangeOpenTime : openTradesStartDate.toString(dateFormatJSON),
        	endRangeOpenTime : openTradesEndDate.toString(dateFormatJSON)
        },
        "success": function(data) {
        	var payload = data.response.payload;
        	$(".clients-open-trades-summary .volume").text((payload.totalVolume / 100).toFixed(2));
        },
        "cache": false
    } );
}

function calculateCommissions() {
	$.ajax( {
    	"type" : "get",
        "url": "/json/rest/clients/commissions.json",
        "data": {
        	startRangeCloseTime : closeTradesStartDate.toString(dateFormatJSON),
        	endRangeCloseTime : closeTradesEndDate.toString(dateFormatJSON)
        },
        "success": function(data) {
        	var payload = data.response.payload;
        	$(".clients-close-trades-summary .volume").text((payload.totalVolume / 100).toFixed(2));
        	$(".clients-close-trades-summary .commission").text(payload.commission);
        	$(".clients-close-trades-summary .commission").formatCurrency();
        },
        "cache": false
    } );
}

function calculateDownlinesCommissions() {
	$.ajax( {
    	"type" : "get",
        "url": "/json/rest/clients/commissions/downlines.json",
        "data": {
        	startRangeCloseTime : downlinesCloseTradesStartDate.toString(dateFormatJSON),
        	endRangeCloseTime : downlinesCloseTradesEndDate.toString(dateFormatJSON)
        },
        "success": function(data) {
        	var payload = data.response.payload;
        	var totalComm = 0.00;
        	var totalVol = 0.00;
        	for(var i in payload) {
        		var comm = payload[i].commission;
        		var vol = payload[i].totalVolume;
        		
        		totalComm += comm;
        		totalVol += vol;
        	}
        	
        	$(".downline-clients-trades-summary .volume").text((totalVol / 100).toFixed(2));
        	$(".downline-clients-trades-summary .commission").text(totalComm);
        	$(".downline-clients-trades-summary .commission").formatCurrency();
        },
        "cache": false
    } );
}