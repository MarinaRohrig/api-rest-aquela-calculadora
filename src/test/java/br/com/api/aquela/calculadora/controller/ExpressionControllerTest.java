package br.com.api.aquela.calculadora.controller;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpressionControllerTest {
    @MockBean
    ExpressionRepository expressionRepository;
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveExpressions() throws Exception {
        Expression expression = new Expression();
        expression.setExpression("2+2");
       Mockito.when(expressionRepository.save(Mockito.any())).thenReturn(expression);
        this.mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":null,\"expression\":\"2+2\",\"result\":null}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expression").value("2+2"))
                .andExpect(content().json("{'id':null,'expression':'2+2','result':'4.0'}"));

    }
}
