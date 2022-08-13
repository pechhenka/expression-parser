package ru.pechhenka.expressionparser.operand;

import java.util.Map;
import java.util.Objects;

public class Variable implements Operand {
    private final String name;

    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass() == Variable.class) {
            return ((Variable) obj).name.equals(this.name);
        }
        return false;
    }

    public int evaluate(final Map<String, Integer> values) {
        return Objects.requireNonNull(values.get(name));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + getClass().hashCode();
    }
}
