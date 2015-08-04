package com.abhinav.datafeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abhinav.datafeed.dto.Response;
import com.abhinav.datafeed.service.ShareService;

@Controller
@RequestMapping("/share")
public class ShareController {

	@Autowired
	private ShareService shareService;
	
	@RequestMapping(value="/share", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	public @ResponseBody Response shareSheet() {
		System.out.println("PUT");
		shareService.shareSheet();
		Response response = new Response("success","sent");
		return response;
	}
}
