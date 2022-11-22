package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO;
import br.com.alura.clientelo.models.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDAO extends DAO<Long, Categoria> {

    @Query("SELECT c from Categoria c WHERE c.nome = :nome")
    Categoria buscaPorNome(String nome);

    @Query("SELECT new br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO(" +
            "c.nome, SUM(ip.quantidade), SUM(ip.quantidade * ip.precoUnitario)" +
            ") from " +
            "Pedido p " +
            "JOIN ItemPedido ip on ip.pedido = p " +
            "JOIN Produto pr on ip.produto = pr " +
            "JOIN Categoria c on pr.categoria = c group by c.nome")
    List<VendasPorCategoriaVO> agrupaPorCategoria();


}
