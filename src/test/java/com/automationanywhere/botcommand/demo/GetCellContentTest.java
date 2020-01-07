package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetCellContentTest {

    GetCellContent command = new GetCellContent();

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {"C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv", 3.0,"statement", "10/10/18 desk 520 Ratananopadonchai, Nataya: 3410196"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, Double RowNum, String ColumnName,String Results){
        StringValue d = command.action(CsvFilePath,RowNum, ColumnName);

        assertEquals(d.toString(),Results);

    }
}
