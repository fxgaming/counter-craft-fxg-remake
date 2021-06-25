package com.f3rullo14.cloud.server.gui;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogAgent {
   private final Logger serverLogger;
   private final String logFile;
   private final String loggerName;
   private final String loggerPrefix;

   public LogAgent(String par1Str, String par2Str, String par3Str) {
      this.serverLogger = Logger.getLogger(par1Str);
      this.loggerName = par1Str;
      this.loggerPrefix = par2Str;
      this.logFile = par3Str;
   }

   public void setupLogger() {
      LogFormatter logformatter = new LogFormatter(this);

      try {
         this.serverLogger.setUseParentHandlers(false);
         FileHandler filehandler = new FileHandler(this.logFile, true);
         filehandler.setFormatter(logformatter);
         this.serverLogger.addHandler(filehandler);
      } catch (Exception var3) {
         this.serverLogger.log(Level.WARNING, "Failed to log " + this.loggerName + " to " + this.logFile, var3);
      }

   }

   public void logInfo(String par1Str) {
      this.serverLogger.log(Level.INFO, par1Str);
   }

   public Logger getServerLogger() {
      return this.serverLogger;
   }

   public void logWarning(String par1Str) {
      this.serverLogger.log(Level.WARNING, par1Str);
   }

   public void logWarningFormatted(String par1Str, Object... par2ArrayOfObj) {
      this.serverLogger.log(Level.WARNING, par1Str, par2ArrayOfObj);
   }

   public void logWarningException(String par1Str, Throwable par2Throwable) {
      this.serverLogger.log(Level.WARNING, par1Str, par2Throwable);
   }

   public void logSevere(String par1Str) {
      this.serverLogger.log(Level.SEVERE, par1Str);
   }

   public void logSevereException(String par1Str, Throwable par2Throwable) {
      this.serverLogger.log(Level.SEVERE, par1Str, par2Throwable);
   }

   public void logFine(String par1Str) {
      this.serverLogger.log(Level.FINE, par1Str);
   }

   static String getLoggerPrefix(LogAgent par0LogAgent) {
      return "[CDCloud]";
   }
}
