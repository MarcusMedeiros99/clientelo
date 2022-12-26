package br.com.alura.clientelo.web.dao.categoria;

import br.com.alura.clientelo.web.dao.DAO;
import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDAO extends DAO<Long, Categoria> {

    @Query("SELECT c from Categoria c WHERE c.nome = :nome")
    Categoria buscaPorNome(String nome);

    @Query("SELECT new br.com.alura.clientelo.web.dao.categoria.VendasPorCategoriaVO(" +
            "c.nome, SUM(ip.quantidade), SUM(ip.quantidade * ip.precoUnitario)" +
            ") from " +
            "Pedido p " +
            "JOIN ItemPedido ip on ip.pedido = p " +
            "JOIN Produto pr on ip.produto = pr " +
            "RIGHT JOIN Categoria c on pr.categoria = c group by c.nome")
    List<VendasPorCategoriaVO> agrupaPorCategoria();

}
