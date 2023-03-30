package com.example.admin.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.admin.util.EnumNotification;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long studentId;
	private Long teacherId;
	private Long staffId;
	private String message;
	private String CreationDate;
	private String updateDate;

	@Enumerated(EnumType.STRING)

	private EnumNotification type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(String creationDate) {
		CreationDate = creationDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public EnumNotification getType() {
		return type;
	}

	public void setType(EnumNotification type) {
		this.type = type;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Notification(Long studentId, Long teacherId, Long staffId, String message, String creationDate,
			String updateDate, EnumNotification type) {
		super();
		this.studentId = studentId;
		this.teacherId = teacherId;
		this.staffId = staffId;
		this.message = message;
		CreationDate = creationDate;
		this.updateDate = updateDate;
		this.type = type;
	}

}
