package br.com.alura.clientelo.web.controller.pedido;

import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.core.usecase.pedido.PedidoCreationDto;

public interface PedidoCreationUseCase {

    Pedido createPedido(PedidoCreationDto dto);
}
