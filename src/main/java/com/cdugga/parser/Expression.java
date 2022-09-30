package com.cdugga.parser;

import com.cdugga.scanner.Token;

public abstract class Expression {

  static class Binary extends Expression {

    final Expression left;
    final Token operator;
    final Expression right;

    Binary(Expression left, Token operator, Expression right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }
  }

}
