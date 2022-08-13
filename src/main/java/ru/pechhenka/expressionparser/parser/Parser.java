package ru.pechhenka.expressionparser.parser;


import ru.pechhenka.expressionparser.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression);
}
