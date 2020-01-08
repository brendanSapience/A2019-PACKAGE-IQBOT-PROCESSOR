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

import static com.automationanywhere.commandsdk.model.DataType.STRING;

/**
 * @author Bren Sapience
 *
 */
@BotCommand
@CommandPkg(label="Delete Rows with Regex", name="Delete Rows with Regex", description="Delete Rows that contain cells matching a regular expression", icon="pkg.svg",
		node_label="Delete Rows with Regex",
		return_type= DataType.BOOLEAN, return_label="Assign the output to variable", return_required=true)

public class DeleteRowsWithRegex {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public BooleanValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.REGEX) @Pkg(label = "Regular Expression", default_value_type = STRING) @NotEmpty String Regex,
			@Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "Column Name To Inspect", default_value_type = STRING) @NotEmpty String ColumnName
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if("".equals(Regex)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "Regex"));}

		try{
			boolean res = CsvUtils.DeleteOrKeepRowWithRegex(CsvFilePath,ColumnName,Regex,true);
			return new BooleanValue(Boolean.toString(res));
		}catch(IOException e){
			return  new BooleanValue("false");
		}


	}

}
