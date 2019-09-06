package com.java456.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.java456.entity.Client;

public class ExcelUtil {
	
	public static void main(String[] args) throws IOException {
	}

	
	/**
	 * 格式化单元格返回其内容 格式化成string返回。
	 * 
	 * @param cell
	 * @return
	 */
	public static String formatCell(HSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		}
	}
	
	
	/**
	 * 
	 * 返回 日期  date  2018/3/28 14:18:00  这种类型的可以。
	 */
	public static Date formatDate(HSSFCell cell) throws ParseException{
		
		String str = cell.toString();
		
		Date date = null;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			date  = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			date = cell.getDateCellValue();
		}
		
		return date;
	}
	
	
	/**
	 * 返回 BigDecimal 数据
	 */
	public static BigDecimal formatBigDecimal(HSSFCell cell) throws ParseException{
		String str = ExcelUtil.formatCell(cell);
		BigDecimal num = new BigDecimal(str.trim());
		return num;
	}
	
	
	

}


