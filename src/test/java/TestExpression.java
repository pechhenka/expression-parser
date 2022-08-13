import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pechhenka.expressionparser.parser.ExpressionParser;

import java.util.Random;

/**
 * @author Pavel Sharaev (mail@pechhenka.ru)
 */
public class TestExpression {
    private final Random random;

    public TestExpression() {
        this.random = new Random(23845);
    }

    @Test
    public void primitive() {
        assertParse("x", "x");
        assertParse("0", "0");
        testOperation("|");
        testOperation("^");
        testOperation("&");
        testOperation("+");
        testOperation("-");
        testOperation("*");
        testOperation("/");
    }

    private static void testOperation(final String symbol) {
        testSimpleExpression("1", symbol, "6");
        testSimpleExpression("x", symbol, "sdgsg");
        testSimpleExpression("5", symbol, "dg");
        testSimpleExpression("wergewg", symbol, "0");
        testSimpleExpression("299999", symbol, "29999");
    }

    private static void testSimpleExpression(final String a, final String symbol, final String b) {
        assertParse("(%s %s %s)".formatted(a, symbol, b), "%s%s%s".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "  %s%s%s".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "%s%s%s    ".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "     %s%s%s     ".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "   %s  %s   %s   ".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "     %s%s  %s".formatted(a, symbol, b));
        assertParse("(%s %s %s)".formatted(a, symbol, b), "%s    %s%s".formatted(a, symbol, b));
    }

    private static void assertParse(final String expected, final String expression) {
        Assertions.assertEquals(expected, ExpressionParser.parse(expression).toString());
    }
}
