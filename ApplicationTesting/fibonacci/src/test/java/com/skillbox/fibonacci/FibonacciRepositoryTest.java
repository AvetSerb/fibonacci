package com.skillbox.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = PostgresTestContainerInitializer.class)
public class FibonacciRepositoryTest {

    @Autowired
    private FibonacciRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    public static void setUp() {
        // Очистка таблицы перед каждым тестом
        PostgresTestContainerInitializer.setUp();
    }

    @AfterAll
     public static void tearDown() {
        PostgresTestContainerInitializer.tearDown();
    }
    // сохранение числа
    @Test
    void shouldSaveNewFibonacciNumber() {
        FibonacciNumber number = new FibonacciNumber(1, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        // Verify that the number is saved in the database
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM fibonacci_number WHERE index = ?", new Object[]{1}, Integer.class);
        assertEquals(1, count);
    }

    // поиск числа по порядковому номеру
    @Test
    void testFindFibonacciNumberByIndex() {
        FibonacciNumber number = new FibonacciNumber(2, 1);
        repository.save(number);

        entityManager.flush();
        entityManager.detach(number);

        // Проверяем поиск числа по индексу
        Optional<FibonacciNumber> foundNumber = repository.findByIndex(2);
        assertTrue(foundNumber.isPresent());
        assertEquals(number.getIndex(), foundNumber.get().getIndex());
        assertEquals(number.getValue(), foundNumber.get().getValue());
    }

    // отсутствие дубликатов
    @Test
    void shouldNotThrowExceptionWhenInsertingDuplicate() {
        FibonacciNumber number = new FibonacciNumber(3, 2);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        // Повторная вставка того же числа
        assertDoesNotThrow(() -> {
            repository.save(number);
            entityManager.flush();
        });

        // Проверка, что дубликатов нет
        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 3",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );

        assertEquals(1, actual.size());
    }
}

