package br.com.alura.clientelo.web.dao.produto;

import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.Produto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;

public class ProdutoSpecifications {

    public static Specification<Produto> filter(String nome, String nomeCategoria,
                                                BigDecimal minPreco, BigDecimal maxPreco) {

        return (root, query, criteriaBuilder) -> {
            Join<Produto, Categoria> produtoCategoriaJoin = root.join("categoria");

            Predicate minPrecoPredicate = criteriaBuilder.or(
                    criteriaBuilder.equal(criteriaBuilder.nullLiteral(BigDecimal.class), minPreco),
                    criteriaBuilder.gt(root.get("preco"), minPreco)
            );

            Predicate maxPrecoPredicate = criteriaBuilder.or(
                    criteriaBuilder.equal(criteriaBuilder.nullLiteral(BigDecimal.class), maxPreco),
                    criteriaBuilder.lt(root.get("preco"), maxPreco)
            );

            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("nome"), "%" + nome + "%"),
                    criteriaBuilder.like(produtoCategoriaJoin.get("nome"), "%" + nomeCategoria + "%"),
                    minPrecoPredicate,
                    maxPrecoPredicate
            );
        };
    }
}
