package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.core.entity.produto.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoDAO extends DAO<Long, Produto> {

    @Query("SELECT p from Produto p WHERE p.quantidadeEmEstoque = 0")
    List<Produto> listaIndisponiveis();

    @Query("SELECT p from Produto p " +
            "JOIN ItemPedido ip on ip.produto = p " +
            "GROUP BY p.id HAVING SUM(ip.quantidade) > 3")
    List<Produto> maisVendidos();

}
