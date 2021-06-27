package com.qa.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	static HashMap<String, String> hashMap;	
	public static FileInputStream fileInputStream;
	public static FileOutputStream fo;
	public static Workbook wb;
	public static Sheet ws;
	public static Row row;
	public static Cell cell;
	
	private static Workbook getWorkBook(String path) throws Exception{
		fileInputStream = new FileInputStream(path);
		if(path.endsWith("xlsx")){
			wb=new XSSFWorkbook(fileInputStream);
		}else{
			wb=new HSSFWorkbook(fileInputStream);
		}
		return wb;
	}

	public static int getRowCount(String xlfile,String xlsheet) throws IOException
	{
		fileInputStream=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fileInputStream);
		ws=wb.getSheet(xlsheet);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fileInputStream.close();
		return rowcount;		
	}


	public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fileInputStream=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fileInputStream);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fileInputStream.close();
		return cellcount;
	}

	public static String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fileInputStream=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fileInputStream);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		String data;
		try 
		{
			DataFormatter formatter =new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) 
		{
			data="";
		}
		wb.close();
		fileInputStream.close();
		return data;
	}

	public static void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
	{
		fileInputStream=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fileInputStream);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);		
		wb.close();
		fileInputStream.close();
		fo.close();
	}

	public static HashMap<String, HashMap<String, String>> getData(String path,String sheetName) throws Exception{
		HashMap<String, String> HM=new HashMap<String, String>();
		HashMap<String, HashMap<String, String>> HMM=new HashMap<String, HashMap<String, String>>();
		Workbook wObj=getWorkBook(path);
		Sheet sheetObj=wObj.getSheet(sheetName);
		int rowCount=sheetObj.getLastRowNum();
		for(int i=1;i<=rowCount;i++){
			Row rowObj=sheetObj.getRow(i);
			String tcName=rowObj.getCell(0).getStringCellValue();
			int cellCount=rowObj.getLastCellNum()-1;
			for(int j=1;j<=cellCount;j+=2){
				Cell cellObj=rowObj.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String key=cellObj.getStringCellValue();
				Cell cellObjVal=rowObj.getCell(j+1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String value=cellObjVal.getStringCellValue();
				HM.put(key, value);
			}
			HMM.put(tcName, HM);
		}
		return HMM;
	}	


	public static HashMap<String, String> getTestCaseData(String path,String sheetName,String tcName) throws Exception{
		hashMap=new HashMap<String, String>();
		Workbook wObj=getWorkBook(path);
		Sheet sheetObj=wObj.getSheet(sheetName);
		int rowCount=sheetObj.getLastRowNum();
		for(int i=1;i<=rowCount;i++){
			Row rowObj=sheetObj.getRow(i);
			String tcname=rowObj.getCell(0).getStringCellValue();
			if(tcName.equalsIgnoreCase(tcname)){
				int cellCount=rowObj.getLastCellNum()-1;
				for(int j=1;j<=cellCount;j+=2){
					Cell cellObj=rowObj.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					String key=cellObj.getStringCellValue();
					Cell cellObjVal=rowObj.getCell(j+1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					String value=cellObjVal.getStringCellValue();
					hashMap.put(key, value);
				}
				break;
			}
		}
		return hashMap;
	}		
}

