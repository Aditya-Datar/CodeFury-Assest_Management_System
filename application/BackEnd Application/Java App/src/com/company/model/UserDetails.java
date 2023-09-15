package com.company.model;

public class UserDetails {

	private int uniqueId;
	private String userName;
	private String userRole;
	private Long userTelephone;
	private String userEmail;
	private String userPass;
	
	public UserDetails(int uniqueId, String userName, String userRole, Long userTelephone, String userEmail, String userPass) {
		super();
		this.uniqueId = 0;
		this.userName = userName;
		this.userRole = userRole;
		this.userTelephone = userTelephone;
		this.userEmail = userEmail;
		this.userPass = userPass;
	}
	
	public UserDetails(){
		
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId2) {
		this.uniqueId = uniqueId2;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Long getUserTelephone() {
		return userTelephone;
	}
	public void setUserTelephone(Long userTelephone) {
		this.userTelephone = userTelephone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	@Override
	public String toString() {
		return "User [uniqueId=" + uniqueId + ", userName=" + userName + ", userRole=" + userRole + ", userTelephone="
				+ userTelephone + ", userEmail=" + userEmail + ", userPass=" + userPass + "]";
	}
	
}

