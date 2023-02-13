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
    @PostMapping
    public ResponseEntity<Expression> save(@RequestBody Expression expression){
        expressionService.save(expression);
        return new ResponseEntity<Expression>(expression, HttpStatus.CREATED);
    }

    @GetMapping(value = "/oi")
    public String olah(){
        return "Olá!";
    }

}
