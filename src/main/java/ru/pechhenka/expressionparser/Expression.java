package ru.pechhenka.expressionparser;

import java.util.Map;
import java.util.Set;

public interface Expression {
    int evaluate(Map<String, Integer> values);

    Set<String> getVariables();
}
