package br.com.api.aquela.calculadora.controller;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class ExpressionController {
    @Autowired
    private ExpressionService expressionService;
    @PostMapping
    public ResponseEntity<Expression> save(@RequestBody Expression expression){
        expressionService.save(expression);
        return new ResponseEntity<Expression>(expression, HttpStatus.CREATED);
    }
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long id){
        expressionService.deleteById(id);
        return new ResponseEntity<String>("Expressão id: "+ id +", deletada com sucesso!",HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Expression> getExpressionById(@RequestParam Long id){
        return new ResponseEntity<Expression>(expressionService.getExpressionById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/expressions")
    @ResponseBody
    public ResponseEntity<List<Expression>> getExpressions(){
        List<Expression> expressions = expressionService.getExpressions();
        return new ResponseEntity<List<Expression>>(expressions,HttpStatus.OK);
    }
    @GetMapping(value = "/byText/{expressao}")
    public ResponseEntity<Expression> getExpressionByText(@RequestParam (name = "expression") String expression){
        return new ResponseEntity<Expression>(expressionService.getExpressionByText(expression),HttpStatus.OK);
    }
    @GetMapping(value = "/testeCalc")
    public String getCalc(){

        return "Resultado: ";
    }

}
