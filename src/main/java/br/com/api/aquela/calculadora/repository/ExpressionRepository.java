package br.com.api.aquela.calculadora.repository;

import br.com.api.aquela.calculadora.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
    @Query("select e from Expression e where e.expression like %?1%")
    Expression getExpressionByText(String expression);

    //TODO - Porq o uso do like?
    //Caso fizer uma expressão 2+2+2 e após 2+2, vai dar problema porq vai achaar 2 registros com o like
    // pois 2+2 está incluso no 2+2+2

}
