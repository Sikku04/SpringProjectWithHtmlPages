package com.example.admin.entity;

public class ChangePassword {

	private String newPassword;
	private String confirmPassword;
	private String otp;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "ChangePassword [newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + ", otp=" + otp
				+ "]";
	}

}
