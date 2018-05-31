package com.tiany.util.io;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel工具类
 * 
 * @author tWX508286
 *
 */
public class ExcelUtil {
	/**
	 * 得到表格中的一行的数据
	 * 
	 * @param file
	 * @param rowIndex
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRow(File file, int rowIndex) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		int numberOfSheets = wb.getNumberOfSheets();
		List<String> list = new ArrayList<String>();

		for (int x = 0; x < numberOfSheets; x++) {
			XSSFSheet sheet = wb.getSheetAt(x);
			Row row = sheet.getRow(rowIndex);
			for (Cell cell : row) {
				if (cell != null) {
					list.add(cell.toString());
				} else {
					list.add("");
				}
			}
			if (list.size() != 0) {
				break;
			}
		}

		return list;
	}

	/**
	 * 得到表格中的一列的数据
	 * 
	 * @param file
	 * @param colIndex
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCol(File file, int colIndex) throws Exception {

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		int numberOfSheets = wb.getNumberOfSheets();
		List<String> list = new ArrayList<String>();

		for (int x = 0; x < numberOfSheets; x++) {
			XSSFSheet sheet = wb.getSheetAt(x);
			for (Row row : sheet) {
				if (row != null) {
					Cell cell = row.getCell(colIndex);
					if (cell != null) {
						list.add(cell.toString());
					} else {
						list.add("");
					}
				}
			}
			if (list.size() != 0) {
				break;
			}
		}

		return list;
	}

	/**
	 * 得到表格中的一列的数据
	 * 
	 * @param file
	 * @param colName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCol(File file, String colName) throws Exception {

		List<String> row = getRow(file,0);
		int index = 0;
		for(;index <= row.size();index++){
			if(row.get(index).equals(colName)){
				break;
			}
		}
		List<String> col = getCol(file,index);

		return new ArrayList<String>(col.subList(1,col.size()));
	}
}
