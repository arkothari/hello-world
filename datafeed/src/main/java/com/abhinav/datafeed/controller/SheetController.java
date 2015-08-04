package com.abhinav.datafeed.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abhinav.datafeed.dto.Response;
import com.abhinav.datafeed.dto.Sheet;
import com.abhinav.datafeed.dto.SheetDiff;
import com.abhinav.datafeed.service.SheetService;


@Controller
@RequestMapping("/sheet")
public class SheetController {
	
	@Resource(name="sheetService")
	private SheetService sheetService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET, produces="application/json; charset=utf-8")
	public @ResponseBody Sheet getSheetData(@PathVariable("id") int id) {
		int totalRows = 10;
		String[][] rows = new String[totalRows][];
		System.out.println("coming - "+id);
	    Sheet sheet = new Sheet();
	    sheet.setName("test");
	    sheet.setPublicId("001");
	    sheet.setVersion("4");
	    sheet.setCrtBy("abhinav");
	    sheet.setCrtTS(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date()));
	    sheet.setComments("test sheet");
	    for(int i=0;i<totalRows;i++){
	    	if(i % 17 == 0){
	    		String[] row = {"team4", "Tampa", "100", "170", "17000","abhinav@gmail.com","10/01/2014"};
	    		rows[i]=row;
	    	}else if(i % 4 == 0){
	    		String[] row = {"team3", "Denver", "10", "22", "220","abhinav@gmail.com","10/02/2014"};
	    		rows[i]=row;
	    	}else if(i % 3 == 0){
	    		String[] row = {"team2", "Austin", "12", "12", "144","data.txt","10/11/2014"};
	    		rows[i]=row;
	    	}else if(i % 2 == 0){
	    		String[] row = {"team1", "New York", "100", "22", "2200","abhinav@gmail.com","10/01/2014"};
	    		rows[i]=row;
	    	}else {
	    		String[] row = {"team5", "New York", "20", "17", "3400","abhi@gmail.com","10/01/2014"};
	    		rows[i]=row;
	    	}
	    	
	    }
	    sheet.setData(rows);
	    return sheet;
	}
	
	
	@RequestMapping(value="/list", method = RequestMethod.GET, produces="application/json; charset=utf-8")
	public @ResponseBody List<Sheet> listSheets() {
		System.out.println("LIST"); 
		return sheetService.getAllSheets();
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST, produces="application/json; charset=utf-8")
	public @ResponseBody Response saveSheetData(@RequestBody Sheet sheet) {
		System.out.println("PUT");
		//System.out.println(data.toString());
		//System.out.println(sheet.getName());
		//System.out.println(HtmlUtils.htmlUnescape(sheet.getData()[0][1]));
		//System.out.println(sheet.getData().length);
		sheet.setId(sheet.getName());
		sheet.setPublicId(sheet.getName());
		sheetService.saveSheet(sheet);
		//List<Object[]> rows = data.getData();
		Response response = new Response("success","saved");
		return response;
	}
	
	@RequestMapping(value="/versions/{id}", method = RequestMethod.GET, produces="application/json; charset=utf-8")
	public @ResponseBody List<Sheet> listVersions(@PathVariable("id") String sheetId) {
		System.out.println("VERSIONS");
		return sheetService.getSheetVersions(sheetId);
	}
	
	@RequestMapping(value="/compare/{id}/{v1}/{v2}", method = RequestMethod.GET, produces="application/json; charset=utf-8")
	public @ResponseBody SheetDiff compareSheets(@PathVariable("id") String sheetId, @PathVariable("v1") String baseVersion, @PathVariable("v2") String compareVersion) {
		System.out.println("COMPARE "+ (baseVersion.equals("-1")?sheetId:sheetId+"-"+baseVersion));
		SheetDiff sheetDiff = new SheetDiff();
		sheetDiff.setBaseSheetId(baseVersion.equals("-1")?sheetId:sheetId+"-"+baseVersion);
		sheetDiff.setCompareSheetId(compareVersion.equals("-1")?sheetId:sheetId+"-"+compareVersion);
		sheetService.determineChanges(sheetDiff);
		return sheetDiff;
	}
}
