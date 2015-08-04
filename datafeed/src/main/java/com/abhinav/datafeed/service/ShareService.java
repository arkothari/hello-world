package com.abhinav.datafeed.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.abhinav.datafeed.util.EmailUtil;

public class ShareService {

	@Autowired
	private EmailUtil emailUtil;
	
	public void shareSheet(){
		emailUtil.sendEmail(null, "Check it", "check this email");
	}
}
