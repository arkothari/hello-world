package com.abhinav.datafeed.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abhinav.datafeed.dto.Sheet;
import com.abhinav.datafeed.dto.SheetDiff;
import com.abhinav.datafeed.repository.SheetRepository;

public class SheetService {
	
	@Autowired
	private SheetRepository sheetDAO;
	
	private static final String DIFF_ADD = "a";
	private static final String DIFF_EDIT = "e";
	private static final String DIFF_DELETE = "d";

	public void init() {
		System.out.println("init");
	}
	
	public Sheet getSheet(String id){
		return sheetDAO.find(id, false);
	}
	
	public List<Sheet> getSheetVersions(String sheetId){
		List<Sheet> versions = new ArrayList<Sheet>();
		versions.add(sheetDAO.findCurrentMetadata(sheetId));
		versions.addAll(sheetDAO.findAllVersionsMetadata(sheetId));
		return versions;
	}
	
	public Sheet getSheetMetadata(String id){
		return sheetDAO.find(id, false);
	}
	
	public Sheet getSheetVersionsMetadata(String id){
		return sheetDAO.find(id, false);
	}
	
	public List<Sheet> getAllSheets(){
		return sheetDAO.findAllOnlyMetadata();
	}
	
	public String saveSheet(Sheet sheet){
		System.out.println("hello");
		sheet.setData(Arrays.copyOf(sheet.getData(), sheet.getData().length - 1));
		sheetDAO.save(sheet);
		return null;
	}
	
	public SheetDiff determineChanges(SheetDiff sheetDiff){
		//559deb3dd4c6da20f74e6fc0-559deb3dd4c6da20f74e6fc0
		Sheet baseSheet = sheetDAO.find(sheetDiff.getBaseSheetId(), false);
		Sheet compareSheet = sheetDAO.find(sheetDiff.getCompareSheetId(), true);
		
		String[][] baseData = baseSheet.getData();
		String[][] compareData = compareSheet.getData();
		
		List<String[]> changeSet = new ArrayList<String[]>();
		int rowCount = (baseData.length > compareData.length) ? compareData.length : baseData.length;
		int colCount = baseData[0].length;
		
		for(int row = 0 ; row < rowCount ; row++){
			for(int col = 0 ; col < colCount ; col++){
				if(!baseData[row][col].equals(compareData[row][col])){
					changeSet.add(new String[]{row+"", col+"", compareData[row][col], DIFF_EDIT});
				}
			}
		}
		
		if(baseData.length > compareData.length){
			changeSet.add(new String[]{"["+(compareData.length+1)+","+baseData.length+"]", "-1", "", DIFF_ADD});
		}else if(baseData.length < compareData.length){
			changeSet.add(new String[]{"["+(baseData.length+1)+","+compareData.length+"]", "-1", "", DIFF_DELETE});
		}
		
		sheetDiff.setChangeSet(changeSet);
		
		return sheetDiff;
	
	}
	
	/*private static void printCollection(FindIterable<Document> collection){
		
		for(Document item:collection){
			System.out.println(item);
		}
	}*/
}
