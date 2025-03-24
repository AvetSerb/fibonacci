package com.skillbox.fibonacci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciCalculatorTest {
    private final FibonacciCalculator calculator = new FibonacciCalculator();

    //  корректное вычисления чисел Фибоначчи
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void shouldReturnCorrectFibonacciNumber(int index) {
        int expected = switch (index) {
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 5;
            case 6 -> 8;
            case 7 -> 13;
            default -> 1;
        };
        int result = calculator.getFibonacciNumber(index);
        Assertions.assertEquals(expected, result);
    }

    // меньше 1 выбрасывается исключение
    @Test
    void shouldThrowExceptionForIndexLessThanOne() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculator.getFibonacciNumber(0);
        });
        assertEquals("Index should be greater or equal to 1", exception.getMessage());
    }
    // возвращение 1 для индексов 1 и 2
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void shouldReturnOneForIndexOneAndTwo(int index) {
        int result = calculator.getFibonacciNumber(index);
        Assertions.assertEquals(1, result);
    }

}
