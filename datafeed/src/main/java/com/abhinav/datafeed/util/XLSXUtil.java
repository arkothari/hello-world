package com.abhinav.datafeed.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.abhinav.datafeed.dto.Sheet;

public class XLSXUtil {

	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public String[][] importXLSX(InputStream is){
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			if(rowIterator.hasNext()){
				rowIterator.next();
			}
			String[][] rows = new String[sheet.getPhysicalNumberOfRows()-1][];
			int currRow = 0;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String[] dataRow = new String[row.getPhysicalNumberOfCells()];
				//String[] row = {"team1", "New York", "100", "22", "2200","abhinav@gmail.com","10/01/2014"};
	            Iterator<Cell> cellIterator = row.cellIterator();
	            int cellNum=0;
	            while(cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                String cellVal="";
	                switch (cell.getCellType()){
	                	case Cell.CELL_TYPE_STRING:
	                		cellVal = cell.getStringCellValue();
	                		break;
	                	case Cell.CELL_TYPE_NUMERIC:
	                		cellVal =  ((DateUtil.isCellDateFormatted(cell))?df.format(cell.getDateCellValue()):cell.getNumericCellValue()+"");
	                		break;
	                	case Cell.CELL_TYPE_FORMULA:
	                		cellVal = cell.getCellFormula();
	                		break;
	                }
	                dataRow[cellNum++] = cellVal;
	                //System.out.println(cellVal);
	            }
	            rows[currRow++]=dataRow;
			}
			workbook.close();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
	}
}
