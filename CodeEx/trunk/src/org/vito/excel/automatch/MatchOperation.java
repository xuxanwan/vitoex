package org.vito.excel.automatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableWorkbook;

/**
 * 进行匹配所需辅助操作的类.
 * @author vito
 *
 */
public class MatchOperation {
	
	/**
	 * 读取一个工作薄.
	 * @param filePath
	 * @return
	 */
	public static Workbook readWorkbook(String filePath){
		File file = new File(filePath);		
		return readWorkbook(file);
	}
	
	/**
	 * 读取一个工作薄.
	 * @param f
	 * @return
	 */
	public static Workbook readWorkbook(File f) {		
		Workbook book = null;		
		try{
			book = Workbook.getWorkbook(f);	
		}catch (Exception e){
			e.printStackTrace();
		}			
		return book;
	}	
	
	/**
	 * 创建一个工作薄.
	 * @param fileName
	 * @return
	 */
	public static WritableWorkbook makeWorkbook(String fileName){
		return makeWorkbook(new File(fileName));
	}
	/**
	 * 创建一个工作薄.
	 * @param f
	 * @return
	 */
	public static WritableWorkbook makeWorkbook(File f) {
		WritableWorkbook out = null;
		try {
			out = Workbook.createWorkbook(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * 得到BJ部件表中的所有LC编码的列表.
	 * @param BJBook
	 * @param sheetName
	 * @return
	 */
	public static List getBJLCList(Workbook BJBook, String sheetName){		
		return getColumnContents(BJBook, sheetName, 
				Constants.BJLC_COLUMN_INDEX, Constants.BJ_SHEET);
	}
	
	/**
	 * 得到PA薄中指定表的BJPN列表.
	 * @param PABook
	 * @param sheetName
	 * @return
	 */
	public static List getBJPNList(Workbook PABook, String sheetName){
		return getColumnContents(PABook, sheetName, 
				Constants.BJPN_COLUMN_INDEX, Constants.PA_SHEET);
	}

	/**
	 * 当前工作薄中该指定表, 按某一列的下标得到这一列所有单元格列表.
	 * @param book
	 * @param sheetName
	 * @param columnIndex
	 * @param category 工作表类别,以此判断为了读取列值, 需要越过的该表上部行数.
	 * @return
	 */
	public static List getColumnContents(Workbook book, String sheetName,
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
	 * 如果给定字符串不为空值,就添加进列表中.
	 * @param list
	 * @param str
	 */
	public static void add2List(List list,String str){
		if(str != null && !str.equals("")){
			list.add(str);
		}
	}
	
	/**
	 * 得到PA表中几个编号的横向封装.
	 * [BJPN,barcode,SN,LC]
	 * @param PABook
	 * @param sheetName
	 * @return
	 */
	public static List getPNRecords(Workbook PABook, String sheetName){
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
			//在一行的四个编号中, 有一个有效, 就添加这条记录
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
	 * 获得工作表的表头信息.
	 * 
	 * @param book
	 * @param sheetName
	 * @return 表头有效值的所有单元格.
	 */
	public static Cell[] getSheetHead(Workbook book, String sheetName, String token){
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
	
	/**
	 * 得到BJ表中未匹配的BJLC编号列表.
	 * @param BJBook
	 * @param allMatchedList
	 * @return
	 */
	public static List getUnmatchedBJLC(Workbook BJBook, List allMatchedList){
		List BJLCList = MatchOperation.getBJLCList(BJBook, Constants.BJPARTS_SHEETNAME);
		
		for(int i=0; i<allMatchedList.size(); i++){
			List pn_token =  (List) allMatchedList.get(i);
			String matchedBJLC = (String)pn_token.get(Constants.BJLC_IN_MATCHED_LIST_INDEX);
			
			for(int j=0;j<BJLCList.size(); j++){
				if(isMatched(matchedBJLC, (String)BJLCList.get(j))){
					BJLCList.remove(BJLCList.get(j));
					j--;
					allMatchedList.remove(i);
					i--;
					break;
				}
			}			
		}		
		return BJLCList;
	}
	
	/**
	 * 匹配规则.
	 * 判断传入的编号和BJLC是否匹配.
	 * @param BJLC
	 * @param pn
	 * @return
	 */
	public static boolean isMatched(String BJLC, String pn){
		boolean isOk = false;			
		if(BJLC.equalsIgnoreCase(pn) || BJLC.substring(3).equalsIgnoreCase(pn) || 
				BJLC.substring(4).equalsIgnoreCase(pn) ||
				BJLC.substring(5).equalsIgnoreCase(pn)){
			isOk = true;
		}		
		return isOk;
	}
	
	/**
	 * 复制源列表的每一项到目的列表.
	 * @param src
	 * @param dest
	 * @return
	 */
	public static List copyList(List src, List dest){
		for(int i=0; i<src.size(); i++){
			dest.add(src.get(i));
		}
		return dest;
	}
	
	/**
	 * 从一个工作薄的以该工作表名指定的工作表中, 读取匹配编号的一行记录.
	 * @param book
	 * @param sheetName
	 * @param pn
	 * @param token 判断传入编号所在的列的令牌
	 * @return 该行有效值的所有单元格.
	 */
	public static Cell[] getRow(Workbook book, String sheetName, String pn, String token){
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
