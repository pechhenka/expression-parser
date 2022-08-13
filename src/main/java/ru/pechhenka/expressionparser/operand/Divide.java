package ru.pechhenka.expressionparser.operand;

import ru.pechhenka.expressionparser.operand.BinaryOperand;
import ru.pechhenka.expressionparser.operand.Operand;

public class Divide extends BinaryOperand {

    public Divide(final Operand left, final Operand right) {
        super("/", left, right);
    }


    @Override
    public int calc(final int a, final int b) {
        return a / b;
    }
}
