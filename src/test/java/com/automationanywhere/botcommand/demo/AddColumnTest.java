package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class AddColumnTest {

    AddColumn command = new AddColumn();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, "federal_tax_id","","NEW_COL1", "true"},
                {OutputFile, "","bill_to","NEW_COL2", "true"}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, String ColBefore, String ColAfter, String NewColName ,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        BooleanValue d = command.action(CsvFilePath,ColBefore, ColAfter,NewColName);
        System.out.println("DEBUG:"+d.toString());
        assertEquals(d.toString(),Results);

    }
}
