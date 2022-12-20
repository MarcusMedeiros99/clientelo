package br.com.alura.clientelo.controller.pedido;

import br.com.alura.clientelo.core.entity.pedido.Pedido;
import br.com.alura.clientelo.core.usecase.pedido.PedidoCreationDto;

public interface PedidoCreationUseCase {

    Pedido createPedido(PedidoCreationDto dto);
}
