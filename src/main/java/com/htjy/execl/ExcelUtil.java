package com.htjy.execl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by willz on 9/22/2017.
 */
public class ExcelUtil {
    private final static String XLSX_SUFFIX = "xlsx";
    private final static String XLS_SUFFIX = "xls";

    public static Workbook readExcelFile(String excelPath) {
        if (StringUtils.isEmpty(excelPath) || !(StringUtils.endsWith(excelPath, XLSX_SUFFIX) || StringUtils.endsWith(excelPath, XLS_SUFFIX))) {
            throw new IllegalArgumentException("Input excel path is null or not a formal excel file:" + excelPath);
        }
        Workbook workbook = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(excelPath);
            if (excelPath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (excelPath.endsWith("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + excelPath);
        } catch (IOException e) {
            System.out.println("read file into workbook is fail:" + e.getMessage());
        } finally {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                System.out.println("close file error ");
            }
        }
        return workbook;
    }

    public static Map<String, Integer> trasferExcelRowToStringArray(Workbook workbook) {
        if (workbook == null || workbook.getSheetAt(0) == null) {
            throw new IllegalArgumentException("workbook is null");
        }
        return trasferExcelRowToStringArray(workbook.getSheetAt(0).getRow(0));

    }

    private static Map<String, Integer> trasferExcelRowToStringArray(Row row) {
        int column = row.getPhysicalNumberOfCells();
        if (column <= 0) {
            return null;
        }
        Map<String, Integer> keyHashMap = new HashMap<>();
        Iterator<Cell> iterable = row.cellIterator();
        Cell cell = null;
        int index = 0;
        while (iterable.hasNext()) {
            cell = iterable.next();
            keyHashMap.put(cell.getStringCellValue(), index);
            index++;
        }
        return keyHashMap;
    }

    public static String parSetName(String fieldName) {
        Pattern pattern = Pattern.compile("_");
        return "set" + Arrays.asList(pattern.split(fieldName)).stream().map((String item) -> {
            return StringUtils.isEmpty(item) ? "" : (String.valueOf(item.charAt(0)).toUpperCase() + item.substring(1));
        }).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("_");

        String test0 = "carrier_family_id_str";
        String test1 = "_carrier_family_id_str";
        String test2 = "carrierFamilyId";
        System.out.println(parSetName(test0));
    }
}
