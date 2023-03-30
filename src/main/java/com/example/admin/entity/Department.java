package com.example.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long DeptId;
	private String DeptName;
	private String DeptLocation;
	

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getDeptLocation() {
		return DeptLocation;
	}

	public void setDeptLocation(String deptLocation) {
		DeptLocation = deptLocation;
	}

	@Override
	public String toString() {
		return "Department [DeptId=" + DeptId + ", DeptName=" + DeptName + ", DeptLocation=" + DeptLocation + "]";
	}

	


}
