package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.core.entity.pedido.Pedido;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoDAO extends DAO<Long,Pedido> {

}
