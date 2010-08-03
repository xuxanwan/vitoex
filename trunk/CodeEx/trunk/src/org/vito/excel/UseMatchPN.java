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
		WritableWorkbook output = null;
		
		try {
			BJBook = match.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx" +
					"/src/org/vito/resources/automatch/BJ_parts.xls");
			PABook = match.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/Asset_0729_BJ2.xls");
			output = match.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output.xls");
		} catch (Exception e) {			
			e.printStackTrace();
		} 	
		
		List BJLCList = match.getBJLCList(BJBook, Constants.BJPARTS_SHEET_NAME);
		match.setBJLCList(BJLCList);
		
//		Map pci = match.getPNs4PASheet(PABook, Constants.PCI_SHEET_NAME);
		
		List cr = match.getPNRecords(PABook, Constants.CARDREADER_SHEET_NAME);
		List pci = match.getPNRecords(PABook, Constants.PCI_SHEET_NAME);
		
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
		
		{//使用方式1.
			
			match.setMatchedBJSheet(output.createSheet("MatchedBJ_CR", 0));
			match.setMatchedPASheet(output.createSheet("MatchedPA_CR", 1));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.output2SheetsPerPASheet(BJBook, PABook, matchedCRList, format, 
					Constants.CARDREADER_SHEET_NAME);
			
			match.setMatchedBJSheet(output.createSheet("MatchedBJ_PCI", 2));
			match.setMatchedPASheet(output.createSheet("MatchedPA_PCI", 3));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.output2SheetsPerPASheet(BJBook, PABook, matchedPciList, format, 
					Constants.PCI_SHEET_NAME);
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
		
		match.write(output);
		
		try {			
			BJBook.close();
			PABook.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
	}	

}
