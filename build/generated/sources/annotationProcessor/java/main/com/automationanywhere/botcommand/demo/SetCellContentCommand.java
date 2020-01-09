package com.automationanywhere.botcommand.demo;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Double;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SetCellContentCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(SetCellContentCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    SetCellContent command = new SetCellContent();
    if(parameters.get("CsvFilePath") == null || parameters.get("CsvFilePath").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","CsvFilePath"));
    }

    if(parameters.get("RowNumber") == null || parameters.get("RowNumber").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","RowNumber"));
    }

    if(parameters.get("ColumnName") == null || parameters.get("ColumnName").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","ColumnName"));
    }

    if(parameters.get("NewCellContent") == null || parameters.get("NewCellContent").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","NewCellContent"));
    }

    if(parameters.get("CsvFilePath") != null && parameters.get("CsvFilePath").get() != null && !(parameters.get("CsvFilePath").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","CsvFilePath", "String", parameters.get("CsvFilePath").get().getClass().getSimpleName()));
    }
    if(parameters.get("RowNumber") != null && parameters.get("RowNumber").get() != null && !(parameters.get("RowNumber").get() instanceof Double)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","RowNumber", "Double", parameters.get("RowNumber").get().getClass().getSimpleName()));
    }
    if(parameters.get("ColumnName") != null && parameters.get("ColumnName").get() != null && !(parameters.get("ColumnName").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","ColumnName", "String", parameters.get("ColumnName").get().getClass().getSimpleName()));
    }
    if(parameters.get("NewCellContent") != null && parameters.get("NewCellContent").get() != null && !(parameters.get("NewCellContent").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","NewCellContent", "String", parameters.get("NewCellContent").get().getClass().getSimpleName()));
    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.action(parameters.get("CsvFilePath") != null ? (String)parameters.get("CsvFilePath").get() : (String)null ,parameters.get("RowNumber") != null ? (Double)parameters.get("RowNumber").get() : (Double)null ,parameters.get("ColumnName") != null ? (String)parameters.get("ColumnName").get() : (String)null ,parameters.get("NewCellContent") != null ? (String)parameters.get("NewCellContent").get() : (String)null ));
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
