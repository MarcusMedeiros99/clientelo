package br.com.alura.clientelo.dao.specifications.metamodels;

import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.Produto;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(Produto.class)
public class Produto_ {
    public static volatile SingularAttribute<Produto, Long> id;
    public static volatile SingularAttribute<Produto, String> nome;
    public static volatile SingularAttribute<Produto, Categoria> categoria;
    public static volatile SingularAttribute<Produto, BigDecimal> preco;
}
