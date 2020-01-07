/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 *
 */
package com.automationanywhere.utils;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bren Sapience
 *
 */

public class CsvUtils {

   public static CSVReader ImportCsvFile(String CsvFilePath){

      try {
             FileInputStream fis = new FileInputStream(CsvFilePath);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr);
             return reader;
      } catch (FileNotFoundException e) {
         e.printStackTrace();
         return(null);
      } catch (IOException e) {
         e.printStackTrace();
         return(null);
      }
   }

   public static int GetHeaderIndexFromArray(List<String[]> data, String ColumnName){
       String[] headers = data.get(0);
        boolean ColumnFound = false;

       for(int i=0;i<headers.length;i++){
           if(headers[i].toLowerCase().equals(ColumnName.toLowerCase())){
               ColumnFound = true;
               return i;
           }
       }

       return -1;
   }

    public static List<String[]> GetCsvFileAsList(String CsvFilePath) {
        List<String[]> DataList = new ArrayList<String[]>();
        try{
            CSVReader reader = ImportCsvFile(CsvFilePath);
            DataList = reader.readAll();
        }catch(IOException e){
            return DataList;
        }
        return  DataList;
    }

    public static boolean WriteArrayToCsvFile(String CsvFilePath,List<String[]> DataArray){
       try{
           FileWriter outputfile = new FileWriter(CsvFilePath);

           // create CSVWriter with '|' as separator
           CSVWriter writer = new CSVWriter(outputfile, ',',
                   '"',
                   CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                   CSVWriter.DEFAULT_LINE_END);

           writer.writeAll(DataArray);

           // closing writer connection
           writer.close();

       }catch(IOException e){
           return false;
       }
        return true;
    }
}
