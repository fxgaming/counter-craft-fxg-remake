package com.f3rullo14.cloud.server.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class TextAreaLogHandlerINNER1 extends Formatter {
   final TextAreaLogHandler field_120031_a;

   TextAreaLogHandlerINNER1(TextAreaLogHandler par1TextAreaLogHandler) {
      this.field_120031_a = par1TextAreaLogHandler;
   }

   public String format(LogRecord par1LogRecord) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(" [" + par1LogRecord.getLevel().getName() + "] ");
      stringbuilder.append(this.formatMessage(par1LogRecord));
      stringbuilder.append('\n');
      Throwable throwable = par1LogRecord.getThrown();
      if (throwable != null) {
         StringWriter stringwriter = new StringWriter();
         throwable.printStackTrace(new PrintWriter(stringwriter));
         stringbuilder.append(stringwriter.toString());
      }

      return stringbuilder.toString();
   }
}
