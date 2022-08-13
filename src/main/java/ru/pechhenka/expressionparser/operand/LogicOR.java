package ru.pechhenka.expressionparser.operand;

import ru.pechhenka.expressionparser.operand.BinaryOperand;
import ru.pechhenka.expressionparser.operand.Operand;

public class LogicOR extends BinaryOperand {

    public LogicOR(final Operand left, final Operand right) {
        super("|", left, right);
    }

    @Override
    public int calc(final int a, final int b) {
        return a | b;
    }
}
