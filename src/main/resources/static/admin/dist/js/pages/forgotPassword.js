
		$(document).ready(function() {

			var email = $("#email").val();

			$('#loader').hide();

			$("#email").change(function(event) {
				var txtemail = $(this).val();
				if (txtemail.length == 0) {
					$(".errorMsg").html("");
				}
			});
		});

		function validateFields() {
			var token = document.getElementById("token");
			var newPassword = document.getElementById("newPassword");
			var confirmPassword = document.getElementById("confirmPassword");
			if (token == "" || token == null) {
				alert("token can't be blank");
				return false;
			} else if (newPassword == "" || newPassword == null) {
				alert("newPassword can't be blank");
				return false;

			} else if (confirmPassword == "" || confirmPassword == null) {
				alert("confirmPassword can't be blank");
				return false;

			}
		}
	

	
		function formValidation() {

			var email = $("#email").val();

			$.ajax({
				type : "GET",
				url : "/admin/getEmailId/" + email,
				datatype : 'json',
				data : {
					email : email
				},
				success : function(response) {

					if (response == null) {
						$(".errorMsg").text("Please Provied Valid email....");
					} else {

						$(".errorMsg").text("");
					}
				}

			});

		}
	

	
		function sendOTP() {

			var email = $("#email").val();
			$('#loader').show();

			$.ajax({
				type : "POST",
				url : "/admin/sendEmail/" + email,

				datatype : 'json',
				data : {
					email : email
				},
				success : function(response) {

					JSON.stringify(response)

					var otp = response['otp'];

					if (otp != null) {
						$('#loader').hide();
						$('.success').text(
								'OTP Send successfully on your Email ...!');
						setTimeout(function() {
							window.location = '/demo/passwordChangePage';
						}, 800);

					} else {
						$('.fail').text('Technical Error ...!');
						setTimeout(function() {
							window.location = '/home/SendOTP';
						}, 1000 / 2);

					}

				}

			});

		}
	

