package org.vito.excel;

import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

public class UseMatchPN {
	
	public static void main(String []args){
		MatchPN_1 match = new MatchPN_1();
		
		Workbook BJBook = null;
		Workbook PABook = null;
		WritableWorkbook perPAsheetView = null;
		WritableWorkbook allPasheets = null;
		
		try {
			BJBook = match.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx" +
					"/src/org/vito/resources/automatch/BJ_parts.xls");
			PABook = match.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/Asset_0729_BJ2.xls");
			perPAsheetView = match.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output1.xls");
			allPasheets = match.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output.xls");
		} catch (Exception e) {			
			e.printStackTrace();
		} 	
		
		List BJLCList = match.getBJLCList(BJBook, Constants.BJPARTS_SHEET_NAME);
		match.setBJLCList(BJLCList);
		
//		Map pci = match.getPNs4PASheet(PABook, Constants.PCI_SHEET_NAME);
		
//		List cr = match.getPNRecords(PABook, Constants.CARDREADER_SHEET_NAME);
//		List pci = match.getPNRecords(PABook, Constants.PCI_SHEET_NAME);
		
//		List matchedCRList = match.compare(CRPN);
//		List matchedCRList = match.compare(cr);
//		List matchedPciList = match.compare(pci);		
		
		List matchedCRList = match.compareBJPN(
				match.getBJPNList(PABook, Constants.CARDREADER_SHEET_NAME));
		List matchedPciList = match.compareBJPN(
				match.getBJPNList(PABook, Constants.PCI_SHEET_NAME));
		
		System.out.println(matchedCRList.size());
		System.out.println(matchedPciList.size());
		
		WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9);
		WritableCellFormat format = new WritableCellFormat(arial9);
		
//		match.setMatchedBJSheet(output.createSheet("MatchedBJSheet", 0));
//		match.setMatchedPASheet(output.createSheet("MatchedPASheet", 1));
		
		{//使用方式1. 对单个PA表输出两个新表, 分别是BJ表和PA表中的匹配对应项
			
			match.setMatchedBJSheet(perPAsheetView.createSheet("MatchedBJ_CR", 0));
			match.setMatchedPASheet(perPAsheetView.createSheet("MatchedPA_CR", 1));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.output2SheetsPerPASheet(BJBook, PABook, matchedCRList, format, 
					Constants.CARDREADER_SHEET_NAME);
			
			match.setMatchedBJSheet(perPAsheetView.createSheet("MatchedBJ_PCI", 2));
			match.setMatchedPASheet(perPAsheetView.createSheet("MatchedPA_PCI", 3));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.output2SheetsPerPASheet(BJBook, PABook, matchedPciList, format, 
					Constants.PCI_SHEET_NAME);
			
			match.write(perPAsheetView);
		}
		
		{//使用方式2. 所有进行匹配的PA表, 最后只输出2个新表, 分别是BJ表和PA表中的对应项.
			match.setMatchedBJSheet(allPasheets.createSheet("MatchedBJ", 0));
			match.setMatchedPASheet(allPasheets.createSheet("MatchedPA", 1));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.fillOutputSheets(BJBook, PABook, matchedCRList, format, 
					Constants.CARDREADER_SHEET_NAME);
			match.fillOutputSheets(BJBook, PABook, matchedPciList, format, 
					Constants.PCI_SHEET_NAME);
			
			match.write(allPasheets);
		}
		
		
//		match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
//		match.makeSheetHead(PABook, format, Constants.PA_SHEET);
		
//		match.fillSheetContents(BJBook, PABook, matchedCRList, format, 
//				Constants.CARDREADER_SHEET_NAME);
//		match.fillSheetContents(BJBook, PABook, matchedPciList, format, 
//				Constants.PCI_SHEET_NAME);
		
//		match.makeOutputSheets(BJBook,PABook,output, matchedCRList, Constants.CARDREADER_SHEET_NAME);
//		match.makeOutputSheets(BJBook, PABook, output, matchedCRList, Constants.CARDREADER_SHEET_NAME);
//		match.makeOutputSheets(BJBook,PABook,output, 
//				matchedPciList, Constants.PCI_SHEET_NAME);
		
		
		
		try {			
			BJBook.close();
			PABook.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
	}	

}
