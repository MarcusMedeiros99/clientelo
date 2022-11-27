package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.models.Produto;
import org.hibernate.Criteria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoDAO extends DAO<Long, Produto> {

    @Query("SELECT p from Produto p WHERE p.quantidadeEmEstoque = 0")
    List<Produto> listaIndisponiveis();

    @Query("SELECT p from Produto p " +
            "JOIN ItemPedido ip on ip.produto = p " +
            "GROUP BY p.id HAVING SUM(ip.quantidade) > 3")
    List<Produto> maisVendidos();

//    @Query("SELECT p FROM Produto p " +
//            "JOIN FETCH Categoria c " +
//            "WHERE (:nome IS NULL or p.nome like :nome " +
//            "AND (:nomeCategoria IS NULL or c.nome like '%:nomeCategoria%') " +
//            "AND (:minPreco IS NULL or p.preco >= :minPreco) " +
//            "AND (:maxPreco IS NULL or p.preco <= :maxPreco)")
//    List<Produto> findAllBy(@Param("nome") String nome,
//                            @Param("nomeCategoria") String nomeCategoria,
//                            @Param("minPreco") BigDecimal minPreco,
//                            @Param("maxPreco") BigDecimal maxPreco);

}
