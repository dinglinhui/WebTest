package com.dlh.galaxy.web.admin.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class ValidateModel {

	@NotEmpty(message = "{name.not.empty}")
	private String name;
	@Range(min = 0, max = 150, message = "{age.not.inrange}")
	private String age;
	@NotEmpty(message = "{email.not.empty}")
	@Email(message = "{email.not.correct}")
	private String email;

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public String getAge() {
		return this.age;
	}

	public String getEmail() {
		return this.email;
	}

}
