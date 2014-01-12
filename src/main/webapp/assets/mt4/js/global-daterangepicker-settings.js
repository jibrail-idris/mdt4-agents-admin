var date_change_listeners = {};
var daterangepicker_settings = {
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
    format: 'dd-MM-yyyy',
    separator: ' to ',
    startDate: Date.today().add({
        days: -29
    }),
    endDate: Date.today(),
    minDate: '01/01/2012',
    maxDate: '12/31/2014',
    locale: {
        applyLabel: 'Submit',
        fromLabel: 'From',
        toLabel: 'To',
        customRangeLabel: 'Custom Range',
        daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
        monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        firstDay: 1
    },
    showWeekNumbers: true,
    buttonClasses: ['btn-danger']
}

function register_date_change_listener(componentId, func) {
	date_change_listeners[componentId] = func;
}

function on_date_change(start, end) {
	var elements = this.element;
	for (var i = 0; i < elements.length; i++) {
		var element = $(elements[i]);
		var func = date_change_listeners[element.attr("id")](start, end);
		//$("span", $("#" + element.attr("id"))).html(start.toString(dateFormatView) + ' - ' + end.toString(dateFormatView));
		update_date_label(element.attr("id"), start, end);
		
	}
}

function update_date_label(id, start, end) {
	$("span", $("#" + id)).html(start.toString(dateFormatView) + ' - ' + end.toString(dateFormatView));
}

$(document).ready(function() {
	$(".datepickerrange").daterangepicker(daterangepicker_settings, on_date_change);
	$('.datepickerrange span').html(startDate.toString(dateFormatView) + ' - ' + endDate.toString(dateFormatView));
});
