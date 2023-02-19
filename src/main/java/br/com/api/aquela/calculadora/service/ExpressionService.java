package br.com.api.aquela.calculadora.service;

import br.com.api.aquela.calculadora.model.Expression;
import br.com.api.aquela.calculadora.repository.ExpressionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpressionService {
    @Autowired
    private ExpressionRepository expressionRepository;
    static ArrayList<String> operadores = new ArrayList<>();
    static ArrayList<String> valores = new ArrayList<>();

    @Transactional
    public Expression save(Expression expression){
        if (expression.equals(getExpressionByText(expression.getExpression()))){
            return getExpressionByText(expression.getExpression());
        }
        expression.setResult(calcResult(expression).replace(",","."));
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
        valores.clear();
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

    public Double calculaRecursivo(ArrayList<String> operators, ArrayList<String> values) {
        Double result = 0.0;
        int j = 0;
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals("/") || operators.get(i).equals("*")) {
                j = i;
                if (operators.get(i).equals("/")) {
                    if (Double.parseDouble(values.get(i+1)) == 0){
                        result = 0.0;
                    } else {
                        result = Double.parseDouble(values.get(i)) / Double.parseDouble(values.get(i + 1));
                    }
                    i = operators.size();
                } else if (operators.get(i).equals("*")) {
                    result = Double.parseDouble(values.get(i)) * Double.parseDouble(values.get(i + 1));
                    i = operators.size();
                }
            }
        }
        if (operators.get(j).equals("+")) {
            result = Double.parseDouble(values.get(j)) + Double.parseDouble(values.get(j + 1));
        } else if (operators.get(j).equals("-")) {
            result = Double.parseDouble(values.get(j)) - Double.parseDouble(values.get(j + 1));
        }
        operators.remove(j);
        values.add(j, String.valueOf(result));
        values.remove(j+1);
        values.remove(j+1);

        if (operators.size() == 0){
            return Double.parseDouble(values.get(0));
        }
        return calculaRecursivo(operators, values);
    }
}
