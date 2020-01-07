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

import com.automationanywhere.botcommand.data.impl.BooleanValue;
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
import com.opencsv.CSVWriter;

import java.io.StringWriter;
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
		return_type= DataType.BOOLEAN, return_label="Assign the output to variable", return_required=true)

public class SetCellContent {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public BooleanValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.NUMBER) @Pkg(label = "Row Number", default_value_type = DataType.NUMBER) @NotEmpty Double RowNumber,
			@Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "Column Name", default_value_type = DataType.STRING) @NotEmpty String ColumnName,
			@Idx(index = "4", type = AttributeType.TEXT) @Pkg(label = "New Content", default_value_type = DataType.STRING) @NotEmpty String NewCellContent
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if(RowNumber.equals(null)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "RowNumber"));}
		if("".equals(ColumnName)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "ColumnName"));}

		List<String[]> DataArray = CsvUtils.GetCsvFileAsList(CsvFilePath);
		int ColumnIdx = CsvUtils.GetHeaderIndexFromArray(DataArray,ColumnName);
		int IntRowNumber = (int) Math.round(RowNumber);

		String[] RowToChange = DataArray.get(IntRowNumber);

		RowToChange[ColumnIdx] = NewCellContent;

		DataArray.set(IntRowNumber,RowToChange);

		boolean res = CsvUtils.WriteArrayToCsvFile(CsvFilePath,DataArray);
		return new BooleanValue(res);

	}

}
