package ru.pechhenka.expressionparser.operand;

import java.util.Map;
import java.util.Objects;

public abstract class BinaryOperand implements Operand {

    private final String symbol;
    private final Operand left;
    private final Operand right;

    public BinaryOperand(final String symbol, final Operand left, final Operand right) {
        this.symbol = symbol;
        this.left = left;
        this.right = right;
    }

    public abstract int calc(final int a, final int b);

    @Override
    public int evaluate(final Map<String, Integer> values) {
        return calc(left.evaluate(values), right.evaluate(values));
    }


    @Override
    public String toString() {
        return String.format("(%s %s %s)", left.toString(), symbol, right.toString());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BinaryOperand that = (BinaryOperand) o;
        return symbol.equals(that.symbol) && left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, left, right) * 31 + getClass().hashCode();
    }
}