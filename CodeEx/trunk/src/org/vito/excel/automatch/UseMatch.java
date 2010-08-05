package org.vito.excel.automatch;

import java.util.ArrayList;
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
		WritableWorkbook allPasheets = null;		
		BJBook = MatchOperation.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx" +
					"/src/org/vito/resources/automatch/BJ_parts.xls");
		PABook = MatchOperation.readWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/Asset_0805.xls");
		allPasheets = MatchOperation.makeWorkbook("D:/MyDocs/eclipse/experiment/CodeEx/" +
					"src/org/vito/resources/automatch/output.xls");		 
		
		WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9);
		WritableCellFormat format = new WritableCellFormat(arial9);
		
		List BJLCList = MatchOperation.getBJLCList(BJBook, Constants.BJPARTS_SHEETNAME);
		match.setBJLCList(BJLCList);
		
		List hdd = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.HDD_SHEETNAME));
		List odd = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.ODD_SHEETNAME));
		List mem = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.MEM_SHEETNAME));
		List fdd = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.FDD_SHEETNAME));
		List nv = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.NV_SHEETNAME));
		List ati = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.ATI_SHEETNAME));
		List kb = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.KB_SHEETNAME));
		List mouse = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.MOUSE_SHEETNAME));
		List cardreader = match.compareBJPN(
				MatchOperation.getBJPNList(PABook, Constants.CARDREADER_SHEETNAME));
		List pci = match.compareBJPN(
				MatchOperation.getBJPNList(PABook, Constants.PCI_SHEETNAME));
		List connector = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.CONNECTOR_SHEETNAME));
		List other = match.compareBJPN(MatchOperation.getBJPNList(PABook, Constants.OTHER_SHEETNAME));
		
		List allMatched = new ArrayList();
		allMatched = MatchOperation.copyList(cardreader, allMatched);
		allMatched = MatchOperation.copyList(pci, allMatched);
		allMatched = MatchOperation.copyList(connector, allMatched);	
		allMatched = MatchOperation.copyList(hdd, allMatched);
		allMatched = MatchOperation.copyList(odd, allMatched);
		allMatched = MatchOperation.copyList(mem, allMatched);
		allMatched = MatchOperation.copyList(fdd, allMatched);
		allMatched = MatchOperation.copyList(nv, allMatched);
		allMatched = MatchOperation.copyList(ati, allMatched);
		allMatched = MatchOperation.copyList(kb, allMatched);
		allMatched = MatchOperation.copyList(mouse, allMatched);
		allMatched = MatchOperation.copyList(other, allMatched);
		
		
		{//使用方式2. 所有进行匹配的PA表, 最后只输出2个新表, 分别是BJ表和PA表中的对应项.
			match.setMatchedBJSheet(allPasheets.createSheet("MatchedBJ", 0));
			match.setMatchedPASheet(allPasheets.createSheet("MatchedPA", 1));
			match.setUnmatchedBJSheet(allPasheets.createSheet("UnmatchedBJ", 2));
			match.makeSheetHead(BJBook, format, Constants.BJ_SHEET);
			match.makeSheetHead(PABook, format, Constants.PA_SHEET);
			match.makeSheetHead(BJBook, format, Constants.BJ_UNMATCHED_SHEET);
			
			match.fillOutputSheets(BJBook, PABook, hdd, format, Constants.HDD_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, odd, format, Constants.ODD_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, mem, format, Constants.MEM_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, fdd, format, Constants.FDD_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, nv, format, Constants.NV_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, ati, format, Constants.ATI_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, kb, format, Constants.KB_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, mouse, format, Constants.MOUSE_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, cardreader, format, 
					Constants.CARDREADER_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, pci, format, 
					Constants.PCI_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, connector, format, Constants.CONNECTOR_SHEETNAME);
			match.fillOutputSheets(BJBook, PABook, other, format, Constants.OTHER_SHEETNAME);
			
			List unMatchedBJLC = MatchOperation.getUnmatchedBJLC(BJBook, allMatched);
			match.makeUnmatchedBJSheet(BJBook, unMatchedBJLC, format);
			
			
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
