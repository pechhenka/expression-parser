package ru.pechhenka.expressionparser.parser;

import ru.pechhenka.expressionparser.*;
import ru.pechhenka.expressionparser.operand.*;

import java.util.Map;
import java.util.Set;

public class ExpressionParser extends BaseParser implements Parser {

    /*

    L - logic operation
    E - expression + -
    T - term * /
    F - fundamental
    U - unary minus

    C - const
    V - variable

    L -> E | L | E ^ L | E & L | E
    E -> T + E | T - E | T
    T -> F * T | F / T | F
    F -> C     | V     | (L)   | U
    U -> -C    | -V    | -(L)

     */

    public Expression parse(final String expression) {
        this.source = new StringSource(expression);

        nextChar();
        final Operand result = parseL();
        if (eof()) {
            return new Expression() {
                @Override
                public int evaluate(final Map<String, Integer> values) {
                    return result.evaluate(values);
                }

                @Override
                public Set<String> getVariables() {
                    return null;
                }
            };
        }

        throw error("Expected end of input, actual: '" + ch + '\'');
    }

    public static Expression parseExpression(final String expression) {
        return new ExpressionParser().parse(expression);
    }

    private Operand parseL() {
        return parseLogicOperation('|');
    }

    private Operand parseE() {
        Operand left = parseT();

        while (!test(END)) {
            skipWhiteSpace();
            final char op = ch;
            if (op != '+' && op != '-') {
                break;
            }
            nextChar();

            final Operand right = parseT();
            if (op == '+') {
                left = new Add(left, right);
            } else if (op == '-') {
                left = new Subtract(left, right);
            }
        }
        return left;
    }

    private Operand parseT() {
        Operand left = parseF();

        while (!test(END)) {
            skipWhiteSpace();
            final char op = ch;
            if (op != '*' && op != '/') {
                break;
            }
            nextChar();

            final Operand right = parseF();
            if (op == '*') {
                left = new Multiply(left, right);
            } else if (op == '/') {
                left = new Divide(left, right);
            }
        }
        return left;
    }

    private Operand parseF() {
        skipWhiteSpace();

        if (isDigit()) {
            return parseConst();
        } else if (test('(')) {
            final Operand lowestLevel = parseL();
            if (!test(')')) {
                throw error("expected ')'");
            }
            return lowestLevel;
        } else if (ch == '-') {
            return parseU();
        } else if (isLetter()) {
            return new Variable(readVariable());
        }

        throw new IllegalStateException("wrong state");
    }

    private Operand parseU() {

        if (!test('-')) {
            throw error("wrong state");
        }

        skipWhiteSpace();
        if (isDigit()) {
            return parseNegativeConst();
        } else if (test('(')) {
            final Operand lowestLevel = parseL();
            if (!test(')')) {
                throw error("wrong state");
            }
            return new UnaryMinus(lowestLevel);
        } else if (isLetter()) {
            return new UnaryMinus(new Variable(readVariable()));
        }
        return new UnaryMinus(parseU());
    }

    private Operand parseLogicOperation(final char operation) {
        Operand left = getLogicLowerOperation(operation);

        while (!test(END)) {
            skipWhiteSpace();
            final char op = ch;
            if (op != operation) {
                break;
            }
            nextChar();

            final Operand right = getLogicLowerOperation(operation);
            if (op == '|') {
                left = new LogicOR(left, right);
            } else if (op == '^') {
                left = new LogicXOR(left, right);
            } else if (op == '&') {
                left = new LogicAND(left, right);
            }
        }
        return left;
    }

    private Operand getLogicLowerOperation(final char operation) {
        if (operation == '|') {
            return parseLogicOperation('^');
        } else if (operation == '^') {
            return parseLogicOperation('&');
        } else {
            return parseE();
        }
    }

    private Operand parseConst() {
        final String value = readDigits();
        return new Const(Integer.parseInt(value));
    }

    private Operand parseNegativeConst() {
        final String value = readDigits();
        return new Const(Integer.parseInt('-' + value));
    }


    private void skipWhiteSpace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    private String readVariable() {
        final StringBuilder sb = new StringBuilder();

        do {
            sb.append(ch);
            nextChar();
        } while (isLetter());

        return sb.toString();
    }

    private String readDigits() {
        final StringBuilder sb = new StringBuilder();

        do {
            sb.append(ch);
            nextChar();
        } while (isDigit());

        return sb.toString();
    }

    private boolean isLetter() {
        return Character.isLetter(ch);
    }

    private boolean isDigit() {
        return Character.isDigit(ch);
    }
}