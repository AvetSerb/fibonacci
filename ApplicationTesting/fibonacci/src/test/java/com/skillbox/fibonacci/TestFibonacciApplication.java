package com.skillbox.fibonacci;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestFibonacciApplication.class)
@TestConfiguration(proxyBeanMethods = false)
public class TestFibonacciApplication {
	@Test
    public void contextLoads() {
	}

	public static void main(String[] args) {
		SpringApplication.from(FibonacciApplication::main).with(TestFibonacciApplication.class).run(args);
	}


}
