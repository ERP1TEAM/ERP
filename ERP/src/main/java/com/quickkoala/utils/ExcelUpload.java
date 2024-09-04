package com.quickkoala.utils;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public class ExcelUpload {

	public Sheet uploadExcel(@RequestParam("excel") MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
		    Workbook workbook = WorkbookFactory.create(inputStream);
		    Sheet sheet = workbook.getSheetAt(0);
		    
		    return sheet;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
}
