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
@CommandPkg(label="Add Column", name="Add Column", description="Add Column to a CSV before or after an existing column", icon="pkg.svg",
		node_label="Add Column",
		return_type= DataType.BOOLEAN, return_label="Assign the output to variable", return_required=true)

public class AddColumn {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public BooleanValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Insert New Column Before", default_value_type = DataType.STRING)  String InsertColumnBefore,
			@Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "Insert New Column After", default_value_type = DataType.STRING)  String InsertColumnAfter,
			@Idx(index = "4", type = AttributeType.TEXT) @Pkg(label = "New Column Name", default_value_type = DataType.STRING) @NotEmpty String NewColumnName
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if("".equals(InsertColumnBefore) && "".equals(InsertColumnAfter)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "Column After or Before"));}
		if("".equals(NewColumnName)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "NewColumnName"));}


		List<String[]> DataArray = CsvUtils.GetCsvFileAsList(CsvFilePath);

		int NewColumnIdx = -1;
		if(!"".equals(InsertColumnBefore)){
			int ColumnIdx = CsvUtils.GetHeaderIndexFromArray(DataArray,InsertColumnBefore);
			NewColumnIdx = ColumnIdx;
			//System.out.println("DEBUG BEFORE:"+ColumnIdx);
		}
		if(!"".equals(InsertColumnAfter)){
			int ColumnIdx = CsvUtils.GetHeaderIndexFromArray(DataArray,InsertColumnAfter);
			NewColumnIdx = ColumnIdx+1;
			//System.out.println("DEBUG AFTER:"+ColumnIdx);
		}
		try{
			CsvUtils.AddEmptyColumn(CsvFilePath,"",NewColumnIdx,NewColumnName);

		}catch(IOException e){
			return new BooleanValue("false");
		}

		boolean res = true;
		return new BooleanValue(Boolean.toString(res));

	}

}
