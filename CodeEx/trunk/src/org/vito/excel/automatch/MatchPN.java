package org.vito.excel.automatch;

import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.*; 
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.vito.excel.automatch.Constants;

/**
 * 提供部件编号匹配方法的类.
 * @author vito
 *
 */
public class MatchPN {
	
	/**
	 * BJ表中LC编号列表.
	 */
	private List BJLCList;
	private List matchedList;
	private WritableSheet matchedBJSheet;
	private WritableSheet unmatchedBJSheet;
	private WritableSheet matchedPASheet;
		
	public WritableSheet getMatchedBJSheet() {
		return matchedBJSheet;
	}
	
	public void setUnmatchedBJSheet(WritableSheet unmatchedBJSheet){
		this.unmatchedBJSheet = unmatchedBJSheet;
	}
	
	/**
	 * 创建输出工作薄的一个匹配部件BJ表
	 * @param matchedBJSheet
	 */
	public void setMatchedBJSheet(WritableSheet matchedBJSheet) {
		this.matchedBJSheet = matchedBJSheet;
	}

	public WritableSheet getMatchedPASheet() {
		return matchedPASheet;
	}
	
	/**
	 * 创建输出工作薄的一个匹配部件的PA表
	 * @param matchedPASheet
	 */
	public void setMatchedPASheet(WritableSheet matchedPASheet) {
		this.matchedPASheet = matchedPASheet;
	}
	
	public void setBJLCList(List list){
		this.BJLCList = list;
	}
	
	public List getBJLCList(){
		return this.BJLCList;
	}
	
	public void setMatchedList(List list){
		this.matchedList = list;
	}
	
	public List getMatchedList(){
		return this.matchedList;
	}
	///
	
	/**
	 * 默认构造器.
	 */
	public MatchPN(){
		BJLCList = null;
		matchedList = null;
		matchedBJSheet = null;
		matchedPASheet = null;
	}	
	
	/**
	 * 与BJ工作薄中的LC编号进行比较.
	 * 
	 * @param pnRecords PA表中几个编号的横向封装列表, [BJPN,barcode,SN,LC].
	 * @return 匹配编号的封装列表,其中一项:[BJ编号,通过某种方式匹配的代号].
	 */
	public List compare(List pnRecords){
		matchedList = new ArrayList();				
		List pnRecord = null;
		int constructLength = pnRecords.size();
		
		for(int i=0; i<constructLength; i++){
			pnRecord = (List) pnRecords.get(i); // 一条横向封装记录.			
			comparePNRecord(pnRecord);
		}		
		return matchedList;
	}	
	
	/**
	 * 把BJLC和PA表中的BJPN进行比较. 
	 * 并把比较的结果(匹配的编号)填充入匹配列表封装.
	 * @param BJPNList
	 * @return 匹配列表的封装: [BJLC, PAPN, 编号代号token]
	 */
	public List compareBJPN(List BJPNList){
		return comparePN(BJPNList, Constants.BJPN_CODENAME);				
	}
	
	/**
	 * 把PA表中的给定编号列表和BJLC进行比较. 
	 * @param PNList
	 * @param token 该编号的代号(标示哪一种编号)
	 * @return 匹配列表的封装: [BJLC, PAPN, 编号代号token]
	 */
	public List comparePN(List PNList, String token){
		matchedList = new ArrayList();	
		
		for(int i=0; i<PNList.size(); i++){
			String pn = (String) PNList.get(i);
			
			for(int j=0; j<BJLCList.size(); j++){
				String BJLC = (String) BJLCList.get(j);
				
				if(MatchOperation.isMatched(BJLC, pn)){
//					fillMatchedList(BJLC, token);
					fillMatchedList(BJLC, pn, token);
					
					PNList.remove(pn);
					i = i - 1;  //下标回调, 补位
					BJLCList.remove(BJLC);
					j = j - 1; 
					break;
				}				
			}
		}
		return matchedList;		
	}
	
	
	/**
	 * 先构造横向值(代表一条记录), 再与BJLC列表比较. 找到匹配,则填充进匹配列表的封装.
	 *  
	 * 这个比较方法遵循的比较方式为:
	 * 在BJPN存在的条件下,只比较BJPN,匹配则加入匹配列表,不匹配则跳过该记录.
	 * BJPN无有效值,比较barcode,匹配方式同上
	 * BJPN,barcode都无有效值时, 比较SN和LC, 两者若能匹配到BJLC,就加入匹配列表.
	 */
	public void comparePNRecord(List pnRecord){ 
		String token = null;
		
		String bjpn = (String) pnRecord.get(0);  // 后续改进!
		String bar = (String) pnRecord.get(1);
		String sn = (String) pnRecord.get(2);
		String lc = (String) pnRecord.get(3);
		
		for(int i=0 ; i<BJLCList.size(); i++){
			String BJLC = (String)BJLCList.get(i);
			
			if(bjpn != null && bjpn != ""){  //bjpn有效值
				if(MatchOperation.isMatched(BJLC, bjpn)){
					token = Constants.BJPN_CODENAME;
					fillMatchedList(BJLC,bjpn, token);	
					BJLCList.remove(BJLC);
					break;
				}				
			}else if(bar != null && bar != ""){ //bar有效值
				if(MatchOperation.isMatched(BJLC, bar) ){
					token = Constants.BARCODE_CODENAME;
					fillMatchedList(BJLC,bar, token);
					BJLCList.remove(BJLC);
					break;
				} 		
			}else {  //bjpn,bar 都无有效值
				if( (lc != null && lc != "") || (sn != null && sn != "") ){
					if(MatchOperation.isMatched(BJLC, lc) ){
						token = Constants.LCPN_CODENAME;
						fillMatchedList(BJLC, lc, token);
						BJLCList.remove(BJLC);
						break;
					}else if(MatchOperation.isMatched(BJLC, sn) ){
						token = Constants.SN_CODENAME;
						fillMatchedList(BJLC, sn, token);
						BJLCList.remove(BJLC);
						break;
					}				
				}		
			}
		}
	}
	
	/**
	 * 填充匹配列表的封装. [BJLC, PAPN, 编号代号token]
	 * @param PN
	 * @param token
	 */
	public void fillMatchedList(String BJLC, String PAPN, String token){
		List pn_token = new ArrayList();	 //重新初始化
		pn_token.add(BJLC);
		pn_token.add(PAPN);
		pn_token.add(token);
		matchedList.add(pn_token);	
	}	
	
	/**
	 * 这个填充方法只针对PA薄中的该指定工作表,
	 * 根据匹配列表封装中的匹配编号,将BJ表和该PA表中对应的记录行分别抽取
	 * 到输出工作薄的相应表.
	 * 
	 * 一张是对应匹配编号的BJ物料表中的记录.
	 * 一张是对应PA表中的记录. 
	 * @param matchedList
	 * @param format
	 * @param paSheetName
	 * @param category
	 */
	public void output2SheetsPerPASheet(Workbook BJBook, Workbook PABook,
			List matchedList, WritableCellFormat format,
			String paSheetName){		
		Label []bjLabelRow = null;
		Label []paLabelRow = null;
		Cell []bjCellRow = null;
		Cell []paCellRow = null;
		
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];  //初始化
		paLabelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
		for(int i=0; i<matchedList.size(); i++){
			List pn_token = (List)matchedList.get(i);
			String bjlc = (String)pn_token.get(Constants.BJLC_IN_MATCHED_LIST_INDEX);
			String pn = (String) pn_token.get(Constants.PAPN_IN_MATCHED_LIST_INDEX);
			String token = (String) pn_token.get(Constants.TOKEN_IN_MATCHED_LIST_INDEX);
			
			bjCellRow = MatchOperation.getRow(BJBook,Constants.BJPARTS_SHEETNAME,bjlc,Constants.BJLC_CODENAME);
			paCellRow = MatchOperation.getRow(PABook,paSheetName,pn,token);		
			
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
	
	/**
	 * 将BJ表中没有匹配的编号对应的行输出到新的工作表中.
	 * @param BJBook
	 * @param unmatchedBJLC
	 * @param format
	 */
	public void makeUnmatchedBJSheet(Workbook BJBook, List unmatchedBJLC, 
			WritableCellFormat format){
		Label []bjLabelRow = null;
		Cell []bjCellRow = null;		
		int currentRowCount = unmatchedBJSheet.getRows();
		
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];  //初始化
		for(int i=0; i<unmatchedBJLC.size(); i++){			
			String bjlc = (String)unmatchedBJLC.get(i);
			bjCellRow = MatchOperation.getRow(BJBook,Constants.BJPARTS_SHEETNAME,bjlc,Constants.BJLC_CODENAME);
			
			for(int j=0; j<bjCellRow.length; j++){
				bjLabelRow[j] = new Label(j,currentRowCount,bjCellRow[j].getContents(),format);				
				try {
					unmatchedBJSheet.addCell(bjLabelRow[j]);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}			
			currentRowCount++;		
		}	
	}
	
	/**
	 * 对于所有进行匹配的PA表, 最后只输出2个新表, 分别是BJ表和PA表中的对应项.
	 * 调用一次该填充方法, 对当前的PA表, 向准备输出的两个工作表分别填充
	 * 对应于该PA表的匹配结果.
	 * 
	 * @param paSheetName 当前的PA表
	 */
	public void fillOutputSheets(Workbook BJBook, Workbook PABook,
			List matchedList, WritableCellFormat format,
			String paSheetName){
		int currentBJRowCount = matchedBJSheet.getRows();
		int currentPARowCount = matchedPASheet.getRows();		
		
		Label []bjLabelRow = null;
		Label []paLabelRow = null;
		Cell []bjCellRow = null;
		Cell []paCellRow = null;
		
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];  //初始化
		paLabelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
		for(int i=0; i<matchedList.size(); i++){
			List pn_token = (List)matchedList.get(i);
			String bjlc = (String)pn_token.get(Constants.BJLC_IN_MATCHED_LIST_INDEX);
			String pn = (String) pn_token.get(Constants.PAPN_IN_MATCHED_LIST_INDEX);
			String token = (String) pn_token.get(Constants.TOKEN_IN_MATCHED_LIST_INDEX);
			
			bjCellRow = MatchOperation.getRow(BJBook,Constants.BJPARTS_SHEETNAME,bjlc,Constants.BJLC_CODENAME);
			paCellRow = MatchOperation.getRow(PABook,paSheetName,pn,token);		
			
			for(int j=0; j<bjCellRow.length; j++){
				bjLabelRow[j] = new Label(j,currentBJRowCount,bjCellRow[j].getContents(),format);				
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
			currentBJRowCount++;
			
			for(int k=0; k<paCellRow.length; k++){
				paLabelRow[k] = new Label(k,currentPARowCount,paCellRow[k].getContents(),format);
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
			currentPARowCount++;
		}
		
	}
	
	/**
	 * 默认的方法(由于PA表中的表头是一致的).
	 * 把表头信息写入到输出的工作表里.  
	 * @param book
	 * @param format
	 * @param category
	 */
	public void makeSheetHead(Workbook book, 
			WritableCellFormat format, String category){			
		makeSheetHead(book, Constants.CARDREADER_SHEETNAME, format, category);		
	}
	/**
	 * 把表头信息写入到输出的工作表里.
	 * @param book
	 * @param sheetName 要读取的PA薄中的工作表的名字 (BJ表是一张工作表)
	 * @param format
	 * @param category
	 */
	public void makeSheetHead(Workbook book, String sheetName, 
			WritableCellFormat format, String category){		
		Label []labelRow = null;
		Cell []sheetHead = null;
		
		if(category.equals(Constants.BJ_SHEET) || 
				category.equals(Constants.BJ_UNMATCHED_SHEET)){
			labelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];
			sheetHead = MatchOperation.getSheetHead(book,Constants.BJPARTS_SHEETNAME,Constants.BJ_SHEET);  //加入表头的列名信息			
		}else if(category.equals(Constants.PA_SHEET)){
			labelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
			sheetHead = MatchOperation.getSheetHead(book,sheetName,Constants.PA_SHEET);			
		}		
		
		for(int i=0; i<sheetHead.length; i++){
			labelRow[i] = new Label(i,0,sheetHead[i].getContents(),format);
			try {
				if(category.equals(Constants.BJ_SHEET)){
					matchedBJSheet.addCell(labelRow[i]);
				}else if(category.equals(Constants.PA_SHEET)){
					matchedPASheet.addCell(labelRow[i]);
				}else if(category.equals(Constants.BJ_UNMATCHED_SHEET)){
					unmatchedBJSheet.addCell(labelRow[i]);
				}
			} catch (RowsExceededException e) {
				System.out.println("Add sheet head failed - rows exceeded!");
				e.printStackTrace();
			} catch (WriteException e) {
				System.out.println("Add sheet head failed - write exception!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 输出该工作薄.
	 * 输出完毕之后,关闭该工作薄.
	 * @param output
	 */
	public void write(WritableWorkbook output){
		System.out.println("Start to write output workbook ...");
		try {
			output.write();
		} catch (IOException e) {
			System.out.println("Write output workbook failed!");
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (Exception e) {		
			System.out.println("Close the writable workbook failed!");
			e.printStackTrace();
		} 
		System.out.println("Write output workbook succeeded!");
	}	
}
