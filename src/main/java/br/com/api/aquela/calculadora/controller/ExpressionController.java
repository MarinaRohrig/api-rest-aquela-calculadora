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

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> save(@RequestBody Expression expression){
        //TODO - E se quiser dividir 10/0.1?
        if (expression.getExpression().contains("/0")){
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }

        // TODO- O ideal seria separar em 2 passos aqui, fazer um get e comparar,
        //  e só depois em caso de não existir, salvar usando o método salvar.
       expression = expressionService.save(expression);
       return new ResponseEntity<String>("{\n\"resultado\": "+expression.getResult()+"\n}", HttpStatus.OK);
    }
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long id){
        expressionService.deleteById(id);
        return new ResponseEntity<String>("Expressão id: "+ id +", deletada com sucesso!",HttpStatus.OK);
    }

    //TODO - Não consegui usar.
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

    //TODO - Não consegui usar.
    @GetMapping(value = "/byText/{expressao}")
    public ResponseEntity<Expression> getExpressionByText(@RequestParam (name = "expression") String expression){
        return new ResponseEntity<Expression>(expressionService.getExpressionByText(expression),HttpStatus.OK);
    }


    //TODO - Pouco controle de exceção.. é bom sempre termos Exceptions próprias e capturarmos ela pra devolver
    // a quem requisitou uma resposta mais clara e um código Http mais claro..
}
