

	
	
	
		$(document).ready(function() {

			//$("#token").val(localStorage.getItem('otp'));

			$('.pass_show').append('<span class="ptxt">Show</span>');
		});

		$(document).on('click', '.pass_show .ptxt', function() {

			$(this).text($(this).text() == "Show" ? "Hide" : "Show");

			$(this).prev().attr('type', function(index, attr) {
				return attr == 'password' ? 'text' : 'password';
			});

		});
	


	
		function validateFields() {

			var token = $("#token").val();
			var newPassword = $("#password").val();

			var confirmPassword = $("#confirmPassword").val();

			var checkValue = 1;

			if (newPassword == "" || newPassword == null) {
				alert("newPassword can't be blank");
				checkValue = 0;
			} else if (confirmPassword == "" || confirmPassword == null) {
				alert("confirmPassword can't be blank");
				checkValue = 0;

			} else if (newPassword != confirmPassword) {
				alert("Password does not match...");
				checkValue = 0;

			}

			if (checkValue == 1) {

				$
						.ajax({
							url : "/admin/changePassword",
							type : 'POST',
							dataType : 'json',
							data : {
								"otp" : token,
								"newPassword" : newPassword,
								"confirmPassword" : confirmPassword

							},

						})
						.always(
								function(response) {
									JSON.stringify(response)
									var res = response['responseText'];
									if (res == 'Your password successfully updated.') {
										$('.success')
												.text(
														'Your password successfully updated.');
										setTimeout(function() {
											window.location = '/demo/index';
										}, 3000);

									} else if (res == 'Token expired.') {
										$('.expired').text('Token expired.');
										setTimeout(
												function() {
													window.location = '/demo/passwordChangePage';
												}, 3000);
									}
								});
				return false;
			}

		}
	

	
		function tokenValidation() {

			var token = $("#token").val();

			$.ajax({
				type : "GET",
				url : "/admin/getOtp/" + token,
				datatype : 'json',
				data : {
					token : token
				},
				success : function(response) {

					if (response == null) {
						$(".errorMsg").text("Please Provied Valid token");
					} else {

						$(".errorMsg").text("");
					}
				}

			});

		}
	
