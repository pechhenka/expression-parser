package ru.pechhenka.expressionparser.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CharSource {
    boolean hasNext();

    void undo(int count);

    char next();

    ParseException error(final String message);
}
