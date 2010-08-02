package org.vito.excel;

import java.util.List;
import java.util.Map;

import jxl.Workbook;
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
		
//		Map CRPN = match.getPNs4PASheet(PABook, Constants.CARDREADER_SHEET_NAME);
//		Map pci = match.getPNs4PASheet(PABook, Constants.PCI_SHEET_NAME);
		List pci = match.getPNRecords(PABook, Constants.PCI_SHEET_NAME);
		
//		List matchedCRList = match.compare(CRPN);
		List matchedPciList = match.compare(pci);
		
//		System.out.println(matchedCRList.size());
		System.out.println(matchedPciList.size());
		
		try {
//			match.makeOutputSheets(BJBook,PABook,output, matchedCRList, Constants.CARDREADER_SHEET_NAME);
			match.makeOutputSheets(BJBook,PABook,output, 
					matchedPciList, Constants.PCI_SHEET_NAME);
//			match.write(output);
			
			BJBook.close();
			PABook.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}	

}
