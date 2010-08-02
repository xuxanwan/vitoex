package org.vito.excel;

public class Constants {
	/**
	 * BJ部件表中LC编码所在列的下标.
	 */
	public static final int BJLC_COLUMN_INDEX = 1;
	/**
	 * PA部件表中的BJ物料编号列的下标.
	 */
	public static final int BJPN_COLUMN_INDEX = 7;
	public static final int LCPN_COLUMN_INDEX = 6;
	public static final int BARCODE_COLUMN_INDEX = 8;
	public static final int SN_COLUMN_INDEX = 4;
	
	/**
	 * BJ表中表头所在行下标
	 */
	public static final int BJ_SHEET_HEAD_ROWINDEX = 0;
	
	/**
	 * PA表中表头所在行下标
	 */
	public static final int PA_SHEET_HEAD_ROWINDEX = 10;
	
	/**
	 * BJ部件表中,为了越过所在列的列名而越过的行数.
	 */
	public static final int BJ_STEPPING = 1;
	/**
	 * PA部件表中,为了越过所在列的上部空间,直接读取表数值而越过的行数.
	 */
	public static final int PA_STEPPING = 11;
	
	/**
	 * BJ表中,有效数据的列的数量.
	 */
	public static final int BJ_VALID_COLUMN_COUNT = 14;
	
	/**
	 * PA表中,有效数据的列的数量.
	 */
	public static final int PA_VALID_COLUMN_COUNT = 16;
	
	public static final String BJPARTS_SHEET_NAME = "Sheet1";
	public static final String CARDREADER_SHEET_NAME = "Cardreader-Barry";
	public static final String PCI_SHEET_NAME = "PCI Adapter-barry";
	
	/**
	 * BJ物料表.
	 */
	public static final String BJ_SHEET = "BJ";
	
	/**
	 * PA工作表
	 */
	public static final String PA_SHEET = "PA";
	
	/**
	 * BJ表中的LC编号
	 */
	public static final String BJLC_CODENAME = "BJLC";
	
	/**
	 * PA表中的BJ物料编号.
	 */
	public static final String BJPN_CODENAME = "BJPN";
	/**
	 * PA表中的LC编号.
	 */
	public static final String LCPN_CODENAME = "LCPN";
	/**
	 * PA表中的条形码.
	 */
	public static final String BARCODE_CODENAME = "Barcode";
	/**
	 * PA表中的SN.
	 */
	public static final String SN_CODENAME = "SN";
	
}
