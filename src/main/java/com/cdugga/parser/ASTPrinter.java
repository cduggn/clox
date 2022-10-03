package com.cdugga.parser;

import com.cdugga.parser.Expr;
import com.cdugga.parser.Expr.Binary;
import com.cdugga.parser.Expr.Grouping;
import com.cdugga.parser.Expr.Literal;
import com.cdugga.parser.Expr.Unary;
import com.cdugga.scanner.Token;
import com.cdugga.scanner.TokenType;

public class ASTPrinter implements Expr.Visitor<String> {


  public static  void main(String[] args) {
    Expr expression = new Binary(
        new Unary(
            new Token(TokenType.MINUS, "-", null, 1),
            new Literal(123)),
        new Token(TokenType.STAR, "*", null, 1),
        new Grouping(
            new Literal(45.67)));
    System.out.println(new ASTPrinter().print(expression));
  }

  @Override
  public String visitBinaryExpr(Binary expr) {
    return parenthesis(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Grouping expr) {
    return parenthesis("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Literal expr) {
    if (expr.value == null) {
      return "nil";
    }
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Unary expr) {
    return parenthesis(expr.operator.lexeme, expr.right);
  }

  public String print(Expr expr) {
    return expr.accept(this);
  }

  private String parenthesis(String expr, Expr... exprs) {
    StringBuilder sb = new StringBuilder();
    sb.append("(").append(expr);
    for (Expr e : exprs) {
      sb.append(" ");
      sb.append(e.accept(this));
    }
    sb.append(")");
    return sb.toString();
  }



}
