






const loginText = document.querySelector(".title-text .login");
const loginForm = document.querySelector("form.login");
const loginBtn = document.querySelector("label.login");
const signupBtn = document.querySelector("label.signup");
const signupLink = document.querySelector("form .signup-link a");
signupBtn.onclick = (() => {

	loginForm.style.marginLeft = "-50%";

	loginText.style.marginLeft = "-50%";
});
loginBtn.onclick = (() => {

	loginForm.style.marginLeft = "0%";
	loginText.style.marginLeft = "0%";

});
signupLink.onclick = (() => {
	signupBtn.click();
	return false;
});










$(document).ready(function() {

	setTimeout(function() {
		var login = $('input[name="slide"]:checked').val();
		if (login == 'on') {
			$('.SingUpMSG').css('display', 'none');
		}
	}, 2000);

	var name = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var image = $("#image").val();

	/* for loginForm */


	$("#Lemail").change(function(event) {
		var txtemail = $(this).val();
		if (txtemail.length != 0) {
			$(".LemailErr").html("");
		}
	});


	$("#Lpassword").change(function(event) {
		var txtpassword = $(this).val();
		if (txtpassword.length != 0) {
			$(".LpasswordErr").html("");
		}
	});


	$("#name").change(function(event) {
		var txtemail = $(this).val();
		if (txtemail.length != 0) {
			$(".nameErr").html("");
		}
	});

	$("#email").change(function(event) {
		var txtemail = $(this).val();
		if (txtemail.length != 0) {
			$(".emailErr").html("");
		}
	});

	$("#password").change(function(event) {
		var txtpassword = $(this).val();
		if (txtpassword.length != 0) {
			$(".passwordErr").html("");
		}
	});


	$("#image").change(function(event) {
		var txtpassword = $(this).val();
		if (txtpassword.length != 0) {
			$(".imageErr").html("");
		}
	});
});





function imageWithDataForm() {

	var form = document.forms[0];
	var formData = new FormData(form);

	alert(JSON.stringify(Object.fromEntries(formData)));

	if (formData != '') {
		$.ajax({
			url: '/SignUpWithImage',
			type: 'POST',
			data: formData, success: function(response) {

			}
		});

	}
}



function emailValidation(email = '', buttonclick = false) {

	var email = $("#email").val();



	$.ajax({
		type: "GET",
		url: "/admin/getEmailId/" + email,
		datatype: 'json',
		data: {
			email: email
		},
		success: function(response) {


			if (response == null) {
				$(".emailErr").text("");
			}
			else {
				$(".emailErr").text("This email is already available");
			}
		}

	});

}



var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// When the user clicks on the password field, show the message box
myInput.onfocus = function() {
	document.getElementById("message").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function() {
	document.getElementById("message").style.display = "none";
}

// When the user starts to type something inside the password field
myInput.onkeyup = function() {
	// Validate lowercase letters
	var lowerCaseLetters = /[a-z]/g;
	if (myInput.value.match(lowerCaseLetters)) {
		letter.classList.remove("invalid");
		letter.classList.add("valid");
	} else {
		letter.classList.remove("valid");
		letter.classList.add("invalid");
	}

	// Validate capital letters
	var upperCaseLetters = /[A-Z]/g;
	if (myInput.value.match(upperCaseLetters)) {
		capital.classList.remove("invalid");
		capital.classList.add("valid");
	} else {
		capital.classList.remove("valid");
		capital.classList.add("invalid");
	}

	// Validate numbers
	var numbers = /[0-9]/g;
	if (myInput.value.match(numbers)) {
		number.classList.remove("invalid");
		number.classList.add("valid");
	} else {
		number.classList.remove("valid");
		number.classList.add("invalid");
	}

	// Validate length
	if (myInput.value.length >= 8) {
		length.classList.remove("invalid");
		length.classList.add("valid");
	} else {
		length.classList.remove("valid");
		length.classList.add("invalid");
	}

	if (myInput.value.match(lowerCaseLetters) && myInput.value.match(upperCaseLetters)
		&& myInput.value.match(numbers) && myInput.value.length >= 8) {
		$('.hideBox').hide();

	} else {
		$('.hideBox').show();
	}

}







function formValidation() {



	var name = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();


	//var choosedFileName = image.substring(image.lastIndexOf("\\") + 1, image.length);

	// var file = $('#image')[0].files;

	var reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var checkVal = 1;



	if (name == '') {
		$('.nameErr').text('Name should not be blank');
		checkVal = 0;
	}


	if (email == '') {
		$('.emailErr').text("Email can't be blank");
		checkVal = 0;
	}


	if (!reg.test(email)) {
		$('.emailErr').text('Please type valid e-mail address ');
		checkVal = 0;

	}

	if (password == '') {
		$('.passwordErr').text("Password can't be blank");
		checkVal = 0;
	}
	/* if(image==''){
	    
	    
		$('.imageErr').text("Upload your Image");
		checkVal=0;
	} */
	if (checkVal == 1) {

		//	var fileName = file[0]['name'];

		$.ajax({
			url: "/find/signUp",
			type: 'POST',
			dataType: 'json',
			data: {
				'name': name,
				'email': email,
				'password': password



			},

		}).always(function(response) {
			$('.success').text('User Registration has successfully...!');

			setTimeout(function() {
				window.location = '/home/index';
			}, 3000);


		});
		return false;
	}


}







function loginUserForm() {

	var email = $("#Lemail").val();
	var password = $("#Lpassword").val();
	var checkval = 1;

	if (email == '') {
		$('.LemailErr').text('Please enter email');
		checkval = 0;

	}
	if (password == '') {
		$('.LpasswordErr').text('Please enter password');
		checkval = 0;
	}
	if (checkval == 1) {




		$.ajax({
			url: "/admin/login",
			type: 'POST',
			datatype: 'json',
			data: {
				'email': email,
				'password': password
			},
			success: function(response) {

				JSON.stringify(response);

				var loginUserName = response['name'];
				var photoName = response['photos'];
				var userid = response['id'];

				sessionStorage.setItem("loginUserName", loginUserName);
				sessionStorage.setItem("loginUserImg", photoName);
				sessionStorage.setItem("userId", userid);



				if (response != "") {
					$('.LfailMsg').css('display', 'none');
					$('.LsuccessMsg').text('Login Successfull ...!');
					setTimeout(function() {

						window.location = '/demo/dashboard';
					}, 3000);
				}
				else {
					$('.LfailMsg').text('invalid  UserName & Password.....!');
					setTimeout(function() {
						window.location = '/demo/index';
					}, 3000);
				}
			}

		});


		return false;
	}
}


