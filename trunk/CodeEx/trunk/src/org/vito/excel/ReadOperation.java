package org.vito.excel;

import java.io.File; 
import java.io.IOException;
import java.util.Date; 
import jxl.*; 
import jxl.read.biff.BiffException;

public class ReadOperation {
	
	public ReadOperation(){
		
	}
	
	public void readFile(String filePath){
//		readFile(filePath,null);
		File file = new File(filePath);
	}
	
//	public void readFile(File file){
//		readFile(null,file);
//	}
	
	private void readFile(File f) {
//		File file = null;
//		if(filePath != null && f == null){
//			file = new File(filePath);
//		}else if(filePath == null && f != null){
//			file = f;
//		}
		
		Workbook workbook = null;	
		try {
			workbook = Workbook.getWorkbook(f);
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
		Sheet sheet = workbook.getSheet("Sheet1"); 
		Cell b2 = sheet.getCell(1, 1);
		String b2Content = b2.getContents();
	}	
	
	
	
	
}
