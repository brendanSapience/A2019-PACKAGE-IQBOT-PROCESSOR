package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class DeleteRowFromRowNumberTest {

    DeleteRowFromRowNumber command = new DeleteRowFromRowNumber();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, 10.0, "true"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, Double RowNumber ,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        BooleanValue d = command.action(CsvFilePath,RowNumber);
        System.out.println("DEBUG:"+d.toString());
        assertEquals(d.toString(),Results);

    }
}
