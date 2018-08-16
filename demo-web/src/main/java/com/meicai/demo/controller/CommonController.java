package com.meicai.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {
	
	@RequestMapping("error")
	public String error() {
		return "error/error";
	}

}
