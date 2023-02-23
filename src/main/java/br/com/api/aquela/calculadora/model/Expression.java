package br.com.api.aquela.calculadora.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "expression")
@Getter
@Setter
public class Expression implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String expression;
    private String result;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Expression){
            Expression that = (Expression) o;
            return this.getExpression().equals(that.getExpression());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// TODO - Nomenclaturas de entidades são muito importantes p/ demonstrar o que elas armazenam e retornam
// nesse caso aqui, uma tabela chamada Expression com uma coluna expression não é uma boa prática..
// Eu colocaria Operation o nome da tabela.