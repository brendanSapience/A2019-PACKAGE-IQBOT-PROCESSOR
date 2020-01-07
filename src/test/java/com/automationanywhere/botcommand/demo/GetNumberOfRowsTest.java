package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetNumberOfRowsTest {

    GetNumberOfRows command = new GetNumberOfRows();

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {"C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv", 31.0}

        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, double Results){
        NumberValue NumRows = command.action(CsvFilePath);
        Double dRes = NumRows.get();
        System.out.println("Debug:"+NumRows);
        assertEquals(dRes,Results);

       // assertEquals(output,result);
    }
}
