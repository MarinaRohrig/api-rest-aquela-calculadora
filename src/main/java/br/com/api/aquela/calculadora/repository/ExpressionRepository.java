package br.com.api.aquela.calculadora.repository;

import br.com.api.aquela.calculadora.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
