package com.htjy.execl;

import com.htjy.dom.Carrier;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by willz on 9/15/2017.
 */
public class ExeclProcess {
    private static String xlsName = "src/resource/test.xls";
    private static String xlsxName = "src/resource/test.xlsx";

    public static void main(String[] args) throws IOException, SAXException {
        List<Carrier> carrierList = excelToList(xlsName);
        carrierList.stream().forEach((carrier) -> {
            System.out.println(carrier.toString());
        });
        ExeclToJsonHandler execlToJsonHandler = new ExeclToJsonHandler<Carrier>();
        JSONArray jsonArray = execlToJsonHandler.buildJsonByFileName(Carrier.class, xlsName);
        System.out.println(jsonArray.toString());
    }

    private static List<Carrier> excelToList(String excelName) {
        List<Carrier> carrierList = new ArrayList<>();
        Workbook workbook = null;
        FileInputStream fileInputStream = null;
        try {

            fileInputStream = new FileInputStream(excelName);
            if (excelName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (excelName.endsWith("xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else {
                throw new IllegalArgumentException("not a excel file.");
            }
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowI = sheet.rowIterator();
            while (rowI.hasNext()) {
                Row row = rowI.next();
                if (NumberUtils.isNumber(String.valueOf(row.getCell(0)))) {
                    Carrier carrier = new Carrier();
                    carrier.setCarrierFamilyId(Double.valueOf(String.valueOf(row.getCell(0))).intValue());
                    carrier.setCarrierFamilyName(String.valueOf(row.getCell(1)).trim());
                    carrier.setGenericCarrierId(Double.valueOf(String.valueOf(row.getCell(2))).intValue());
                    carrier.setState(String.valueOf(row.getCell(3)).trim());
                    carrier.setProductLine(String.valueOf(row.getCell(4)).trim());
                    carrier.setCarrierName(String.valueOf(row.getCell(5)).trim());
                    carrierList.add(carrier);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                fileInputStream.close();
                workbook.close();
            } catch (Exception e) {

            }
        }
        return carrierList;
    }
}
