package br.com.api.aquela.calculadora.controller;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.service.ExpressionService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class CalculadoraTest extends TestCase {
    @Autowired
    ExpressionService expressionService = new ExpressionService();

    @Test
    public void testCalcResult(){
        Expression expression = new Expression();
        String returnedString="0";
        String expectedReturn = "4";
        expression.setExpression("2+2");
        returnedString = expressionService.calcResult(expression);
        System.out.println(returnedString);
        assertEquals(expectedReturn,returnedString);
    }
}
