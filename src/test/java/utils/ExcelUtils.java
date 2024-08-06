package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static void main(String args[]){
        getRowCount();
        getCellData();
    }

    public static void getCellData(){
        try {
            String excelPath = "./data/TestData.xlsx";

            XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            //String value = sheet.getRow(1).getCell(0).getStringCellValue();

            DataFormatter formatter=new DataFormatter();
            Object value=formatter.formatCellValue(sheet.getRow(1).getCell(0));
            System.out.println(value);
        }catch (Exception exe){
            System.out.println(exe.getMessage());
            System.out.println(exe.getCause());
            exe.printStackTrace();
        }
    }
    public static void getRowCount(){
        try {
            String excelPath = "./data/TestData.xlsx";

            XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            int rowCount = sheet.getPhysicalNumberOfRows();
            System.out.println("Number Of Rows: " + rowCount);
        }catch (Exception exe){
            System.out.println(exe.getMessage());
            System.out.println(exe.getCause());
            exe.printStackTrace();
        }
    }
}
