package com.cdugga;

import com.cdugga.interpreter.Interpreter;
import com.cdugga.parser.ASTPrinter;
import com.cdugga.parser.Expr;
import com.cdugga.parser.Parser;
import com.cdugga.scanner.Token;
import com.cdugga.scanner.TokenType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.cdugga.scanner.Scanner;
import com.cdugga.statement.Stmt;


/**
 * Hello world!
 */
public class Lox {

  private static final Interpreter interpreter = new Interpreter();

  private static boolean hadError = false;

  private static boolean hadRuntimeError = false;

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

    if (hadError){
      System.exit(65);
    }

    if (hadRuntimeError) {
      System.exit(70);
    }
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
      hadError = false;
    }
  }

  private static void run(String source) {
    System.out.println("<><><><>\tStart Scanning Tokens");
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens(); // to do create scanner and scan tokens, and token class

    for (Token token : tokens) {
      System.out.println(token);
    }

    System.out.println("<><><><>\tEnd Scanning Tokens");
    System.out.println("  -------  ");
    System.out.println("<><><><>\tStart Parsing Tokens");
    Parser parser = new Parser(tokens);
    List<Stmt> expression = parser.parse();

    if(hadError) return;
//    System.out.println(new ASTPrinter().print(expression));
//    System.out.println("<><><><>\tEnd Parsing Tokens");
//
//    interpreter.interpret(expression);

  }

  static void runtimeError(RuntimeException error) {
    System.err.println(error.getMessage());
    hadRuntimeError = true;
  }

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

}
