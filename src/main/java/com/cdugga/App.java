package com.cdugga;

import com.cdugga.scanner.Token;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.cdugga.scanner.Scanner;


/**
 * Hello world!
 */
public class App {

  private static Logger logger = new Logger();

  public static void main(String[] args) throws IOException {

    if (args.length > 1) {
      System.out.println("Usage: clox [script]");
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
    System.out.println("Hello World!");
  }


  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));

    if (logger.isHadError()) System.exit(65);
  }

  private static void runPrompt() throws IOException {

    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    while (true) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) {
        break;
      }
      run(line);
      logger.setHadError(false);
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source, logger);
    List<Token> tokens = scanner.scanTokens(); // to do create scanner and scan tokens, and token class

    for (Token token : tokens) {
      System.out.println(token);
    }
  }

//  static void error(int line, String message) {
//    report(line, "", message);
//  }
//
//  private static void report(int line, String where, String message) {
//    System.err.println(
//        "[line " + line + "] Error" + where + ": " + message);
//    hadError = true;
//  }

//  private static boolean hadError = false;

}
