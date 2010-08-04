package org.vito.excel.automatch;

import java.util.List;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

public class UseMatch {
	
	public static void main(String[] args) {
		MatchPN match = new MatchPN();
		
		Workbook BJBook = null;
		Workbook PABook = null;
		WritableWorkbook perPAsheetView = null;
		WritableWorkbook allPasheets = null;		
		BJBook = MatchOperation.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx" +
					"/src/org/vito/resources/automatch/BJ_parts.xls");
		PABook = MatchOperation.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/Asset_0729_BJ2.xls");
		perPAsheetView = MatchOperation.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output1.xls");
		allPasheets = MatchOperation.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output.xls");		 
		
		WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9);
		WritableCellFormat format = new WritableCellFormat(arial9);
		
		List BJLCList = MatchOperation.getBJLCList(BJBook, Constants.BJPARTS_SHEETNAME);
		match.setBJLCList(BJLCList);
		
		List cardreader = match.compareBJPN(
				MatchOperation.getBJPNList(PABook, Constants.CARDREADER_SHEETNAME));
		List pci = match.compareBJPN(
				MatchOperation.getBJPNList(PABook, Constants.PCI_SHEETNAME));
		List connector = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.CONNECTOR_SHEETNAME));
		
//		System.out.println(connector.size());		
		{//使用方式1. 对单个PA表输出两个新表, 分别是BJ表和PA表中的匹配对应项
			
//			match.setMatchedBJSheet(perPAsheetView.createSheet("MatchedBJ_CR", 0));
//			match.setMatchedPASheet(perPAsheetView.createSheet("MatchedPA_CR", 1));
//			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
//			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
//			match.output2SheetsPerPASheet(BJBook, PABook, matchedCRList, format, 
//					Constants.CARDREADER_SHEETNAME);
//			
//			match.setMatchedBJSheet(perPAsheetView.createSheet("MatchedBJ_PCI", 2));
//			match.setMatchedPASheet(perPAsheetView.createSheet("MatchedPA_PCI", 3));
//			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
//			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
//			match.output2SheetsPerPASheet(BJBook, PABook, matchedPciList, format, 
//					Constants.PCI_SHEETNAME);
//			
//			match.write(perPAsheetView);
		}
		
		{//使用方式2. 所有进行匹配的PA表, 最后只输出2个新表, 分别是BJ表和PA表中的对应项.
			match.setMatchedBJSheet(allPasheets.createSheet("MatchedBJ", 0));
			match.setMatchedPASheet(allPasheets.createSheet("MatchedPA", 1));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			
			match.fillOutputSheets(BJBook, PABook, cardreader, format, 
					Constants.CARDREADER_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, pci, format, 
					Constants.PCI_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, connector, format, Constants.CONNECTOR_SHEETNAME);
			
			
			match.write(allPasheets);
		}		
		
		try {			
			BJBook.close();
			PABook.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}

}
