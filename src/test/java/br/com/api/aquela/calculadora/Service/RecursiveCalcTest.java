package br.com.api.aquela.calculadora.Service;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.service.ExpressionService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class RecursiveCalcTest extends TestCase {
    @Autowired
    ExpressionService expressionService = new ExpressionService();

    @Test
    public void testCalcResult(){
        Expression expression = new Expression();
        String returnedString="0";
        String expectedReturn = "4";
        expression.setExpression("2+2");
        returnedString = expressionService.calcResult(expression);
        assertEquals(expectedReturn,returnedString);

        expectedReturn="10,29";
        expression.setExpression("2.3*2.3+5");
        returnedString = expressionService.calcResult(expression);
        assertEquals(expectedReturn,returnedString);

        expectedReturn="0,78";
        expression.setExpression("2.33/3");
        returnedString = expressionService.calcResult(expression);
        assertEquals(expectedReturn,returnedString);

        expectedReturn="0";
        expression.setExpression("1/0");
        returnedString = expressionService.calcResult(expression);
        assertEquals(expectedReturn,returnedString);

    }
}
