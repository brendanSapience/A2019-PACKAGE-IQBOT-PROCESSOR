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
import java.util.ArrayList;
import java.util.List;

import static com.automationanywhere.commandsdk.model.DataType.STRING;

/**
 * @author Bren Sapience
 *
 */
@BotCommand
@CommandPkg(label="Delete Row", name="Delete Row", description="Delete a Row selected by number", icon="pkg.svg",
		node_label="Delete Row",
		return_type= DataType.BOOLEAN, return_label="Assign the output to variable", return_required=true)

public class DeleteRowFromRowNumber {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public BooleanValue action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath,
			@Idx(index = "2", type = AttributeType.NUMBER) @Pkg(label = "Row Number", default_value_type = DataType.NUMBER) @NotEmpty Double RowNumber
			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}
		if("".equals(RowNumber)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "RowNumber"));}

		int IntRowNumber = (int) Math.round(RowNumber);
		try{
			boolean res = CsvUtils.DeleteRow(CsvFilePath,IntRowNumber);
			return new BooleanValue(Boolean.toString(res));
		}catch(IOException e){
			return  new BooleanValue("false");
		}


	}

}
