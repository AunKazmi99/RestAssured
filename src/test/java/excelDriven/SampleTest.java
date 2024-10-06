package excelDriven;

import io.cucumber.java.an.E;

import java.io.IOException;
import java.util.ArrayList;

public class SampleTest {

    public static void main(String[] args) throws IOException {
        ExcelDriven excelDriven = new ExcelDriven();
        ArrayList<String> data = excelDriven.getData("Sheet1","Profile");
        System.out.println(data);
    }
}
