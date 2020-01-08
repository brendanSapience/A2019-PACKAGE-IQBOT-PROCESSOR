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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      }
   }

    public static List<Integer> getRange(int from, int to) {

        List<Integer> numbers = new ArrayList<>(to-from);

        for (int i = from;i < from+to;++i) {
            numbers.add(i);
        }
        return numbers;
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
        List<String[]> HorizontalDataList = new ArrayList<String[]>();
        try{
            CSVReader reader = ImportCsvFile(CsvFilePath);
            HorizontalDataList = reader.readAll();
        }catch(IOException e){

            return HorizontalDataList;
        }
        return  HorizontalDataList;
    }

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    //
    public static boolean TransformColumnWithRegex(String CsvFilePath, String ColumnName,String RegexExp) throws FileNotFoundException,IOException {
        CSVReader reader = new CSVReader(new FileReader(CsvFilePath));
        List<String[]> DataList = reader.readAll();
        List<String[]> NewDataList = new ArrayList<String[]>();

        int ColumnIdx = GetHeaderIndexFromArray(DataList,ColumnName);

        if(ColumnIdx < 0){
            System.out.println("Could not find column:"+ColumnName);
            return false;
        }
        NewDataList.add(DataList.get(0));

        for (int idx = 1;idx < DataList.size();idx++){
            String[] Row = DataList.get(idx);

            String CellToCheck = Row[ColumnIdx];

            List<String> allMatches = new ArrayList<String>();
            boolean DoesRegexMatchCell = false;
            Matcher m = Pattern.compile(RegexExp).matcher(CellToCheck);

            if(m.find()){
                if(m.groupCount()>0){
                    //System.out.println("Group Count:"+m.groupCount());
                    Row[ColumnIdx] = m.group(1);
                }

            }

            NewDataList.add(Row);

        }

        return WriteArrayToCsvFile(CsvFilePath,NewDataList);

    }

    public static boolean DeleteOrKeepRowWithRegex(String CsvFilePath, String ColumnName,String RegexExp,boolean DeleteOperation) throws FileNotFoundException,IOException {
        CSVReader reader = new CSVReader(new FileReader(CsvFilePath));
        List<String[]> DataList = reader.readAll();
        List<String[]> NewDataList = new ArrayList<String[]>();

        int ColumnIdx = GetHeaderIndexFromArray(DataList,ColumnName);

        if(ColumnIdx < 0){
            System.out.println("Could not find column:"+ColumnName);
            return false;
        }
        NewDataList.add(DataList.get(0));
        for (int idx = 1;idx < DataList.size();idx++){
            String[] Row = DataList.get(idx);

            String CellToCheck = Row[ColumnIdx];

            List<String> allMatches = new ArrayList<String>();
            boolean DoesRegexMatchCell = false;
            Matcher m = Pattern.compile(RegexExp).matcher(CellToCheck);
            while (m.find()) {
                allMatches.add(m.group());
            }

            if(allMatches.size() > 0){
                DoesRegexMatchCell = true;
            }

            if(DeleteOperation){
                if(!DoesRegexMatchCell){
                    NewDataList.add(Row);
                }
            }else{
                if(DoesRegexMatchCell){
                    NewDataList.add(Row);
                }
            }

        }

        return WriteArrayToCsvFile(CsvFilePath,NewDataList);

    }

    public static boolean DeleteRow(String CsvFilePath,int RowIndex) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(CsvFilePath));
        List<String[]> DataList = reader.readAll();

        DataList.remove(RowIndex);
        return WriteArrayToCsvFile(CsvFilePath,DataList);

    }

    public static boolean DeleteColumn(String CsvFilePath,int ColumnIndex) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(CsvFilePath));
        List<String[]> DataList = reader.readAll();
        List<String[]> NewDataList = reader.readAll();

        for (int idx = 0;idx < DataList.size();idx++){
            String[] Row = DataList.get(idx);

            ArrayList list = new ArrayList(Arrays.asList(Row));
            list.remove(ColumnIndex); // Add the new element here
            NewDataList.add(GetStringArray(list));

        }
        return WriteArrayToCsvFile(CsvFilePath,NewDataList);

    }

    public static boolean AddEmptyColumn(String CsvFilePath,String PlaceHolderValue,int IndexToInsertColumnAt,String ColumnName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(CsvFilePath));
        List<String[]> DataList = reader.readAll();
        List<String[]> NewDataList = reader.readAll();

        // Add Header first
        ArrayList Headers = new ArrayList(Arrays.asList(DataList.get(0)));
        Headers.add(IndexToInsertColumnAt,ColumnName); // Add the new element here
        NewDataList.add(GetStringArray(Headers));


        for (int idx = 1;idx < DataList.size();idx++){
            String[] Row = DataList.get(idx);

            ArrayList list = new ArrayList(Arrays.asList(Row));
            list.add(IndexToInsertColumnAt,PlaceHolderValue); // Add the new element here
            NewDataList.add(GetStringArray(list));

        }
        return WriteArrayToCsvFile(CsvFilePath,NewDataList);

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
