package br.com.api.aquela.calculadora.service;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
            return getExpressionByText(expression.getExpression());
        }
        expression.setResult(calcResult(expression).replace(",","."));
        System.out.println(expression.getResult());
        operadores.clear();
        valores.clear();
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
        return expressionRepository.getExpressionByText(expression);
    }

    public String calcResult(Expression expression){
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        stringToArrayList(expression.getExpression());
        result = df.format(calculaRecursivo(operadores,valores));
        System.out.println(result);
        return result;
    }
    public void stringToArrayList(String expression){
        char[] expressaoEmChar = expression.toCharArray();
        for(int i = 0; i < expressaoEmChar.length; i++){
            String string ="";
            do {
                string += String.valueOf(expressaoEmChar[i]);
                if(i < expressaoEmChar.length-1) {
                    i++;
                }else{
                    break;
                }
            }while (!(expressaoEmChar[i] == '+' || expressaoEmChar[i] == '-'
                    || expressaoEmChar[i] == '/' || expressaoEmChar[i] == '*'));
            valores.add(string);
            if(i < expressaoEmChar.length-1) {
                operadores.add(String.valueOf(expressaoEmChar[i]));
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
                        System.out.println(valores.get(i));
                        System.out.println(Double.parseDouble(valores.get(i)));
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
