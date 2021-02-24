package com.example.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordForm {

	@NotNull
	private Long id;
	@NotBlank(message = "El campo no puede estar vacio")
	private String currentPassword;
	@NotBlank(message = "El campo no puede estar vacio")
	private String newPassword;
	@NotBlank(message = "El campo no puede estar vacio")
	private String confirmPassword;

	public ChangePasswordForm() {
	}

	public ChangePasswordForm(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

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

}
