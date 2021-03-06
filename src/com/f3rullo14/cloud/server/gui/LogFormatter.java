package com.f3rullo14.cloud.server.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
   private SimpleDateFormat field_98228_b;
   final LogAgent field_98229_a;

   public LogFormatter(LogAgent par1LogAgent) {
      this.field_98229_a = par1LogAgent;
      this.field_98228_b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   }

   public String format(LogRecord par1LogRecord) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(this.field_98228_b.format(par1LogRecord.getMillis()));
      if (LogAgent.getLoggerPrefix(this.field_98229_a) != null) {
         stringbuilder.append(LogAgent.getLoggerPrefix(this.field_98229_a));
      }

      stringbuilder.append(" [").append(par1LogRecord.getLevel().getName()).append("] ");
      stringbuilder.append(this.formatMessage(par1LogRecord));
      Throwable throwable = par1LogRecord.getThrown();
      if (throwable != null) {
         StringWriter stringwriter = new StringWriter();
         throwable.printStackTrace(new PrintWriter(stringwriter));
         stringbuilder.append(stringwriter.toString());
      }

      System.out.println("" + stringbuilder.toString());
      return stringbuilder.toString() + "\n";
   }
}
