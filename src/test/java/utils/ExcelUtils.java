package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUtils(String excelPath, String sheetName){
        try{
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);
        }catch(Exception exp){
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    public static void getCellData(int rowNum, int colNum){
            //String value = sheet.getRow(1).getCell(0).getStringCellValue();

        DataFormatter formatter=new DataFormatter();
        Object value=formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
        System.out.println(value);
    }
    public static void getRowCount(){

        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("Number Of Rows: " + rowCount);

    }
}
