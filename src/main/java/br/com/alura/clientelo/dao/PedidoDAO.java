package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.models.Pedido;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoDAO extends DAO<Long,Pedido> {

}
