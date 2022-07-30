package com.cdugga;

import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.parser.Tokens.Token;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


/**
 * Hello world!
 */
public class App {

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
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Tokens> tokens = scanner.scanTokens(); // to do create scanner and scan tokens, and token class

    for (Token token : tokens) {
      System.out.println(token);
    }
  }


}
