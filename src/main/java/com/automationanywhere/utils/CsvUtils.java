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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
}
