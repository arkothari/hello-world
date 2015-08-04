package com.abhinav.datafeed.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.abhinav.datafeed.dto.Sheet;

public class SheetRepository {

    private MongoOperations mongoOps;
    private static final String SHEET_COLLECTION = "SHEET";
    private static final String SHEET_COLLECTION_AUDIT = "SHEET_AUDIT";
    

	public SheetRepository(MongoOperations mongoOps) {
		super();
		this.mongoOps = mongoOps;
	}


	public Sheet find(String id, boolean audit){
		return mongoOps.findById(id, Sheet.class, !audit?SHEET_COLLECTION:SHEET_COLLECTION_AUDIT);
	}
	
	public List<Sheet> findAllOnlyMetadata(){

		Query query = new Query();
		query.fields().exclude("data");
		
		return mongoOps.find(query,Sheet.class, SHEET_COLLECTION);
	}
	
	public Sheet findCurrentMetadata(String sheetId){

		//BasicQuery findQuery = new BasicQuery("{publicId : '"+sheetId+"'}");
		Query findQuery = new Query();
		findQuery.addCriteria(Criteria.where("publicId").is(sheetId));
		findQuery.fields().exclude("data");
		List<Sheet> sheet = mongoOps.find(findQuery,Sheet.class, SHEET_COLLECTION);
		if(sheet.size() > 0){
			return sheet.get(0);
		}else{
			return null;
		}	 
	}
	
	public List<Sheet> findAllVersionsMetadata(String sheetId){

		Query findQuery = new Query();
		findQuery.addCriteria(Criteria.where("publicId").is(sheetId));
		findQuery.with(new Sort(Sort.Direction.DESC, "version"));
		findQuery.fields().exclude("data");
		
		return mongoOps.find(findQuery,Sheet.class, SHEET_COLLECTION_AUDIT);
	}
	
	public void save(Sheet sheet){
		//mongoOps.save(sheet, "SHEET_COL");
		BasicQuery findQuery = new BasicQuery("{publicId : '"+sheet.getPublicId()+"'}");
		List<Sheet> existingSheet = mongoOps.find(findQuery, Sheet.class, SHEET_COLLECTION);
		if(existingSheet.size() > 0){
			Sheet currObject = existingSheet.get(0);
			System.out.println("existing record");
			int currentVersion = Integer.parseInt(currObject.getVersion());
			sheet.setVersion((currentVersion+1)+"");
			sheet.setId(currObject.getId());
			currObject.setId(currObject.getId()+"-"+currentVersion);
			//using insert to save instead of save to error out in case the record exists with the same id
			mongoOps.insert(currObject,SHEET_COLLECTION_AUDIT);
			
			mongoOps.save(sheet,SHEET_COLLECTION);
		}else{
			System.out.println("new object");
			sheet.setVersion("0");
			mongoOps.insert(sheet,SHEET_COLLECTION);
		}
		
		/*List<Sheet> allCurrentSheets = mongoOps.findAll(Sheet.class,SHEET_COLLECTION);
		for(Sheet sh:allCurrentSheets){
			System.out.println("CURRENT > "+sh.getPublicId()+" > "+sh.getId());
		}

		List<Sheet> allArchivedSheets = mongoOps.findAll(Sheet.class,SHEET_COLLECTION_AUDIT);
		for(Sheet sh:allArchivedSheets){
			System.out.println("ARCHIVE > "+sh.getPublicId()+" > "+sh.getId());
		}*/
	}

}
