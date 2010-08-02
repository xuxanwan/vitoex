package org.vito.excel;

import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.*; 
import jxl.read.biff.BiffException;

public class MatchPN_1 {
	
	public MatchPN_1(){
		
	}
	
	public Workbook readWorkbook(String filePath) throws BiffException, IOException{
		File file = new File(filePath);
		return readWorkbook(file);
	}
	
	public Workbook readWorkbook(File f) throws IOException, BiffException {		
		Workbook workbook = null;		
		workbook = Workbook.getWorkbook(f);		
		return workbook;
	}	
	
	/**
	 * 得到BJ部件表中的所有LC编码的列表.
	 * @param BJBook
	 * @param sheetName
	 * @return
	 */
	public List getBJLCList(Workbook BJBook, String sheetName){
		Sheet BJSheet = BJBook.getSheet(sheetName);
		List BJLCList = new ArrayList();
		Cell []BJLCCells = BJSheet.getColumn(Constants.BJLC_COLUMN_INDEX); 
		int BJSheetRows = BJSheet.getRows();
		for(int i=Constants.BJ_STEPPING ; i<BJSheetRows; i++){  //越过该列列名,即越过1行
//			String readOut = BJLCCells[i].getContents();
//			if(readOut != null && !readOut.equals("")){
//				BJLCList.add(readOut);
//			}
			add2List(BJLCList, BJLCCells[i].getContents());
		}
		
		return BJLCList;
	}
	
	/**
	 * 对于PA部件表工作薄中的一个工作表, 抽取其中的BJ物料编号,LC编号,条形码,SN.
	 * @param PABook
	 * @param sheetName
	 * @return PA工作薄中一张工作表的所需编号的封装.
	 */
	public Map getPNs4PASheet(Workbook PABook, String sheetName){
		Map PAPN = new HashMap();		
		List BJPNList = new ArrayList();
		List LCPNList = new ArrayList();
		List BarcodeList = new ArrayList();
		List SNList = new ArrayList();
		
		Sheet sheet = PABook.getSheet(sheetName);
		Cell []BJPNCells = sheet.getColumn(Constants.BJPN_COLUMN_INDEX); 
		Cell []LCPNCells = sheet.getColumn(Constants.LCPN_COLUMN_INDEX);
		Cell []BarcodeCells = sheet.getColumn(Constants.BARCODE_COLUMN_INDEX);
		Cell []SNCells = sheet.getColumn(Constants.SN_COLUMN_INDEX);
		
		int sheetRows = sheet.getRows();
		for(int i=Constants.PA_STEPPING ; i<sheetRows; i++){  				
			add2List(BJPNList, BJPNCells[i].getContents());
			add2List(LCPNList, LCPNCells[i].getContents());
			add2List(BarcodeList, BarcodeCells[i].getContents());
			add2List(SNList, SNCells[i].getContents());			
		}
		PAPN.put(Constants.BJPN_CODENAME, BJPNList);
		PAPN.put(Constants.LCPN_CODENAME, LCPNList);
		PAPN.put(Constants.BARCODE_CODENAME,BarcodeList);
		PAPN.put(Constants.SN_CODENAME,SNList);
		
		return PAPN;
	}
	
	/**
	 * 如果字符串不为空值,就添加进列表中.
	 * @param list
	 * @param str
	 */
	public void add2List(List list,String str){
		if(str != null && !str.equals("")){
			list.add(str);
		}
	}
	
	/**
	 * 进行匹配.
	 * 
	 * @param list BJ工作薄中的LC编号列表
	 * @param map PA工作薄中一张工作表的所需编号的封装.
	 * @return 匹配编号的封装列表,其中一项是[BJ编号,通过某种方式匹配的代号].
	 */
	public List compare(List list, Map map){
		List BJPNList = (List)map.get(Constants.BJPN_CODENAME);
		List LCPNList = (List)map.get(Constants.LCPN_CODENAME);
		List barList = (List)map.get(Constants.BARCODE_CODENAME);
		List SNList = (List)map.get(Constants.SN_CODENAME);
		
		
		List matchedList = new ArrayList();
		matchedList = comparePN(list, BJPNList, Constants.BJPN_CODENAME, matchedList);
		matchedList = comparePN(list, LCPNList, Constants.LCPN_CODENAME, matchedList);
		matchedList = comparePN(list, barList, Constants.BARCODE_CODENAME, matchedList);
		matchedList = comparePN(list, SNList, Constants.SN_CODENAME, matchedList);
		
		return matchedList;
	}
	
	/**
	 * 对比编号.
	 * @param BJList BJ编号列表.
	 * @param PNlist 当前要对比的编号列表
	 * @param token 对哪种编号进行匹配的代号
	 * @param matchedList 要封装的匹配列表
	 * @return 
	 */
	public List comparePN(List BJList, List PNlist, String token, List matchedList){		
		List pn_token = null;
		String pn = null;
		
		for(int i=0 ; i<BJList.size(); i++){
			String BJLC = (String)BJList.get(i);
			
			if(i < PNlist.size()){
				pn = (String)PNlist.get(i);
				
				if(BJLC.equals(pn) || BJLC.substring(3).equals(pn) || 
						BJLC.substring(4).equals(pn) ){  
					pn_token = new ArrayList();	 //重新初始化
					pn_token.add(BJLC);
					pn_token.add(token);
					matchedList.add(pn_token);
				}				
			}			
		}
		
		return matchedList;
	}
	
	public static void main(String []args){
		MatchPN_1 match = new MatchPN_1();
		
		Workbook BJBook = null;
		Workbook PABook = null;
		
		try {
			BJBook = match.readWorkbook("D:/Asset/archive/统计_1007291031/AutoMatch/BJ_parts.xls");
			PABook = match.readWorkbook("D:/Asset/archive/统计_1007291031/AutoMatch/Asset_0729_BJ1.xls");
		} catch (Exception e) {			
			e.printStackTrace();
		} 	
		
		List BJLCList = match.getBJLCList(BJBook, Constants.BJPARTS_SHEET_NAME);
		Map CRPN = match.getPNs4PASheet(PABook, Constants.CARDREADER_SHEET_NAME);
		
		List matchedList = match.compare(BJLCList, CRPN);
		
		
		System.out.println(matchedList.size());
		
	}	
}
