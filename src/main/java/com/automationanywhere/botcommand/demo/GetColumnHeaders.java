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
import com.automationanywhere.botcommand.data.impl.NumberValue;
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
@CommandPkg(label="Get Column Headers", name="Get Column Headers", description="Get Column Headers as a list", icon="pkg.svg",
		node_label="Get Column Headers from CSV",
		return_type= DataType.LIST, return_label="Assign the output to variable", return_required=true)

public class GetColumnHeaders {

	private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

	@Execute
	public com.automationanywhere.botcommand.data.impl.ListValue<String> action(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "CSV Input File Path", default_value_type = STRING) @NotEmpty String CsvFilePath

			)
	{
		if("".equals(CsvFilePath)) {throw new BotCommandException(MESSAGES.getString("emptyInputString", "CsvFilePath"));}

		CSVReader reader = CsvUtils.ImportCsvFile(CsvFilePath);

		Iterator<String[]> iter = reader.iterator();
		boolean FirstRowProcessed = false;

		while(!FirstRowProcessed && iter.hasNext()){
			String[] Row = iter.next();
			FirstRowProcessed = true;
			ListValue<String> FinalList = new ListValue<String>();
			List<Value> ContainerList = new ArrayList<Value>();

			for(int i = 0;i < Row.length;i++){
				Value v = new StringValue(Row[i]);
				ContainerList.add(v);
			}
			FinalList.set(ContainerList);
			return FinalList;
		}
		return null;
	}


}
