package com.skillbox.fibonacci;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FibonacciController.class)
class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FibonacciService service;

    @Test
    void shouldReturnFibonacciNumberForValidIndex() throws Exception {
        FibonacciNumber mockNumber = new FibonacciNumber(5, 5);
        when(service.fibonacciNumber(5)).thenReturn(mockNumber);

        // Выполняем GET-запрос и проверяем статус и тело ответа
        mockMvc.perform(get("/fibonacci/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.index").value(5))
                .andExpect((ResultMatcher) jsonPath("$.value").value(5));
    }

    @Test
    void shouldReturnBadRequestForInvalidIndex() throws Exception {
        when(service.fibonacciNumber(0)).thenThrow(new IllegalArgumentException("Index should be greater or equal to 1"));

        mockMvc.perform(get("/fibonacci/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Index should be greater or equal to 1"));
    }
    @Test
    void shouldReturnFibonacciNumberForLargeIndex() throws Exception {
        FibonacciNumber mockNumber = new FibonacciNumber(10, 55);
        when(service.fibonacciNumber(10)).thenReturn(mockNumber);

        mockMvc.perform(get("/fibonacci/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.index").value(10))
                .andExpect((ResultMatcher) jsonPath("$.value").value(55));
    }
}


