package ru.pechhenka.expressionparser;

public class Divide extends BinaryExpression {

    public Divide(final Operand left, final Operand right) {
        super("/", left, right);
    }


    @Override
    public int calc(int a, int b) {
        return a / b;
    }
}
