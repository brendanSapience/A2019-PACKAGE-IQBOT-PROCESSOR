package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetCellContentTest {

    GetCellContent command = new GetCellContent();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, 3.0,"statement", "10/10/18 desk 520 Moran: 3410112"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, Double RowNum, String ColumnName,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        StringValue d = command.action(CsvFilePath,RowNum, ColumnName);

        assertEquals(d.toString(),Results);

    }
}
