package com.dlh.galaxy.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login() {
		return "login";
	}

}
