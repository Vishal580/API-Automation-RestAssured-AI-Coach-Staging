package utils;

public class ExcelUtilsTest {

    public static void main(String[] args){

        String excelPath = "./data/TestData.xlsx";
        String sheetName = "sheet1";

        ExcelUtils excel = new ExcelUtils(excelPath, sheetName);

        excel.getRowCount();
        excel.getCellData(1,0);
        excel.getCellData(1,1);
    }
}
