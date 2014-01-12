function commissionsDatatable(startDate, endDate) {
	
	 var table = $(".commissions-table-template").clone();
	 table.removeClass("commissions-table-template");
	 table.addClass("commissions-table");
	 table.show();
	 $(".portlet-body").append(table);
	
	// JS object agents declared in WEB-INF/jsp/agents/js.jsp mapped to /agent/js
	 if (agents) {
		var agentIds = [];
		for (var key in agents) {
			var agent = agents[key];
			agentIds.push(agent.id);
		}
		var params = {
			agentIds: agentIds.toString(),
			startRangeCloseTime: startDate.toString("dd-MM-yyyy"),
			endRangeCloseTime: endDate.toString("dd-MM-yyyy")
		};
		$.get("/json/rest/agents/commissions.json", 
				params, 
				function(data) {
					var tbody = $("tbody", table);
					tbody.empty();
					var response = data.response;
					var status = response.status;
					if (status == "OK") {
						var payload = response.payload;
						for (var i in payload) {
							var commission = payload[i];
							
							var tr = $("<tr></tr>");
							var loginCell = $("<td></td>");
							var nameCell = $("<td></td>");
							var emailCell = $("<td></td>");
							var volumeCell = $("<td></td>");
							var commissionCell = $("<td></td>");
							
							loginCell.text(commission.login);
							nameCell.text(commission.name);
							emailCell.text(commission.email);
							volumeCell.text(commission.totalVolume);
							commissionCell.text(commission.commission);
							
							$(commissionCell).formatCurrency();
							
							tr.append(loginCell);
							tr.append(nameCell);
							tr.append(emailCell);
							tr.append(volumeCell);
							tr.append(commissionCell);
							
							tbody.append(tr);
						}
						
						oTable = $(table).dataTable();
					} else {
						alert(response.errorMessage);
					}
				})
		.fail( function(error) {alert(error);} );
	 }
}

$(document).ready(function() {
	 
	 var startDate =  Date.today().add({
         days: -29
     });
	 var endDate = Date.today();
	 
	 commissionsDatatable(startDate, endDate);
	
	 $('#commissions-report-range').daterangepicker({
         ranges: {
             'Today': ['today', 'today'],
             'Yesterday': ['yesterday', 'yesterday'],
             'Last 7 Days': [Date.today().add({
                 days: -6
             }), 'today'],
             'Last 30 Days': [Date.today().add({
                 days: -29
             }), 'today'],
             'This Month': [Date.today().moveToFirstDayOfMonth(), Date.today().moveToLastDayOfMonth()],
             'Last Month': [Date.today().moveToFirstDayOfMonth().add({
                 months: -1
             }), Date.today().moveToFirstDayOfMonth().add({
                 days: -1
             })]
         },
         opens: 'left',
         format: 'dd/MM/yyyy',
         separator: ' to ',
         startDate: Date.today().add({
             days: -29
         }),
         endDate: Date.today(),
         minDate: '01/01/2012',
         maxDate: '12/31/2014',
         locale: {
             applyLabel: 'Update',
             fromLabel: 'From',
             toLabel: 'To',
             customRangeLabel: 'Custom Range',
             daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
             monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
             firstDay: 1
         },
         showWeekNumbers: true,
         buttonClasses: ['btn-danger']
     },

     function (start, end) {
    	 oTable.fnDestroy();
    	 oTable.remove();
    	 commissionsDatatable(start, end);
         App.blockUI(jQuery("#commissions-portlet"));
         setTimeout(function () {
             App.unblockUI(jQuery("#commissions-portlet"));
             $.gritter.add({
                 title: 'Commissions',
                 text: 'Commissions date range updated.'
             });
             App.scrollTo();
         }, 1000);
         $('#commissions-report-range span').html(start.toString('MMMM d, yyyy') + ' - ' + end.toString('MMMM d, yyyy'));

     });

     $('#commissions-report-range').show();

     $('#commissions-report-range span').html(Date.today().add({
         days: -29
     }).toString('MMMM d, yyyy') + ' - ' + Date.today().toString('MMMM d, yyyy'));
});