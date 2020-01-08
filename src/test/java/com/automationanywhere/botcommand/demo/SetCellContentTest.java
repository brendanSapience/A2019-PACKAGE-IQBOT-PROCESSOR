package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class SetCellContentTest {

    SetCellContent command = new SetCellContent();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, 3.0,"statement","My New Content!", "true"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, Double RowNum, String ColumnName, String NewCellValue ,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        BooleanValue d = command.action(CsvFilePath,RowNum, ColumnName,NewCellValue);
        System.out.println("DEBUG:"+d.toString());
        assertEquals(d.toString(),Results);

    }
}
