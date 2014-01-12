startDate = Date.today().add({
        days: -7
    });
endDate = Date.today();
dateFormatView = "MMMM d, yyyy";
dateFormatJSON = "dd-MM-yyyy";
fullDateFormatJSON = "dd-MM-yyyy hh:mm tt";

function handle_json_response(data, successhandlerfunc, container) {
	if (data.response.status == "ERR") {
		var arr = data.response.errorMessage.split(":");
		var componentId = arr[0];
		var message = arr[1];
		
		if(container != null) {
			var parent = $("div.alert-error", container).parent();
			$("div.alert-success", parent).addClass("hide");
			$("div.alert-error.show-alert", parent).remove();
			var alert = $("div.alert-error", container).clone(true);
			alert.removeClass("hide");
			alert.addClass("show-alert");
			alert.append(message);
			parent.prepend(alert);
		}
		
	} else {
		if ($.isFunction(successhandlerfunc)) {
			successhandlerfunc(data.response.payload, container);
		} else {
			document.location = "";
		}
	}
}

function getScript(src) {
	if (jQuery) {
	    $("body").append('<script src="' + src + '"' +
	                   ' type="text/javascript"><' + '/script>');
	}
}