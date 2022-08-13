package ru.pechhenka.expressionparser.operand;

import ru.pechhenka.expressionparser.operand.Operand;
import ru.pechhenka.expressionparser.operand.UnaryOperand;

public class Abs extends UnaryOperand {

    public Abs(final Operand operand) {
        super("abs", operand);
    }

    public int calc(final int a) {
        return Math.absExact(a);
    }
}