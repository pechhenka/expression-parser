package ru.pechhenka.expressionparser.operand;

import ru.pechhenka.expressionparser.operand.Operand;
import ru.pechhenka.expressionparser.operand.UnaryOperand;

public class UnaryMinus extends UnaryOperand {

    public UnaryMinus(final Operand operand) {
        super("-", operand);
    }

    public int calc(final int a) {
        return Math.negateExact(a);
    }
}
