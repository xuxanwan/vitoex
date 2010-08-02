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
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class MatchPN_1 {
	
	/**
	 * BJ表中LC编号列表.
	 */
	private List BJLCList;
	private List matchedList;
	//private List PNList;
	private WritableSheet matchedBJSheet;
	private WritableSheet matchedPASheet;
	
	public WritableSheet getMatchedBJSheet() {
		return matchedBJSheet;
	}

	public void setMatchedBJSheet(WritableSheet matchedBJSheet) {
		this.matchedBJSheet = matchedBJSheet;
	}

	public WritableSheet getMatchedPASheet() {
		return matchedPASheet;
	}

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
	
//	public void setPNList(List list){
//		this.PNList = list;
//	}
//	
//	public List getPNList(){
//		return this.PNList;
//	}
	
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
	 * 得到PA表中几个编号的横向封装.
	 * @param PABook
	 * @param sheetName
	 * @return
	 */
	public List getPNRecords(Workbook PABook, String sheetName){
		List pnRecord = new ArrayList();
		List pnRecords = new ArrayList();
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
			String bjpn = BJPNCells[i].getContents();
			String bar = BarcodeCells[i].getContents();
			String sn = SNCells[i].getContents();
			String lc = LCPNCells[i].getContents();
			if((bjpn != null && bjpn != "") ||
					(bar != null && bar != "") || 
					(sn != null && sn != "") ||
					(lc != null && lc != "")){
				pnRecord.add(bjpn);
				pnRecord.add(bar);
				pnRecord.add(sn);
				pnRecord.add(lc);
			}
			pnRecords.add(pnRecord);
		}
		
		return pnRecords;
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
	 * 与BJ工作薄中的LC编号进行匹配.
	 * 
	 * @param list BJ工作薄中的LC编号列表
	 * @param map PA工作薄中一张工作表的所需编号的封装.
	 * @return 匹配编号的封装列表,其中一项是[BJ编号,通过某种方式匹配的代号].
	 */
//	public List compare(Map map){
	public List compare(List pnRecords){
//		List BJPNList = (List)map.get(Constants.BJPN_CODENAME);
//		List LCPNList = (List)map.get(Constants.LCPN_CODENAME);
//		List barList = (List)map.get(Constants.BARCODE_CODENAME);
//		List SNList = (List)map.get(Constants.SN_CODENAME);		
		
		//matchedList = getMatchedList();
		//List list = getBJLCList();
		matchedList = new ArrayList();		
		
		///
//		comparePN(BJPNList, Constants.BJPN_CODENAME);		
//		comparePN(LCPNList, Constants.LCPN_CODENAME);  
//		comparePN(barList, Constants.BARCODE_CODENAME);
//		comparePN(SNList, Constants.SN_CODENAME);
		///
		
		List constructList = null;
		int constructLength = pnRecords.size();
//		int constructLength = BJPNList.size();
//		if(LCPNList.size() > constructLength){
//			constructLength = LCPNList.size();
//		}
//		if(barList.size() > constructLength){
//			constructLength = barList.size();
//		}
//		if(SNList.size() > constructLength){
//			constructLength = SNList.size();
//		}
		
		for(int i=0; i<constructLength; i++){
			constructList = (List) pnRecords.get(i); // 一条横向封装记录.
			
//			constructList = fillConstructList(i, BJPNList, constructList);
//			constructList = fillConstructList(i, barList, constructList);
//			constructList = fillConstructList(i, SNList, constructList);
//			constructList = fillConstructList(i, LCPNList, constructList);
			
//			constructList.add(BJPNList.get(i));
//			constructList.add(barList.get(i));
//			constructList.add(SNList.get(i));
//			constructList.add(LCPNList.get(i));
			
			comparePN2(constructList);
		}
		
		return matchedList;
	}
	
	public List fillConstructList(int index,List list, List constructList){
		if(index < list.size()){
			constructList = fillConstructList((String)list.get(index),constructList);
		}else {
			constructList.add("");
		}
		return constructList;
	}
	
	public List fillConstructList(String str, List constructList){		
		if(str != null && str != ""){
			constructList.add(str);
		}else {
			constructList.add("");
		}
		
		return constructList;
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
	 * 先构造横向值(代表一条记录), 再与BJLC列表比较.
	 */
	public void comparePN2(List constructList){ // 改名?
		//List pn_token = null;
		//String pn = null;	
		String token = null;
		
		String bjpn = (String) constructList.get(0);  // 后续改进!
		String bar = (String) constructList.get(1);
		String sn = (String) constructList.get(2);
		String lc = (String) constructList.get(3);
		
		for(int i=0 ; i<BJLCList.size(); i++){
			String BJLC = (String)BJLCList.get(i);
			
			if(bjpn != null && bjpn != ""){  //bjpn有效值
				if(isMatched(BJLC, bjpn)){
					token = Constants.BJPN_CODENAME;
					fillMatchedList(BJLC, token);	
					BJLCList.remove(BJLC);
					break;
				}				
			}else if(bar != null && bar != ""){ //bar有效值
				if(isMatched(BJLC, bar) ){
					token = Constants.BARCODE_CODENAME;
					fillMatchedList(BJLC, token);
					BJLCList.remove(BJLC);
					break;
				} 		
			}else {  //bjpn,bar 都无有效值
				if( (lc != null && lc != "") || (sn != null && sn != "") ){
					if(isMatched(BJLC, lc) ){
						token = Constants.LCPN_CODENAME;
						fillMatchedList(BJLC, token);
						BJLCList.remove(BJLC);
						break;
					}else if(isMatched(BJLC, sn) ){
						token = Constants.SN_CODENAME;
						fillMatchedList(BJLC, token);
						BJLCList.remove(BJLC);
						break;
					}				
				}		
			}
		}
	}
	
	/**
	 * 是否匹配.
	 * @param BJLC
	 * @param pn
	 * @return
	 */
	public boolean isMatched(String BJLC, String pn){
		boolean isOk = false;
		
		if(BJLC.equals(pn) || BJLC.substring(3).equals(pn) || 
				BJLC.substring(4).equals(pn) ){
			isOk = true;
		}		
		return isOk;
	}
	
	/**
	 * 填充匹配列表的封装.
	 * @param BJLC
	 * @param token
	 */
	public void fillMatchedList(String BJLC, String token){
		List pn_token = new ArrayList();	 //重新初始化
		pn_token.add(BJLC);
		pn_token.add(token);
		matchedList.add(pn_token);	
	}
	
	public WritableWorkbook makeWorkbook(String fileName) throws IOException{
		return makeWorkbook(new File(fileName));
	}
	
	public WritableWorkbook makeWorkbook(File f) throws IOException{
		WritableWorkbook out = Workbook.createWorkbook(f);
		return out;
	}
	
	/**
	 * 对PA薄中一个工作表作对比, 将匹配的结果分别写入新工作薄中的两张工作表.
	 * 一张是对应匹配编号的BJ物料表中的记录.
	 * 一张是对应PA表中的记录.
	 * @param destBook
	 * @param list 对PA薄中一个工作表作对比,返回的匹配的编号封装
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void makeOutputSheets(Workbook srcBook1, Workbook srcBook2, 
			WritableWorkbook destBook, 
			List list, String paSheetName) throws RowsExceededException, WriteException{		
		setMatchedBJSheet(destBook.createSheet("MatchedBJSheet", 0));
		setMatchedPASheet(destBook.createSheet("matchedPASheet", 1));
		
		WritableFont arial9 = new WritableFont(WritableFont.ARIAL, 9);
		WritableCellFormat format = new WritableCellFormat(arial9);
		
		Label []bjLabelRow = null;
		Label []paLabelRow = null;
		Cell []bjCellRow = null;
		Cell []paCellRow = null;
		
		bjLabelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT + 1];
		paLabelRow = new Label[Constants.PA_VALID_COLUMN_COUNT + 1];
		
		Cell []bjSheetHead = getSheetHead(srcBook1,Constants.BJPARTS_SHEET_NAME,
				Constants.BJ_SHEET);		
		Cell []paSheetHead = getSheetHead(srcBook2,paSheetName,Constants.PA_SHEET);
		for(int i=0; i<bjSheetHead.length; i++){
			bjLabelRow[i] = new Label(i,0,bjSheetHead[i].getContents(),format);
			matchedBJSheet.addCell(bjLabelRow[i]);
		}
		for(int i=0; i<paSheetHead.length; i++){
			paLabelRow[i] = new Label(i,0,paSheetHead[i].getContents(),format);
			matchedPASheet.addCell(paLabelRow[i]);
		}
		
		for(int i=0; i<list.size(); i++){
			List pn_token = (List)list.get(i);
			String pn = (String) pn_token.get(0);
			String token = (String) pn_token.get(1);
			
			bjCellRow = getRow(srcBook1,Constants.BJPARTS_SHEET_NAME,pn,Constants.BJLC_CODENAME);
			paCellRow = getRow(srcBook2,paSheetName,pn,token);
//			bjLabelRow = new Label[bjCellRow.length + 1];
//			paLabelRow = new Label[paCellRow.length + 1];			
			
			for(int j=0; j<bjCellRow.length; j++){
				bjLabelRow[j] = new Label(j,i+1,bjCellRow[j].getContents(),format);				
				matchedBJSheet.addCell(bjLabelRow[j]);				
			}			
			for(int k=0; k<paCellRow.length; k++){
				paLabelRow[k] = new Label(k,i+1,paCellRow[k].getContents(),format);
				matchedPASheet.addCell(paLabelRow[k]);
			}			
		}
		
				
		
	}
	
	/**
	 * 获得工作表的表头信息.
	 * @param book
	 * @param sheetName
	 * @return
	 */
	public Cell[] getSheetHead(Workbook book, String sheetName, String token){
		Cell []cells = null;
		Cell []temp = null;
		Sheet sheet = book.getSheet(sheetName);
		int limit = -1;
		
		if(token.equals(Constants.BJ_SHEET)){
			temp = sheet.getRow(Constants.BJ_SHEET_HEAD_ROWINDEX);
			limit = Constants.BJ_VALID_COLUMN_COUNT;
		}else if(token.equals(Constants.PA_SHEET)){
			temp = sheet.getRow(Constants.PA_SHEET_HEAD_ROWINDEX);
			limit = Constants.PA_VALID_COLUMN_COUNT;
		}	
		
		cells = new Cell[limit];		
		for(int i=0; i<limit; i++){
			cells[i] = temp[i];
		}
		return cells;	
		
	}
	
	public void write(WritableWorkbook output) throws IOException, WriteException{
		output.write();
		output.close();
	}
	
	/**
	 * 从一个工作薄的以改工作表名指定的工作表中, 读取匹配编号的一行记录.
	 * @param book
	 * @param sheetName
	 * @param pn
	 * @param token
	 * @return
	 */
	public Cell[] getRow(Workbook book, String sheetName, String pn, String token){
		Cell []cells = null;
		Sheet srcSheet = book.getSheet(sheetName);		
		int rowIndex = -1;
		Cell []srcCells = null;
		
		if(token.equals(Constants.BJPN_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BJPN_COLUMN_INDEX);
		}else if(token.equals(Constants.BARCODE_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BARCODE_COLUMN_INDEX);
		}else if(token.equals(Constants.SN_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.SN_COLUMN_INDEX);
		}else if(token.equals(Constants.BJLC_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BJLC_COLUMN_INDEX);
		}
		
		for(int i=0; i<srcCells.length; i++){
			String content = srcCells[i].getContents();
//			if(content != null && !content.equals("")){
				if(content.equals(pn)){
					rowIndex = i;
					break;
				}
//			}
		}
		
		Cell []temp = srcSheet.getRow(rowIndex);
		int limit = -1;
		if(token.equals(Constants.BJLC_CODENAME)){
			limit = Constants.BJ_VALID_COLUMN_COUNT;			
		}else {
			limit = Constants.PA_VALID_COLUMN_COUNT;
		}
		cells = new Cell[limit];
		
		for(int i=0; i<limit; i++){
			cells[i] = temp[i];
		}
		return cells;
	}
	
}
