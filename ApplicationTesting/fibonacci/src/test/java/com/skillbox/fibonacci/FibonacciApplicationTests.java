package com.skillbox.fibonacci;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FibonacciApplicationTests {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private FibonacciController fibonacciController;

	@Autowired
	private FibonacciService fibonacciService;

	@Test
	void contextLoads() {
		assertNotNull(context, "Контекст должен быть загружен");
	}

	// FibonacciController корректно инициализируется
	@Test
	void fibonacciControllerLoads() {
		assertNotNull(fibonacciController, "FibonacciController должен быть загружен");
	}

	//FibonacciService корректно инициализируется
	@Test
	void fibonacciServiceLoads() {
		assertNotNull(fibonacciService, "FibonacciService должен быть загружен");
	}
}

