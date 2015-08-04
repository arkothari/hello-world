package com.abhinav.datafeed.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.abhinav.datafeed.dto.Sheet;
import com.abhinav.datafeed.util.XLSXUtil;

public class ImportExportService {

	@Autowired
	private XLSXUtil xlsxUtil;
	
	@Autowired
	private SheetService sheetService;
	
	public String importXLSX(Sheet sheet,InputStream is){
		System.out.println("service import");
		String[][] data = xlsxUtil.importXLSX(is);
		sheet.setData(data);;
		System.out.println(data.length);
		return null;
		//return sheetService.saveSheet(xlsxUtil.importXLSX());
	}
}
