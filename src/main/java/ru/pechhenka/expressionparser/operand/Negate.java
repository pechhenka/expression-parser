package ru.pechhenka.expressionparser.operand;

public class Negate extends UnaryOperand {

    public Negate(final Operand operand) {
        super("-", operand);
    }

    public int calc(final int a) {
        return Math.negateExact(a);
    }
}
