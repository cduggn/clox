package com.cdugga.logger;

public class Logger {


  public static void error(int line, String message) {
    report(line, "", message);
  }

  public static void report(int line, String where, String message) {
    System.err.println(
        "[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
  private static boolean hadError = false;

  public static boolean isHadError() {
    return hadError;
  }

  public static void setHadError(boolean hadError) {
    Logger.hadError = hadError;
  }
}
