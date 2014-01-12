$(document).ready(function() {
	$("#password-change-form #password-change-submit").click(function() {
		var currentPassword = $("#password-change-form input[name=current_password]").val();
		var newPassword1 = $("#password-change-form input[name=new_password1]").val();
		var newPassword2 = $("#password-change-form input[name=new_password2]").val();
		
		if (newPassword1 == "" && newPassword2 == "") {
			$(".show-alert").remove();
			var alert = $("#password-change-form-div .alert-error").clone();
			alert.append("New password fields are blank.");
			alert.removeClass("hide");
			alert.addClass("show-alert");
			$("#password-change-form-div").prepend(alert);
		} else {
			$.post("/json/rest/profile/password/change.json", 
			{
				currentPassword: currentPassword,
				newPassword1: newPassword1,
				newPassword2: newPassword2
			}, 
			function(data) {
				handle_json_response(data, change_password_successful, $("#password-change-form-div"));
			});
		}
	});
});

function change_password_successful(data, container) {
	$("#password-change-form-div .alert-error.show-alert").remove();
	$(".alert-success", container).removeClass("hide");
}