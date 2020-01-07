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
package com.automationanywhere.botcommand.demo;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.utils.CsvUtils;
import com.opencsv.CSVReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.automationanywhere.commandsdk.model.DataType.STRING;

/**
 * @author Bren Sapience
 *
 */
@BotCommand
@CommandPkg(label="Get Row Content", name="Get Row Content", description="Get Row Content", icon="pkg.svg",
		node_label="Get Row Content",
		return_type= STRING, return_label="Assign the output to variable", return_required=true)

public class GetCellContent {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public StringValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.NUMBER) @Pkg(label = "Row Number", default_value_type = DataType.NUMBER) @NotEmpty Double RowNumber,
			@Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "Column Name", default_value_type = DataType.STRING) @NotEmpty String ColumnName
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if(RowNumber.equals(null)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "RowNumber"));}
		if("".equals(ColumnName)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "ColumnName"));}

		CSVReader reader = CsvUtils.ImportCsvFile(CsvFilePath);

		Iterator<String[]> iter = reader.iterator();
		int IntRowNumber = (int) Math.round(RowNumber);
		ArrayList<String> ColumnNames = new ArrayList<String>();

		int Idx = 0;
		boolean ColumnFound = false;
		int ColumnIndex = -1;

		while(iter.hasNext()){
			String[] Row = iter.next();

			if(Idx == 0){

				for(int i = 0;i<Row.length;i++){
					if(Row[i].toLowerCase().equals(ColumnName.toLowerCase())){
						ColumnFound = true;
						ColumnIndex = i;
					}
				}

			}
			if(ColumnFound && Idx == IntRowNumber){
				String CellValue = Row[ColumnIndex];
				return new StringValue(CellValue);

			}
			Idx = Idx + 1;

		}
		return null;
	}

}
