package br.com.alura.clientelo.web.dao.cliente;

import br.com.alura.clientelo.web.dao.DAO;
import br.com.alura.clientelo.web.core.entity.cliente.Cliente;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDAO extends DAO<Long, Cliente> {
    Pageable clientesFieisPage = PageRequest.of(0,3, JpaSort.unsafe(Sort.Direction.DESC, "(montante)"));
    Pageable maisLucrativosPage = PageRequest.of(0,2, JpaSort.unsafe(Sort.Direction.DESC, "(montante)"));

    @Query("SELECT c from Cliente c WHERE c.nome like :nome")
    List<Cliente> listaPorNome(String nome);

    @Query("SELECT new br.com.alura.clientelo.web.dao.cliente.ClienteFielVO(" +
            "c.nome, COUNT(p.id)," +
            "SUM(ip.quantidade * ip.precoUnitario) as montante" +
            ") FROM Cliente c " +
            "JOIN Pedido p on p.cliente = c " +
            "JOIN ItemPedido ip on ip.pedido = p " +
            "GROUP BY c.id ")
    List<ClienteFielVO> clientesFieis(Pageable clientesFieisPage);

    @Query("SELECT new br.com.alura.clientelo.web.dao.cliente.ClienteFielVO(" +
            "c.nome, COUNT(p.id)," +
            "SUM(ip.quantidade * ip.precoUnitario) as montante" +
            ") FROM Cliente c " +
            "JOIN Pedido p on p.cliente = c " +
            "JOIN ItemPedido ip on ip.pedido = p " +
            "GROUP BY c.id ")
    List<ClienteFielVO> maisLucrativos(Pageable maisLucrativosPage);

}
