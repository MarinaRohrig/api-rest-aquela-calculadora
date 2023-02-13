package br.com.api.aquela.calculadora.controller;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import br.com.api.aquela.calculadora.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ExpressionController {
    @Autowired
    private ExpressionService expressionService;
    @GetMapping(value = "/oi")
    public String olah(){
        return "Olá!";
    }
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
        Expression expression = expressionService.getExpressionById(id);
        return new ResponseEntity<Expression>(expression, HttpStatus.OK);
    }
}
