package ru.pechhenka.expressionparser.operand;


import java.util.Map;

public interface Operand {
    int evaluate(Map<String, Integer> values);

}
