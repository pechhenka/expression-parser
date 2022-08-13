package ru.pechhenka.expressionparser.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseParser {
    public static final char END = '\0';
    protected CharSource source;
    protected char ch;

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected boolean test(final char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean test(final String expected) {
        for (int i = 0; i < expected.length(); i++) {
            if (ch == expected.charAt(i)) {
                nextChar();
            } else {
                undo(i);
                return false;
            }
        }
        return true;
    }

    protected void expect(final char c) {
        if (ch != c) {
            throw error("Expected '%s', found '%s'".formatted(c, ch));
        }
        nextChar();
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    private void undo(final int count) {
        source.undo(count);
    }



    protected boolean eof() {
        return test(END);
    }

    protected ParseException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}