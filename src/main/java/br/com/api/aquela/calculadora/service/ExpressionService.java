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

    //TODO - Sempre é bom termos nomenclaturas de métodos o mais próximos possíveis de oque eles fazem.
    // Aqui é um método que não só salva, mas também faz um get e compara, se caso achar, retorna..
    // ou seja, ele se torna um metodo de find dentro do método de save..
    // Partindo do conceito de SOLID, não é uma boa prática fazer essa forma.
    // https://blog.casadodesenvolvedor.com.br/solid-na-pratica/#:~:text=SOLID%20%C3%A9%20um%20conjunto%20de,artigo%20The%20Principles%20of%20OOD.
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
        // TODO - Precisa do cast?
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
        // TODO - Método dificil de entender
        // Em caso de necessidade de manutenção, vai custar o dobro ou triplo do tempo de outro DEV.
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
        // TODO - Hoje em dia temos operações mais legíveis, práticos e performáticos para iterar sobre listas, como por exemplo
        // for(String item: operators) { print(item) }
        // OU, melhor ainda
        // operators.forEach { item -> print(item)},

        // TODO - Poderia evitar o loop completo toda vez fazendo um operators.contains() antes.
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
        //TODO - Aqui dentro deveria estar a regra da divisão por 0, em caso de ter, lançar uma exceção capturada pelo controlador.

        //TODO - Coisas que são usados várias vezes no código, deve ser colocadas numa constante.. ex:
        // const DIVISION = "/"
        // ao inves de usar "/" sempre, usar DIVISION


        //TODO - Quando um método se torna pesado de ler, é sempre bom ter comentários explicando oque cada passo deve fazer
        // mas o ideia é não ser pesado pra ler.

        if (operators.get(j).equals("+")) {
            result = Double.parseDouble(values.get(j)) + Double.parseDouble(values.get(j + 1));
        } else if (operators.get(j).equals("-")) {
            result = Double.parseDouble(values.get(j)) - Double.parseDouble(values.get(j + 1));
        }
        operators.remove(j);
        values.add(j, String.valueOf(result));
        values.remove(j+1);
        values.remove(j+1);

        // TODO - operators.isEmpty() é melhor
        if (operators.size() == 0){
            return Double.parseDouble(values.get(0));
        }
        return calculaRecursivo(operators, values);
    }
}
