package com.htjy.execl;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * Created by willz on 9/22/2017.
 */
public class ExeclToJsonHandler<T> {
    private  ExcelToListHandler<T> excelToListHandler = new  ExcelToListHandler<T>();
    public JSONArray buildJsonByFileName(Class<T> clazz, String fileName) {
        return buildJsonByWorkbook(clazz, ExcelUtil.readExcelFile(fileName));
    }

    public JSONArray buildJsonByWorkbook(Class<T> clazz, Workbook workbook) {
        try {
            List<T> list = excelToListHandler.buildListByWorkbook(clazz, workbook);
            return new JSONArray(list);
        } finally {
            try {
                workbook.close();
            } catch (IOException ie) {
                System.out.println("close workbook error!" + ie.getMessage());
            }
        }
    }

}
