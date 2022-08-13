package ru.pechhenka.expressionparser.operand;

import ru.pechhenka.expressionparser.operand.Operand;
import ru.pechhenka.expressionparser.operand.UnaryOperand;

public class Sqrt extends UnaryOperand {
    public Sqrt(final Operand operand) {
        super("sqrt", operand);
    }

    public int calc(final int a) {
        if (a < 0) {
            throw new ArithmeticException("sqrt by negative integer");
        }
        return (int) Math.sqrt(a);
    }
}