package br.com.api.aquela.calculadora.service;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;
    @Transactional
    public Expression save(Expression expression){
        return expressionRepository.save(expression);
    }
    @Transactional
    public void deleteById(Long idExpression){
        expressionRepository.deleteById(idExpression);
    }
    public Expression getExpressionById(Long idExpression){
        return expressionRepository.findById(idExpression).get();
    }
    public List<Expression> getExpressions(){
        return (List<Expression>) expressionRepository.findAll();
    }
}
