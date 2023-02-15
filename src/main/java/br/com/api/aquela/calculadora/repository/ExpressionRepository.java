package br.com.api.aquela.calculadora.repository;

import br.com.api.aquela.calculadora.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
    @Query("from Expression c where c.expression like %?1%")
    Expression getExpressionByText(String expression);
}
