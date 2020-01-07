package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetColumnHeadersTest {

    GetColumnHeaders command = new GetColumnHeaders();

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {"C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv", ""}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath,String Results){
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
