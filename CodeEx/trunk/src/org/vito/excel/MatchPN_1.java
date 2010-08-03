package org.vito.excel;

import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.orbutil.closure.Constant;

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
//		Sheet BJSheet = BJBook.getSheet(sheetName);
//		List BJLCList = new ArrayList();
//		Cell []BJLCCells = BJSheet.getColumn(Constants.BJLC_COLUMN_INDEX); 
//		int BJSheetRows = BJSheet.getRows();
//		for(int i=Constants.BJ_STEPPING ; i<BJSheetRows; i++){  //越过该列列名,即越过1行
////			String readOut = BJLCCells[i].getContents();
////			if(readOut != null && !readOut.equals("")){
////				BJLCList.add(readOut);
////			}
//			add2List(BJLCList, BJLCCells[i].getContents());
//		}
//		
//		return BJLCList;
		
		return getColumnContents(BJBook, sheetName, 
				Constants.BJLC_COLUMN_INDEX, Constants.BJ_SHEET);
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
		List pnRecord = null;
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
				pnRecord = new ArrayList(); // 重新初始化
				pnRecord.add(bjpn);
				pnRecord.add(bar);
				pnRecord.add(sn);
				pnRecord.add(lc);
				pnRecords.add(pnRecord);
			}			
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
	 * 把BJLC和PA表中的BJPN进行比较. ?
	 * 并把比较的结果(匹配的编号)填充入匹配列表封装.
	 * @param BJPNList
	 */
	public List compareBJPN(List BJPNList){
		matchedList = new ArrayList();	
		
		for(int i=0; i<BJLCList.size(); i++){
			String BJLC = (String) BJLCList.get(i);
			
			for(int j=0; j<BJPNList.size(); j++){
				String BJPN = (String) BJPNList.get(j);
				
				if(isMatched(BJLC, BJPN)){
					fillMatchedList(BJLC, Constants.BJPN_CODENAME);
					
					BJPNList.remove(BJPN);
					BJLCList.remove(BJLC);
					break;
				}				
			}
		}
		return matchedList;
		
	}
	
	/**
	 * 得到PA薄中指定表的BJPN列表.
	 * @param PABook
	 * @param sheetName
	 * @return
	 */
	public List getBJPNList(Workbook PABook, String sheetName){
		return getColumnContents(PABook, sheetName, 
				Constants.BJPN_COLUMN_INDEX, Constants.PA_SHEET);
	}
	
	/**
	 * 得到指定表某一列的值.
	 * @param book
	 * @param sheetName
	 * @param columnIndex
	 * @param category
	 * @return
	 */
	public List getColumnContents(Workbook book, String sheetName,
			int columnIndex, String category){
		Sheet sheet = book.getSheet(sheetName);
		List list = new ArrayList();
		Cell []cells = sheet.getColumn(columnIndex);
		
		int sheetRows = sheet.getRows();
		int stepping = -1;  //越过该列列名
		if(category.equals(Constants.BJ_SHEET)){
			stepping = Constants.BJ_STEPPING;
		}else if(category.equals(Constants.PA_SHEET)){
			stepping = Constants.PA_STEPPING;
		}
		
		for(int i=stepping ; i<sheetRows; i++){  
			add2List(list, cells[i].getContents());
		}		
		return list;
	}
	
	
	/**
	 * 先构造横向值(代表一条记录), 再与BJLC列表比较. 找到匹配,则填充进匹配列表的封装.
	 * 
	 * 
	 * 这个比较方法遵循的比较方式为:
	 * 在BJPN存在的条件下,只比较BJPN,匹配则加入匹配列表,不匹配则跳过该记录.
	 * BJPN无有效值,比较barcode,匹配方式同上
	 * BJPN,barcode都无有效值时, 比较SN和LC, 两者若能匹配到BJLC,就加入匹配列表.
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
	 * 判断传入的编号和BJLC是否匹配.
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
	
//	/**
//	 * 按传入的匹配模式来匹配BJLC和传入的编号.
//	 * @param BJLC
//	 * @param pn
//	 * @param modus
//	 * @return
//	 */
//	public boolean isMatched(String BJLC, String pn, String modus){
//		if(modus.equals(Constants.BJPN_COMPARE)){
//			return BJLC.equals(pn);
//		}		
//		return false;
//	}
//	
//	/**
//	 * 判断BJLC和BJPN是否相同.
//	 * @param BJLC
//	 * @param BJPN
//	 * @return
//	 */
//	public boolean isBJPNMatched(String BJLC, String BJPN){
//		return BJLC.equals(BJPN);
//	}
	
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
		Cell []bjSheetHead = getSheetHead(BJBook,Constants.BJPARTS_SHEET_NAME,
				Constants.BJ_SHEET);		
		Cell []paSheetHead = getSheetHead(PABook,paSheetName,Constants.PA_SHEET);
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
			
			bjCellRow = getRow(BJBook,Constants.BJPARTS_SHEET_NAME,pn,Constants.BJLC_CODENAME);
			paCellRow = getRow(PABook,paSheetName,pn,token);
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
	
	/**
	 * 这个填充方法只针对PA薄中的该指定工作表,
	 * 根据匹配列表封装中的匹配编号,将BJ表和该PA表中对应的记录行分别抽取
	 * 到输出工作薄的相应表.
	 * 
	 * 一张是对应匹配编号的BJ物料表中的记录.
	 * 一张是对应PA表中的记录.
	 * 
	 * 
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
			String pn = (String) pn_token.get(Constants.PN_IN_MATCHED_LIST_INDEX);
			String token = (String) pn_token.get(Constants.TOKEN_IN_MATCHED_LIST_INDEX);
			
			bjCellRow = getRow(BJBook,Constants.BJPARTS_SHEET_NAME,pn,Constants.BJLC_CODENAME);
			paCellRow = getRow(PABook,paSheetName,pn,token);		
			
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
	
	//所有匹配显示的结果,只填充到两个表中...
	public void fillSheets(){
		
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
		
		if(category.equals(Constants.BJ_SHEET)){
			labelRow = new Label[Constants.BJ_VALID_COLUMN_COUNT ];
			sheetHead = getSheetHead(book,Constants.BJPARTS_SHEET_NAME,Constants.BJ_SHEET);  //加入表头的列名信息			
		}else if(category.equals(Constants.PA_SHEET)){
			labelRow = new Label[Constants.PA_VALID_COLUMN_COUNT ];
			sheetHead = getSheetHead(book,sheetName,Constants.PA_SHEET);			
		}		
		
		for(int i=0; i<sheetHead.length; i++){
			labelRow[i] = new Label(i,0,sheetHead[i].getContents(),format);
			try {
				if(category.equals(Constants.BJ_SHEET)){
					matchedBJSheet.addCell(labelRow[i]);
				}else if(category.equals(Constants.PA_SHEET)){
					matchedPASheet.addCell(labelRow[i]);
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
	 * 把表头信息写入到输出的工作表里. 
	 * 默认的方法(PA表中的表头一致).
	 * @param book
	 * @param format
	 * @param category
	 */
	public void makeSheetHead(Workbook book, 
			WritableCellFormat format, String category){			
		makeSheetHead(book, Constants.CARDREADER_SHEET_NAME, format, category);		
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
	
	/**
	 * 从一个工作薄的以该工作表名指定的工作表中, 读取匹配编号的一行记录.
	 * @param book
	 * @param sheetName
	 * @param pn
	 * @param token
	 * @return
	 */
	public Cell[] getRow(Workbook book, String sheetName, String pn, String token){
		Cell []cells = null;
		Sheet srcSheet = book.getSheet(sheetName);
		int rowIndex = -1;  //要抽取的行的下标
		Cell []srcCells = null;
		
		if(token.equals(Constants.BJPN_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BJPN_COLUMN_INDEX);
		}else if(token.equals(Constants.BARCODE_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BARCODE_COLUMN_INDEX);
		}else if(token.equals(Constants.SN_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.SN_COLUMN_INDEX);
		}else if(token.equals(Constants.BJLC_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.BJLC_COLUMN_INDEX);
		}else if(token.equals(Constants.LCPN_CODENAME)){
			srcCells = srcSheet.getColumn(Constants.LCPN_COLUMN_INDEX);
		}
		
		for(int i=0; i<srcCells.length; i++){
			String content = srcCells[i].getContents();
			if(content != null && !content.equals("")){
				if(content.equals(pn)){
					rowIndex = i;
					break;
				}
			}
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
