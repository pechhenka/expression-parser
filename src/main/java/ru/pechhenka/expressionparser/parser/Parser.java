package ru.pechhenka.expressionparser.parser;


import ru.pechhenka.expressionparser.Expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    Expression parse(String expression);
}
