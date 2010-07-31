package org.vito.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelTest {

	public static void main(String args[]){
		File f = new File("D:/MyDocs/eclipse/experiment/CodeEx/src/org/vito/resources/Book1.xls");
		Workbook workbook = null;	
		try {
			workbook = Workbook.getWorkbook(f);			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
		Sheet sheet = workbook.getSheet("Sheet1"); 
		
		Cell b2 = sheet.getCell(1, 1);
		String b2Content = b2.getContents();
		System.out.println(b2Content);
//		
//		Cell b3 = sheet.getCell("B3");
//		String b3Content = b3.getContents();
//		System.out.println(b3Content);
		
		int PNCount = sheet.getRows();
		
		Cell []partNumberCells = sheet.getColumn(1);  //
		String []partNumbers = new String[PNCount];		
		
		List PNList = new ArrayList();
		for(int i = 0; i < partNumbers.length; i++){
			PNList.add(partNumbers[i]);
		}
		
		///
		List matchedPNs = new ArrayList(); //匹配的所有编号
		List []matchedRowsInSheet1;  //从工作表1中抽取的匹配编号所在行包含的内容
		List []matchedRowsInSheet2;  
		
		Sheet matchedExportSheet1; //将表1所有匹配的行导出的新表
		Sheet matchedExportSheet2; 
		Sheet summaryExportSheet;  //统计相关信息的导出新表
		
		Workbook exportedBook;  //导出的工作薄
		///
	
		for(int i=0;i<PNCount;i++){			
			partNumbers[i]=partNumberCells[i].getContents();
			System.out.println(partNumbers[i]);
			
//			if(partNumbers[i].equals("00001123338AD")){  //找匹配
//				matchedPNs.add(partNumbers[i]);
//				Cell []currentRowCells = sheet.getRow(i);
//				String []currentRow = new String[currentRowCells.length];
//				for(int j=0;j<currentRowCells.length;j++){
//					currentRow[j] = currentRowCells[j].getContents();
//					System.out.println(currentRow[j]);
//				}
//			}
			
		}	
	
		
		
	}

}
