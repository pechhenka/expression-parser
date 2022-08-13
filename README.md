# Парсер выражений
Домашнее задание выполненное мною в университете.

`ExpressionParse` парсит данную строчкy и строит её модель в виде дерева, по сути здесь написана маленькая часть парсера для калькулятора.

Примеры использования:


```java
new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
).equals(ExpressionParser.parse("(2*x)*x"));


ExpressionParser.parse("(5 - 3 * y) + 10")
        .evaluate(Map.of("y", 2))
        .equals(9);
```