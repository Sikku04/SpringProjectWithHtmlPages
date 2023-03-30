package com.example.admin.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String otp;
	private LocalDateTime otpCreationDate;
	private String picUrl;
	private boolean isActive;

	@Lob
	private String photos;

	private String Phone;
	private String Street;
	private String City;
	private String State;
	private String ZipCode;

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpCreationDate() {
		return otpCreationDate;
	}

	public void setOtpCreationDate(LocalDateTime otpCreationDate) {
		this.otpCreationDate = otpCreationDate;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", name=" + name + ", email=" + email + ", password=" + password + ", otp=" + otp
				+ ", otpCreationDate=" + otpCreationDate + ", picUrl=" + picUrl + ", isActive=" + isActive + ", photos="
				+ photos + ", Phone=" + Phone + ", Street=" + Street + ", City=" + City + ", State=" + State
				+ ", ZipCode=" + ZipCode + "]";
	}

}
