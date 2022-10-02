package com.cdugga.parser;

import com.cdugga.scanner.Token;
import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private int current = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  private Expr expression() {
    return equality();
  }

  private Expr equality() {
    Expr expr = comparison();

    while (match("!=","==")) {
      Token operator = previous();
      Expr right = comparison();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }
  private boolean match(String... types) {
    for (String type : types) {
      if (check(type)) {
        advance();
        return true;
      }
    }
    return false;
  }

  private boolean check(String type) {
    if (isAtEnd()) return false;
    return peek().equals(type);
  }

  private Token advance() {
    if (!isAtEnd()) current++;
    return previous();
  }

  private boolean isAtEnd() {
    return peek().equals("EOF");
  }

  private Token peek() {
    return tokens.get(current);
  }

  private Token previous() {
    return tokens.get(current - 1);
  }

  private Expr comparison() {
    Expr expr = term();

    while (match(">","<",">=","<=")) {
      Token operator = previous();
      Expr right = term();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr term() {
    Expr expr = factor();

    while (match("-","+")) {
      Token operator = previous();
      Expr right = factor();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr factor() {
    Expr expr = unary();

    while (match("*","/")) {
      Token operator = previous();
      Expr right = unary();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr unary() {
    if (match("-","!")) {
      Token operator = previous();
      Expr right = unary();
      return new Expr.Unary(operator, right);
    }

    return primary();
  }

  private Expr primary() {
    if (match("false") return new Expr.Literal(false);
    if (match("true") return new Expr.Literal(true);
    if (match("nil") return new Expr.Literal(null);

    if (match("NUMBER","STRING")) {
      return new Expr.Literal(previous().literal);
    }


    if (match("(")) {
      Expr expr = expression();
      consume(")", "Expect ')' after expression.");
      return new Expr.Grouping(expr);
    }

  }

}
