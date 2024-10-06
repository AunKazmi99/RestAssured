package excelDriven;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelDriven {

    public ArrayList<String> getData(String sheetName, String testCaseName) throws IOException {
        ArrayList<String> data = new ArrayList<String>();

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\AunAbbas\\" +
                "Desktop\\RestAssuredDataDrivenTesting.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        //int sheetCount = workbook.getNumberOfSheets();
        XSSFSheet sheet = workbook.getSheet(sheetName);
        /*for (int i = 0; i < sheetCount; i++) {
            if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase("Sheet1")) {
                sheet = workbook.getSheetAt(i);
            }
        }*/
        Iterator<Row> rowsIterator = sheet.iterator();
        Row firstRow = rowsIterator.next();
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        int k = 0, column = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue().equalsIgnoreCase(testCaseName)) {
                column = k;
            }
            k++;

        }

        while (rowsIterator.hasNext()) {
            Row row = rowsIterator.next();
            if (row.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                Iterator<Cell> cellIterator1 = row.cellIterator();
                while (cellIterator1.hasNext()) {
                    Cell cell = cellIterator1.next();
                    if (cell.getCellType() == CellType.STRING) {
                        data.add(cell.getStringCellValue());
                    } else {
                        data.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
                    }
                }
            }
        }
        return data;
    }
}
