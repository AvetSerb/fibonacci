package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FibonacciServiceTest {
    private final FibonacciRepository repository = mock(FibonacciRepository.class);
    private final FibonacciCalculator calculator = mock(FibonacciCalculator.class);
    private final FibonacciService service = new FibonacciService(repository, calculator);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // когда число уже есть в базе
    @Test
    void shouldReturnNumberFromDatabaseIfExists() {
        FibonacciNumber mockNumber = new FibonacciNumber(5, 5);

        when(repository.findByIndex(5)).thenReturn(Optional.of(mockNumber));

        FibonacciNumber result = service.fibonacciNumber(5);

        assertEquals(5, result.getValue());
        assertEquals(5, result.getIndex());

        verify(calculator, never()).getFibonacciNumber(5);
    }

    // когда нет в базе, рассчитать и сохранить
    @Test
    void shouldCalculateAndSaveNumberIfNotInDatabase() {
        when(repository.findByIndex(10)).thenReturn(Optional.empty());
        when(calculator.getFibonacciNumber(10)).thenReturn(55);

        FibonacciNumber result = service.fibonacciNumber(10);

        verify(repository).save(result);

        assertEquals(10, result.getIndex());
        assertEquals(55, result.getValue());

    }

    // исключения индекс меньше 1
    @Test
    void shouldThrowExceptionForIndexLessThanOne() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.fibonacciNumber(0);
        });
        assertEquals("Index should be greater or equal to 1", exception.getMessage());
    }
}

