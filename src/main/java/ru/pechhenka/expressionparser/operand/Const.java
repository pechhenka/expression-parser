package ru.pechhenka.expressionparser.operand;

import java.util.Map;

public class Const implements Operand {

    private final int value;

    public Const(final int value) {
        this.value = value;
    }


    public int evaluate(final Map<String, Integer> values) {
        return value;
    }


    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Const aConst = (Const) o;
        return value == aConst.value;
    }

    @Override
    public int hashCode() {
        return value * 31 + getClass().hashCode();
    }
}
