package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(enabled=true)
public class GetRowContentTest {

    GetRowContent command = new GetRowContent();

    @DataProvider(name = "data")
    public Object[][] dataTobeTested(){

        return new Object[][]{
                {"C:\\Users\\Administrator.EC2AMAZ-5L6MMDA\\Desktop\\Axos\\original\\Hamilton Appraisal Group - 2018 October_1.csv", 3.0, ""}
        };
    }

    @Test(dataProvider = "data")
    public void aTests(String CsvFilePath, Double RowNum, String Results){
        ListValue<String> d = command.action(CsvFilePath,RowNum);

        List<Value> myList = d.get();
        assertEquals(myList.size(),9);
        ArrayList<String> ExpectedRes = new ArrayList<String>();

        for(int i=0;i<myList.size();i++){
            System.out.println("Debug:"+myList.get(i));
        }

    }
}
