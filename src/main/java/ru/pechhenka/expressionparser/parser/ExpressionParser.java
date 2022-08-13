package ru.pechhenka.expressionparser.parser;

import ru.pechhenka.expressionparser.*;
import ru.pechhenka.expressionparser.operand.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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
        final Operand result = parseExpression();
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

                public String toString() {
                    return result.toString();
                }
            };
        }

        throw error("Expected end of input, actual: '" + ch + '\'');
    }

    public static Expression parseExpression(final String expression) {
        return new ExpressionParser().parse(expression);
    }

    private Operand parseExpression() {
        return parseOR();
    }

    private Operand parseOR() {
        return parseInfixLeft(this::parseXOR, LogicOR::new, "|");
    }

    private Operand parseXOR() {
        return parseInfixLeft(this::parseAND, LogicXOR::new, "^");
    }

    private Operand parseAND() {
        return parseInfixLeft(this::parseAdd, LogicAND::new, "&");
    }

    private Operand parseAdd() {
        return parseInfixLeft(this::parseSubtract, Add::new, "+");
    }

    private Operand parseSubtract() {
        return parseInfixLeft(this::parseMultiply, Subtract::new, "-");
    }

    private Operand parseMultiply() {
        return parseInfixLeft(this::parseDivide, Multiply::new, "*");
    }

    private Operand parseDivide() {
        return parseInfixLeft(this::parseNegate, Divide::new, "/");
    }

    private Operand parseNegate() {
        return parseUnary(this::parseLeaf, Negate::new, "-");
    }

    private Operand parseLeaf() {
        skipWhiteSpace();
        if (test('(')) {
            final Operand e = parseExpression();
            skipWhiteSpace();
            expect(')');
            return e;
        }
        if (isLetter()) {
            return parseVariable();
        }
        if (isDigit()) {
            return parseConst();
        }
        throw error("Illegal state");
    }

    private Operand parseVariable() {
        if (!isLetter()) {
            throw error("Variable not started by letter");
        }

        final StringBuilder sb = new StringBuilder();
        while (isLetter() || isDigit()) {
            sb.append(ch);
            nextChar();
        }

        return new Variable(sb.toString());
    }


    private Operand parseConst() {
        final String value = readDigits();
        return new Const(Integer.parseInt(value));
    }


    private void skipWhiteSpace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
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

    private Operand parseInfixLeft(final Supplier<Operand> nextParser,
                                   final BinaryOperator<Operand> buildExpression,
                                   final String operation) {
        Operand left = nextParser.get();

        while (!eof()) {
            skipWhiteSpace();
            if (!test(operation)) {
                break;
            }

            final Operand right = nextParser.get();
            left = buildExpression.apply(left, right);
        }

        return left;
    }

    private Operand parseInfixRight(final Supplier<Operand> nextParser,
                                    final BinaryOperator<Operand> buildExpression,
                                    final String operation) {
        final ArrayList<Operand> expressions = new ArrayList<>(1);
        expressions.add(nextParser.get());

        while (!eof()) {
            skipWhiteSpace();
            if (!test(operation)) {
                break;
            }

            expressions.add(nextParser.get());
        }

        Collections.reverse(expressions);
        Operand res = expressions.get(0);
        for (int i = 1; i < expressions.size(); i++) {
            res = buildExpression.apply(expressions.get(i), res);
        }
        return res;
    }

    private Operand parseUnary(final Supplier<Operand> nextParser,
                               final UnaryOperator<Operand> buildExpression,
                               final String operation) {
        skipWhiteSpace();
        if (test(operation)) {
            return buildExpression.apply(parseUnary(nextParser, buildExpression, operation));
        }
        return nextParser.get();
    }
}