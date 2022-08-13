package ru.pechhenka.expressionparser.operand;

import java.util.Map;
import java.util.Objects;

/**
 * @author Pavel Sharaev (mail@pechhenka.ru)
 */
public abstract class UnaryOperand implements Operand {
    private final String symbol;
    private final Operand operand;

    public UnaryOperand(final String symbol, final Operand operand) {
        this.symbol = symbol;
        this.operand = operand;
    }

    public abstract int calc(final int value);

    @Override
    public int evaluate(final Map<String, Integer> values) {
        return calc(operand.evaluate(values));
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", symbol, operand.toString());
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UnaryOperand that = (UnaryOperand) o;
        return symbol.equals(that.symbol) && operand.equals(that.operand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, operand) * 31 + getClass().hashCode();
    }
}
