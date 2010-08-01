package org.vito.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelTest {

	public static void main(String args[]){
		File f = new File("D:/MyDocs/eclipse/experiment/CodeEx/src/org/vito/resources/Book1.xls");
		Workbook workbook1 = null;	
		try {
			workbook1 = Workbook.getWorkbook(f);			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
		Sheet sheet = workbook1.getSheet("Sheet1"); 
		
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
		workbook1.close();
		
		///
		
		WritableWorkbook workbook2 = null;
		f = new File("D:/MyDocs/eclipse/experiment/CodeEx/src/org/vito/resources/output.xls");
		try {
			workbook2 = Workbook.createWorkbook(f);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		WritableSheet matchedBJSheet = workbook2.createSheet("MatchedBJSheet", 0); 
		
		WritableFont arial9font = new WritableFont(WritableFont.ARIAL, 9); 
		WritableCellFormat arial9format = new WritableCellFormat (arial9font); 
		WritableCellFormat wrapFormat = new WritableCellFormat(arial9font);		
		WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.INTEGER); 
		WritableCellFormat floatFormat = new WritableCellFormat (NumberFormats.FLOAT); 
		WritableCellFormat textFormat = new WritableCellFormat(NumberFormats.TEXT);
		NumberFormat fivedps = new NumberFormat("#.#####"); 
		NumberFormat fivedpa = new NumberFormat("0.00000");
		WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps); 
		WritableCellFormat fivedpaFormat = new WritableCellFormat(fivedpa);
		
		try {
			wrapFormat.setWrap(true);
			textFormat.setWrap(true);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}

		
		Label label = new Label(0, 2, "A label record"); //第一列,第三行.A3
		Label label2 = new Label(1,0, "Arial 9 point label", arial9format); //B1	
		Label label3 = new Label(2, 0, "Another Arial 9 point label", arial9format); //C1
		Label label4 = new Label(0,0,"label in arial 9, wrapped",wrapFormat);
		Label label5 = new Label(2,0,"00003232A32",wrapFormat);  // 以字符串显示吧
		Number number1 = new Number(2,4,0004,textFormat);  // faint ...
		Number number = new Number(3, 4, 3.1459);  //第四列,第五行.D5	
		Number number2 = new Number(4,4,4.2352,integerFormat);
		Number number3 = new Number(5,4,3.443500,floatFormat);
		Number number4 = new Number(6,4,3.443500,fivedpsFormat);
		Number number5 = new Number(7,4,3.443500,fivedpaFormat);
		
		try {
			matchedBJSheet.addCell(label);
			matchedBJSheet.addCell(number); 
			matchedBJSheet.addCell(label2);
			matchedBJSheet.addCell(label3);
			matchedBJSheet.addCell(label4);
			matchedBJSheet.addCell(label5);
			matchedBJSheet.addCell(number2);
			matchedBJSheet.addCell(number1);
			matchedBJSheet.addCell(number3);
			matchedBJSheet.addCell(number4);
			matchedBJSheet.addCell(number5);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} 
		
		try {
			workbook2.write();
			workbook2.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		///
		
		
		
	}

}
