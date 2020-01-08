package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetColumnHeadersTest {

    GetColumnHeaders command = new GetColumnHeaders();
    String OriginalFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv";
    String OutputFile = "C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\Hamilton Appraisal Group - 2018 October_1.csv";

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {OutputFile, ""}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath,String Results){
        try{
            FileUtils.copyFile(new File(OriginalFile),new File(OutputFile));
        }catch(IOException e){
            return;
        }
        ListValue<String> d = command.action(CsvFilePath);

        //List<Value> myList = output.get();
        List<Value> myList = d.get();
        assertEquals(myList.size(),9);
        ArrayList<String> ExpectedRes = new ArrayList<String>();

        ExpectedRes.add("statement_number");ExpectedRes.add("federal_tax_id");
        ExpectedRes.add("bill_to");ExpectedRes.add("attn");
        ExpectedRes.add("statement_covers");ExpectedRes.add("statement");
        ExpectedRes.add("subtotal");ExpectedRes.add("Result");;ExpectedRes.add("File Path");

        ArrayList<String> ActualRes = new ArrayList<String>();
        for(int i=0;i<myList.size();i++){
            System.out.println("Debug:"+myList.get(i));
            String s = myList.get(i).toString();
            ActualRes.add(s);
        }
        assertEquals(ActualRes,ExpectedRes);

    }
}
