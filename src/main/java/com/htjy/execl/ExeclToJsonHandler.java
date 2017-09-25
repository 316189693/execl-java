package com.htjy.execl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by willz on 9/22/2017.
 */
public class ExeclToJsonHandler<T> {
    private String fileName;
    private JSONArray jsons;
    private Workbook workbook;

    public JSONArray buildJsonByFileName(Class<T> clazz, String fileName) {
        return buildJsonByWorkbook(clazz, ExcelUtil.readExcelFile(fileName));
    }

    public JSONArray buildJsonByWorkbook(Class<T> clazz, Workbook workbook) {
        try {
            Map<String, Integer> keyMap = ExcelUtil.trasferExcelRowToStringArray(workbook);
            Iterator<Row> rowIterator = workbook.getSheetAt(0).rowIterator();
            List<T> list = new ArrayList();
            boolean firstRow = true;
            while (rowIterator.hasNext()) {
                if (firstRow) {
                    rowIterator.next();
                    firstRow = false;
                }
                list.add(generateDataByRow(clazz, keyMap, rowIterator.next()));
            }
            return new JSONArray(list);
        } finally {
            try {
                workbook.close();
            } catch (IOException ie) {
                System.out.println("close workbook error!" + ie.getMessage());
            }
        }
    }

    public <T> T generateDataByRow(Class<T> clazz, Map<String, Integer> keyMap, Row row) {
        T newInstance = null;
        try {
            newInstance = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Set<String> keys = keyMap.keySet();
            Method method = null;
            for (Field field : fields) {
                try {
                    method = clazz.getMethod(ExcelUtil.parSetName(field.getName()), field.getType());
                } catch (NoSuchMethodException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (keys.contains(field.getName())) {
                    if ("String".equals(field.getType().getSimpleName())) {
                        method.invoke(newInstance, row.getCell(keyMap.get(field.getName())).getStringCellValue());
                    } else if ("int".equals(field.getType().getSimpleName())) {
                        method.invoke(newInstance, Integer.valueOf(Double.valueOf(String.valueOf(row.getCell(keyMap.get(field.getName())))).intValue()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newInstance;
    }
}
