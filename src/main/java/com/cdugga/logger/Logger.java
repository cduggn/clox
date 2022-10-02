package com.cdugga.logger;

import com.cdugga.scanner.Token;
import com.cdugga.scanner.TokenType;

public class Logger {


  public static void error(int line, String message) {
    report(line, "", message);
  }

  public static void report(int line, String where, String message) {
    System.err.println(
        "[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
  public static void error(Token token, String message) {
    if (token.type == TokenType.EOF) {
      report(token.line, " at end", message);
    } else {
      report(token.line, " at '" + token.lexeme + "'", message);
    }
  }

  private static boolean hadError = false;

  public static boolean isHadError() {
    return hadError;
  }

  public static void setHadError(boolean hadError) {
    Logger.hadError = hadError;
  }
}
