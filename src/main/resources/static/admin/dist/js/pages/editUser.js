/**
 * 
 */

let coverPreview = document.getElementById('coverPreview');


let cover = document.getElementById('cover');

coverPreview.addEventListener('click', _ => cover.click());

cover.addEventListener("change", _ => {
	let file = cover.files[0];
	let reader = new FileReader();
	reader.onload = function() {
		coverPreview.src = reader.result;
	}
	reader.readAsDataURL(file);
});


function userValidation() {

	sessionStorage.clear();
	var fullName = $("#fullName").val();
	var userImg = $("#userImg").val();
	var userID = $("#UUserId").val();



	sessionStorage.setItem("loginUserName", fullName);
	sessionStorage.setItem("loginUserImg", userImg);
	sessionStorage.setItem("userId", userID);

}
