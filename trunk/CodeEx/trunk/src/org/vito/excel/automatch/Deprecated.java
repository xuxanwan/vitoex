package org.vito.excel.automatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 弃用方法或操作的存放类.
 * @author vito
 *
 */
public class Deprecated {
	
	private List BJLCList;
	private List matchedList;
	private WritableSheet matchedBJSheet;
	private WritableSheet matchedPASheet;
	
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
			MatchOperation.add2List(BJPNList, BJPNCells[i].getContents());
			MatchOperation.add2List(LCPNList, LCPNCells[i].getContents());
			MatchOperation.add2List(BarcodeList, BarcodeCells[i].getContents());
			MatchOperation.add2List(SNList, SNCells[i].getContents());			
		}
		PAPN.put(Constants.BJPN_CODENAME, BJPNList);
		PAPN.put(Constants.LCPN_CODENAME, LCPNList);
		PAPN.put(Constants.BARCODE_CODENAME,BarcodeList);
		PAPN.put(Constants.SN_CODENAME,SNList);
		
		return PAPN;
	}
	
	/**
	 * BJ工作薄中的LC编号与给定列表的匹配.
	 * @param BJList BJ编号列表.
	 * @param PNlist 当前要对比的编号列表
	 * @param token 对哪种编号进行匹配的代号
	 * @param matchedList 要封装的匹配列表
	 * @return 
	 */
	public void comparePN(List PNList, String token){		
		List pn_token = null;
		String pn = null;			
		
		for(int i=0 ; i<BJLCList.size(); i++){
			String BJLC = (String)BJLCList.get(i);
			
			for(int j=0; j<PNList.size(); j++){
				pn = (String)PNList.get(j);
				
				if(BJLC.equals(pn) || BJLC.substring(3).equals(pn) || 
						BJLC.substring(4).equals(pn)  
						//|| BJLC.substring(3, 12).equals(pn) || BJLC.substring(4, 12).equals(pn)  //不需要?
						){  
					pn_token = new ArrayList();	 //重新初始化
					pn_token.add(BJLC);
					pn_token.add(token);
					matchedList.add(pn_token);
					
					//找到匹配值,两个源列表各自移除此匹配项
					PNList.remove(pn);
					BJLCList.remove(BJLC);
					i = i - 1;  //由于列表移除了一个元素,下标补位
					break;  //找到匹配的编号,跳出此次循环
				}					
			}				
		}
	}
	
	/**
	 * <b> Some problems herein! Remains just for refence. 不要用这个方法! </b> <P>
	 * 对PA薄中一个工作表作对比, 将匹配的结果分别写入新工作薄中的两张工作表.
	 * 一张是对应匹配编号的BJ物料表中的记录.
	 * 一张是对应PA表中的记录.
	 * 
	 * @param BJBook
	 * @param PABook
	 * @param destBook
	 * @param list 对PA薄中一个工作表作对比,返回的匹配的编号封装
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void makeOutputSheets(Workbook BJBook, Workbook PABook, 
			WritableWorkbook destBook, 
			List list, String paSheetName) {		
//		setMatchedBJSheet(destBook.createSheet("MatchedBJSheet", 0));
//		setMatchedPASheet(destBook.createSheet("matchedPASheet", 1));
		
		WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9);
		WritableCellFormat format = new WritableCellFormat(arial9);
		
		Label []bjLabelRow = null;
		Label []paLabelRow = null;
		Cell []bjCellRow = null;
		Cell []paCellRow = null;
		
		//构造一行的显示,数组大小表示列的数量
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];  
		paLabelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
		
		//加入表头的列名信息
		Cell []bjSheetHead = MatchOperation.getSheetHead(BJBook,Constants.BJPARTS_SHEETNAME,
				Constants.BJ_SHEET);		
		Cell []paSheetHead = MatchOperation.getSheetHead(PABook,paSheetName,Constants.PA_SHEET);
		for(int i=0; i<bjSheetHead.length; i++){
			bjLabelRow[i] = new Label(i,0,bjSheetHead[i].getContents(),format);
			try {
				matchedBJSheet.addCell(bjLabelRow[i]);
			} catch (RowsExceededException e) {
				System.out.println("Add BJ sheet head failed - rows exceeded!");
				e.printStackTrace();
			} catch (WriteException e) {
				System.out.println("Add BJ sheet head failed - write exception!");
				e.printStackTrace();
			}
		}
		for(int i=0; i<paSheetHead.length; i++){
			paLabelRow[i] = new Label(i,0,paSheetHead[i].getContents(),format);
			try {
				matchedPASheet.addCell(paLabelRow[i]);
			} catch (RowsExceededException e) {
				System.out.println("Add PA sheet head failed - rows exceeded!");
				e.printStackTrace();
			} catch (WriteException e) {
				System.out.println("Add PA sheet head failed - write exception!");
				e.printStackTrace();
			}
		}
		
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];  //重新初始化
		paLabelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
		for(int i=0; i<list.size(); i++){
			List pn_token = (List)list.get(i);
			String pn = (String) pn_token.get(Constants.PN_IN_MATCHED_LIST_INDEX);
			String token = (String) pn_token.get(Constants.TOKEN_IN_MATCHED_LIST_INDEX);
			
			bjCellRow = MatchOperation.getRow(BJBook,Constants.BJPARTS_SHEETNAME,pn,Constants.BJLC_CODENAME);
			paCellRow = MatchOperation.getRow(PABook,paSheetName,pn,token);
//			bjLabelRow = new Label[bjCellRow.length + 1];
//			paLabelRow = new Label[paCellRow.length + 1];			
			
			for(int j=0; j<bjCellRow.length; j++){
				bjLabelRow[j] = new Label(j,i+1,bjCellRow[j].getContents(),format);				
				try {
					matchedBJSheet.addCell(bjLabelRow[j]);
				} catch (RowsExceededException e) {
					System.out.println("Add a row to BJ sheet failed - rows exceeded!");
					e.printStackTrace();
				} catch (WriteException e) {
					System.out.println("Add a row to BJ sheet failed - write exception!");
					e.printStackTrace();
				}				
			}			
			for(int k=0; k<paCellRow.length; k++){
				paLabelRow[k] = new Label(k,i+1,paCellRow[k].getContents(),format);
				try {
					matchedPASheet.addCell(paLabelRow[k]);
				} catch (RowsExceededException e) {
					System.out.println("Add a row to PA sheet failed - rows exceeded!");
					e.printStackTrace();
				} catch (WriteException e) {
					System.out.println("Add a row to PA sheet failed - write exception!");
					e.printStackTrace();
				}
			}			
		}	
	}
}
