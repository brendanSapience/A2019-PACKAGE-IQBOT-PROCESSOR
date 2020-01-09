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
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.utils.CsvUtils;

import java.io.IOException;
import java.util.List;

import static com.automationanywhere.commandsdk.model.DataType.STRING;

/**
 * @author Bren Sapience
 *
 */
@BotCommand
@CommandPkg(label="Copy Column", name="Copy Column", description="Copy Column and its content", icon="pkg.svg",
		node_label="Copy Column",
		return_type= DataType.BOOLEAN, return_label="Assign the output to variable", return_required=true)

public class CopyColumn {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public BooleanValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Column To Copy", default_value_type = DataType.STRING)  String ColumnToCopy,
			@Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "New Column Name", default_value_type = DataType.STRING) @NotEmpty String NewColumnName
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if("".equals(ColumnToCopy)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "ColumnToCopy"));}
		if("".equals(NewColumnName)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "NewColumnName"));}


		List<String[]> DataArray = CsvUtils.GetCsvFileAsList(CsvFilePath);

		int ColumnIdx = CsvUtils.GetHeaderIndexFromArray(DataArray,ColumnToCopy);

		try{
			CsvUtils.CopyColumn(CsvFilePath,ColumnIdx,NewColumnName);

		}catch(IOException e){
			return new BooleanValue("false");
		}

		boolean res = true;
		return new BooleanValue(Boolean.toString(res));

	}

}
