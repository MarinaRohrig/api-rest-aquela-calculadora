package br.com.api.aquela.calculadora.service;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;
    ArrayList<String> operadores = new ArrayList<>();
    ArrayList<String> valores = new ArrayList<>();
    @Transactional
    public Expression save(Expression expression){
        if (expression.equals(getExpressionByText(expression.getExpression()))){
            System.out.println("Já existe no banco");
            return getExpressionByText(expression.getExpression());
        }
        expression.setResult(calcResult(expression));
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

    public Expression getExpressionByText(String expression){
        Expression expressionReturned = expressionRepository.getExpressionByText(expression);
        return expressionReturned;
    }

    public Double calcResult(Expression expression){
        Double result = 0.0;
                stringToArrayList(expression.getExpression());
         result = calculaRecursivo(operadores,valores);
        return result;
    }
    public void stringToArrayList(String expression){
        char[] expressaoEmChar = expression.toCharArray();
        for(int i = 0; i < expressaoEmChar.length; i++){
            if (expressaoEmChar[i] == '+' || expressaoEmChar[i] == '-'
                    || expressaoEmChar[i] == '/' || expressaoEmChar[i] == '*'){
                operadores.add(String.valueOf(expressaoEmChar[i]));
            }else if ((i < expressaoEmChar.length-1) && (expressaoEmChar[i+1] == '.')){
                valores.add(expressaoEmChar[i] +String.valueOf(expressaoEmChar[i+1])+ expressaoEmChar[i + 2]);
                i+=2;
            } else{
                valores.add(String.valueOf(expressaoEmChar[i]));
            }
        }
    }

    public Double calculaRecursivo(ArrayList<String> operadores, ArrayList<String> valores) {
        Double result = 0.0;
        int j = 0;
        for (int i = 0; i < operadores.size(); i++) {
            if (operadores.get(i).equals("/") || operadores.get(i).equals("*")) {
                j = i;
                if (operadores.get(i).equals("/")) {
                    if (Double.parseDouble(valores.get(i+1)) == 0){
                        result = 0.0;
                    } else {
                        result = Double.parseDouble(valores.get(i)) / Double.parseDouble(valores.get(i + 1));
                    }
                    i = operadores.size();
                } else if (operadores.get(i).equals("*")) {
                    result = Double.parseDouble(valores.get(i)) * Double.parseDouble(valores.get(i + 1));
                    i = operadores.size();
                }
            }
        }
        if (operadores.get(j).equals("+")) {
            result = Double.parseDouble(valores.get(j)) + Double.parseDouble(valores.get(j + 1));
        } else if (operadores.get(j).equals("-")) {
            result = Double.parseDouble(valores.get(j)) - Double.parseDouble(valores.get(j + 1));
        }
        operadores.remove(j);
        valores.add(j, String.valueOf(result));
        valores.remove(j+1);
        valores.remove(j+1);

        if (operadores.size() == 0){
            return Double.parseDouble(valores.get(0));
        }
        return calculaRecursivo(operadores, valores);
    }

}
