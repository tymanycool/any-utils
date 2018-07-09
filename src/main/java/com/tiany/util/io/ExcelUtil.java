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
public abstract class ExcelUtil {
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
	public static List<String> getColumn(File file, int colIndex) throws Exception {

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
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getColumn(File file, String columnName) throws Exception {

		List<String> row = getRow(file,0);
		int index = 0;
		for(;index <= row.size();index++){
			if(row.get(index).equals(columnName)){
				break;
			}
		}
		List<String> col = getColumn(file,index);

		return new ArrayList<String>(col.subList(1,col.size()));
	}

	/**
	 * 导出Excel(2003及以下版本)
	 *
	 * @param lists
	 *            待导出的数据
	 * @param columnNameMaping
	 *            字段的名字与描述 eg. columnNameMaping.put("age","年龄")
	 * @param sheetName
	 * @param sheetSize eg. -1
	 * @param out
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean exportExcel(List<Map<String, Object>> lists, Map<String, String> columnNameMaping,
									  String sheetName, int sheetSize, OutputStream out) {
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象

		// excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}

		double sheetNo = Math.ceil(lists.size() / sheetSize);// 取出一共有多少个sheet.
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
			if (sheetNo == 0) {
				workbook.setSheetName(index, sheetName);
			} else {
				workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
			}
			HSSFRow row;
			HSSFCell cell;// 产生单元格

			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			Iterator<Entry<String, String>> header = columnNameMaping.entrySet().iterator();
			int i = 0;
			while (header.hasNext()) {
				cell = row.createCell(i);// 创建列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
				Entry<String, String> next = header.next();
				cell.setCellValue(next.getValue());// 写入列名
				i++;
			}

			int startNo = index * sheetSize;
			int endNo = Math.min(startNo + sheetSize, lists.size());

			// 写入各条记录,每条记录对应excel表中的一行
			for (i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				Map vo = lists.get(i); // 得到导出对象.
				header = columnNameMaping.entrySet().iterator();
				int j = 0;
				while (header.hasNext()) {
					cell = row.createCell(j);// 创建cell
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					Entry<String, String> next = header.next();
					String value = String.valueOf(vo.get(next.getKey()));
					cell.setCellValue("null".equals(value) ? "" : value);// 如果数据存在就填入,不存在填入空格.
					j++;
				}
			}
		}
		try {
			out.flush();
			workbook.write(out);
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
