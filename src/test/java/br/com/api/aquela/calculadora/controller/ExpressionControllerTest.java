package br.com.api.aquela.calculadora.controller;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpressionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ExpressionRepository expressionRepository;
    @Test
    void getExpressions() throws Exception{
        var expression = new Expression();
        expression.setExpression("2+2");
        Mockito.when(expressionRepository.findAll()).thenReturn(List.of(expression));
        this.mockMvc.perform(get("/api/expressions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':null,'expression':'2+2','result':null}"));
    }
}


