package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class DeleteRowsWithRegexTest {

    DeleteRowsWithRegex command = new DeleteRowsWithRegex();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, "^\\d{2}.*","statement", "true"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, String RegexExp,String ColumnName ,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }

        BooleanValue d = command.action(CsvFilePath,RegexExp,ColumnName);
        System.out.println("DEBUG:"+d.toString());
        assertEquals(d.toString(),Results);

    }
}
