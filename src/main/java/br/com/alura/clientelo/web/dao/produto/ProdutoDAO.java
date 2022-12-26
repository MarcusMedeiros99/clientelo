package br.com.alura.clientelo.web.dao.produto;

import br.com.alura.clientelo.web.core.entity.produto.Produto;
import br.com.alura.clientelo.web.dao.DAO;
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
