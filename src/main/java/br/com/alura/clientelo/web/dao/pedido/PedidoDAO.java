package br.com.alura.clientelo.web.dao.pedido;

import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.dao.DAO;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoDAO extends DAO<Long, Pedido> {

}
