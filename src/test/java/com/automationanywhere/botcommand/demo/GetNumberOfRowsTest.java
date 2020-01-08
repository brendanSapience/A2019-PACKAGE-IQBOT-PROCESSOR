package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetNumberOfRowsTest {

    GetNumberOfRows command = new GetNumberOfRows();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, 31.0}

        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, double Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        NumberValue NumRows = command.action(CsvFilePath);
        Double dRes = NumRows.get();
        System.out.println("Debug:"+NumRows);
        assertEquals(dRes,Results);

       // assertEquals(output,result);
    }
}
