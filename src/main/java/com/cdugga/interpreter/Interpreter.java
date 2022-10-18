package com.cdugga.interpreter;

import com.cdugga.error.RuntimeError;
import com.cdugga.parser.Expr;
import com.cdugga.parser.Expr.Binary;
import com.cdugga.parser.Expr.Grouping;
import com.cdugga.parser.Expr.Literal;
import com.cdugga.parser.Expr.Unary;
import com.cdugga.scanner.Token;

public class Interpreter implements Expr.Visitor<Object>{

  @Override
  public Object visitBinaryExpr(Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right);

    switch (expr.operator.type) {
      case PLUS:
        if (left instanceof Double && right instanceof Double) {
          return (double) left + (double) right;
        }
        if (left instanceof String && right instanceof String) {
          return (String) left + (String) right;
        }
      case MINUS:
        checkNumberOperand(expr.operator, right);
        return (double) left - (double) right;
      case SLASH:
        return (double) left / (double) right;
      case STAR:
        return (double) left * (double) right;
      case GREATER:
        return (double) left > (double) right;
      case GREATER_EQUAL:
        return (double) left >= (double) right;
      case LESS:
        return (double) left < (double) right;
      case LESS_EQUAL:
        return (double) left <= (double) right;
      case BANG_EQUAL:
        return !isEqual(left, right);
      case EQUAL_EQUAL:
        return isEqual(left, right);
    }

    return null;
  }

  @Override
  public Object visitGroupingExpr(Grouping expr) {
    return evaluate(expr.expression);
  }

  @Override
  public Object visitLiteralExpr(Literal expr) {
    return expr.value;
  }

  @Override
  public Object visitUnaryExpr(Unary expr) {
    Object right = evaluate(expr.right);
    switch (expr.operator.type) {
      case MINUS:
        return -(double)right;
      case BANG:
        return !isTruthy(right);
    }
    return null;
  }

  private boolean isTruthy(Object object) {
    if (object == null) {
      return false;
    }
    if (object instanceof Boolean) {
      return (boolean)object;
    }
    return true;
  }


  private Object evaluate(Expr expr){
    return expr.accept(this);
  }

  private boolean isEqual(Object a, Object b) {
    if (a == null && b == null) {
      return true;
    }
    if (a == null) {
      return false;
    }
    return a.equals(b);
  }

  private void checkNumberOperand(Token operator, Object operand) {
    if (operand instanceof Double) {
      return;
    }
    throw new RuntimeError(operator, "Operand must be a number.");
  }
}
