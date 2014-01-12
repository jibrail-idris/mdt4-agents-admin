$(document).ready(function() {
	//populate_agents_select($("select[name=upline]", $("#portlet-mt4-new-agent-form")));
	//populate_users_table();
	
	init_users_table();
	init_columns_toggle();
	//get_users();
});

function init_users_table() {
	
	var oUsersColumns = [
				["LOGIN", true],
				["GROUP", true],
				["ENABLE", false],
				["ENABLE_CHANGE_PASS", false],
				["ENABLE_READONLY", false],
				["PASSWORD_PHONE", false],
				["NAME", true],	
				["COUNTRY", true],
				["CITY", true],
				["STATE", true],
				["ZIPCODE", false],
				["ADDRESS", false],
				["PHONE", false],							
				["EMAIL", true],
				["COMMENT", false],
				["ID", false],
				["STATUS", false],
				["REGDATE", false],
				["LASTDATE", false],
				["LEVERAGE", false],
				["AGENT_ACCOUNT", false],
				["TIMESTAMP", false],
				["BALANCE", false],
				["PREVMONTHBALANCE", false],
				["PREVBALANCE", false],
				["CREDIT", false],
				["INTERESTATE", false],
				["TAXES", false],
				["SEND_REPORTS", false],
				["USER_COLOR", false],
				["EQUITY", false],
				["MARGIN", false],
				["MARGIN_LEVEL", false],
				["MARGIN_FREE", false],
				["MODIFY_TIME", false],
				["UPLINE", true],
				["STATUS", true]
     		];
	
	
	var oUsersAOColumns = [];
  	for (var i in oUsersColumns) {
  		var column = oUsersColumns[i];
  		var column_def = {"bVisible" : column[1]};
  		if (column[0] == "UPLINE" || column[0] == "STATUS") {
  			column_def.bSortable = false;
  		}
  		oUsersAOColumns.push(column_def);
  		
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
  		
  		$("#users-table-config .modal-body table tbody").append(tr);
  	}
	
	oUsersTable = $("#table-mt4users").dataTable({
		"bProcessing": false,
		"bServerSide": true,
		"iDisplayLength" : 10,
		"aoColumns": oUsersAOColumns,
		"sAjaxSource": "/json/dt/users.json",
		"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
			App.blockUI($("#table-mt4users"));
			oSettings.jqXHR = $.ajax( {
            	"type" : "get",
                "url": sUrl,
                "data": aoData,
                "success": function(data) {fnCallback(data); App.unblockUI($("#table-mt4users"));},
                "cache": true
            } );
		},
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			var login = $("td:eq(0)", nRow);
			var cells = $("td", nRow);
			var uplineCell = $(cells[cells.length - 2]);
			var statusCell = $(cells[cells.length - 1]);
			var isAgent = agents[login.text()];
			var assignButton = $("<button type='button' class='btn blue mini' style='height: auto;'>Assign As Agent</button>");
			if(isAgent) {
				make_button_assigned($(assignButton));
			} else {
				$(assignButton).click(function() {
					
					var form = $("#portlet-mt4-new-agent-form").clone();
					make_agent_assignment_wizard(form);
					make_agents_autocomplete($("input.upline-autocomplete", form), $("input[name=upline]", form));
					$("input[name=mt4login]", form).val(login.text());					
					var modal = $(form).modal();
					$(".button-submit", form).click(function(){
						
						$.gritter.add({
					        title: 'Users',
					        text: 'Saving...'
					    });
						clearErrors(form);
						if (validation(form)) {
					    	var post_params = {
					    		"username" : $("input[name=username]", form).val(),
					    		"password1": $("input[name=password]", form).val(),
					    		"password2": $("input[name=password2]", form).val(),
					    		"mt4Login": $("input[name=mt4login]", form).val(),
					    		"commission": $("input[name=commission]", form).val(),
					    		"upline": $("input[name=upline]", form).val()
					    	}
	
					    	$.post("/json/rest/agents/save.json", post_params,
					    			function(data){
					    				//App.blockUI(form);
					    				if (data.response.status == "ERR") {
					    					var message = data.response.errorMessage;
					    					var componentId = message.substring(0, message.indexOf(":"));
					    					var errorMessage = message.substring(message.indexOf(":") + 1);
					    					var component = $("input[name=" + componentId + "]", form);
					    					error(component, errorMessage);
					    					$.gritter.add({
					    				        title: 'Users',
					    				        text: 'An error was encountered while trying to assign user as agent.'
					    				    });
					    				} else {
					    					make_button_assigned($(assignButton));
					    					update_upline(post_params.mt4Login, uplineCell);
					    					modal.modal("hide");
					    					$("#agent-assignment-wizard .tab-pane").removeClass("active");
					    					$("#agent-assignment-wizard #tab1").addClass("active");
					    					$.gritter.add({
										        title: 'Users',
										        text: 'New Agent saved.'
										    });
					    					getScript("/agent/js"); //refreshes the global agent JS variable without a full page reload.
					    					//App.unblockUI(form);
					    				}
					    			}, "json");
						}
				    });
				});
			}
			statusCell.empty();
			statusCell.append(assignButton);
		}
	});
	$("#table-mt4users").removeClass("hide");
}

function init_columns_toggle() {
	var i = 0;
	$(".column-toggle", $("#users-table-config")).each(function() {
		$(this).data("index", i++);
		$(this).data("datatable", oUsersTable);
		$(this).click(table_column_toggle);
	});
}

function update_users_table(users) {
	
	oUsersTable.fnClearTable();
	
	for(var i in users) {
		var user = users[i];
		var assignment = null;
		var parentAgentLabel = null;
		if (user.assignment != null) {
			assignment = user.assignment;
			if (assignment.parentAgentId != null) {
				upline = assignment.parentAgentId;
				parentAgentLabel = assignment.parentAgentLabel;
			}
		}
		
		oUsersTable.fnAddData([
								user.login,
								user.group,
								user.enable,
								user.enableChangePass,
								user.enableReadOnly,
								user.passwordPhone,
								user.name,	
								user.country,
								user.city,
								user.state,
								user.zipcode,
								user.address,
								user.phone,							
								user.email,
								user.comment,
								user.id,
								user.status,
								new Date(user.regDate).toString(fullDateFormatJSON),
								new Date(user.lastDate).toString(fullDateFormatJSON),
								user.leverage,
								user.agentAccount,
								new Date(user.timestamp).toString(fullDateFormatJSON),
								user.balance,
								user.prevMonthBalance,
								user.prevBalance,
								user.credit,
								user.interestRate,
								user.taxes,
								user.sendReports,
								user.userColor,
								user.equity,
								user.margin,
								user.marginLevel,
								user.marginFree,
								new Date(user.modifyTime).toString(fullDateFormatJSON),
								parentAgentLabel,
								""
		                       ]);
	}
	App.unblockUI(jQuery("#users-portlet"));
	$.gritter.add({
        title: 'Users',
        text: 'Users table loaded.'
    });
}

function table_column_toggle() {
	var index = $(this).data("index");
	var is_checked = $(this).is(":checked");
	var datatable = $(this).data("datatable");
	datatable.fnSetColumnVis(index, is_checked);
}

function error(component, errorMessage) {
	$(component).parent().parent().addClass("error");
	var span_error = $('<span class="input-error tooltips" data-original-title="' + errorMessage + '"><i class="icon-exclamation-sign"></i></span>');
	$(span_error).tooltip();
	$(component).parent().append(span_error);
}

function clearErrors(form) {
	$(".input-error", form).remove();
	$(".control-group", form).removeClass("error");
}

function make_agents_autocomplete(input, hidden) {
	$(input).autocomplete({
		source: "/json/ac/agents/search.json?ts=" + new Date().getTime(),
		minLength: 1,
		select: function( event, ui ) {
			this.value = ui.item.label;
			$(hidden).val(ui.item.value);
			return false;
		}
	});
}

function get_users() {
	$.gritter.add({
        title: 'Users',
        text: 'Loading users table.'
    });
	App.blockUI($("#users-portlet"));
	setTimeout(function () {
		$.get("/json/rest/users.json", 
				null, 
				function(data) {
					handle_json_response(data, update_users_table, null);
				});
    }, 1000);
}

function make_button_assigned(assign_button) {
	$(assign_button).removeClass("blue");
	$(assign_button).addClass("red");
	$(assign_button).text("Assigned");
	$(assign_button).attr("disabled", "disabled");
}

function update_upline(login, upline_cell) {
	$.get("/json/rest/agents/get.json", 
			{mt4Login : login}, 
			function(data) {
				handle_json_response(data, function(agent) {
					if (agent.parentAgentLabel != null) {
						upline_cell.text(agent.parentAgentLabel);
					}
				})
			});
}

function validation(form) {
	var commission_component = $("input[name=commission]", form);
	var password = $("input[name=password]", form);
	var password2 = $("input[name=password2]", form);
	var commission = commission_component.val();
	var validate_commission = $.isNumeric(commission);
	var is_error = false;
	if (!validate_commission) {
		error(commission_component, "Commission is not a number.");
		is_error = true;
	}
	if ( password.val() != password2.val()) {
		error(password, "Passwords do not match.");
		is_error = true;
	}
	
	if (is_error) {
		$.gritter.add({
	        title: 'Users',
	        text: 'An error was encountered while trying to assign user as agent.'
	    });
	}
	
	return !is_error;
}

function make_agent_assignment_wizard(form) {
	var wizard =  $("#agent-assignment-wizard", form);
	wizard.bootstrapWizard({
		onTabClick: function (tab, navigation, index) {
            return false;
        },
        onNext: function (tab, navigation, index) {
        	var total = navigation.find('li').length;
            var current = index + 1;
            
            $('.step-title', wizard).text('Step ' + (index + 1) + ' of ' + total);
            
            if (current == 1) {
            	wizard.find('.button-previous').hide();
            } else {
            	wizard.find('.button-previous').show();
            }
            
            $(".tab-pane", wizard).removeClass("active");
            $("#tab" + current, wizard).addClass("active");

            if (current >= total) {
            	wizard.find('.button-next').hide();
            	wizard.find('.button-submit').show();
            } else {
            	wizard.find('.button-next').show();
            	wizard.find('.button-submit').hide();
            }
        },
        onPrevious: function (tab, navigation, index) {
        	var total = navigation.find('li').length;
            var current = index + 1;
            // set wizard title
            $('.step-title', wizard).text('Step ' + (index + 1) + ' of ' + total);
            
            if (current == 1) {
            	wizard.find('.button-previous').hide();
            } else {
            	wizard.find('.button-previous').show();
            }
            
            $(".tab-pane", wizard).removeClass("active");
            $("#tab" + current, wizard).addClass("active");

            if (current >= total) {
            	wizard.find('.button-next').hide();
            	wizard.find('.button-submit').show();
            } else {
            	wizard.find('.button-next').show();
            	wizard.find('.button-submit').hide();
            }
        },
        onTabShow: function (tab, navigation, index) {
        	var total = navigation.find('li').length;
            var current = index + 1;
            var $percent = (current / total) * 100;
            wizard.find('.bar').css({
                width: $percent + '%'
            });
        }
	});
	
	wizard.find('.button-previous').hide();
	$(".button-submit", wizard).hide();
	$(".button-previous", wizard).click(function() {
		wizard.bootstrapWizard("previous");
	});
	$(".button-next", wizard).click(function() {
		wizard.bootstrapWizard("next");
	});
	wizard.find('.button-submit').hide();
	wizard.show();
}