package com.abhinav.datafeed.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.abhinav.datafeed.dto.Response;
import com.abhinav.datafeed.dto.Sheet;
import com.abhinav.datafeed.service.ImportExportService;

@Controller
@RequestMapping("/import")
public class ImportController {

	@Autowired
	private ImportExportService importService;
	
	@RequestMapping(value="/import")
	public @ResponseBody Response importXLSX(MultipartHttpServletRequest request, HttpServletResponse response) {
		System.out.println("IMPORTING");
		Sheet sheet = new Sheet();
		sheet.setPublicId(request.getParameter("sheetName"));
		sheet.setName(request.getParameter("sheetName"));
		System.out.println(request.getParameter("sheetName")+"*****");
		Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        
        while(itr.hasNext()){
        	InputStream is = null;
            mpf = request.getFile(itr.next()); 
            try {
            	is = mpf.getInputStream();
				importService.importXLSX(sheet,is);
				is.close();
			} catch (IOException e) {
				System.out.println(e);
			}finally{
				
			}
            System.out.println(mpf.getOriginalFilename() +" uploaded! ");
            
        }
		Response res = new Response("success","uploaded");
		return res;
	}
}
