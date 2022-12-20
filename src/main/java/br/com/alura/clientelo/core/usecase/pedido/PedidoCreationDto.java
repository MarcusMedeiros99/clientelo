package br.com.alura.clientelo.core.usecase.pedido;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class PedidoCreationDto {
    @NotNull
    private Long clienteId;
    private List<ItemPedidoDto> items;

    public Long getClienteId() {
        return clienteId;
    }

    public List<ItemPedidoDto> getItems() {
        return Collections.unmodifiableList(items);
    }

//    public Pedido convert(ClienteDAO clienteDAO, ProdutoDAO produtoDAO) {
//        Cliente cliente = findCliente(this.getClienteId(), clienteDAO);
//        Pedido pedido = criaPedido(cliente, this.getItems(), produtoDAO);
//
//        return pedido;
//    }

//    private Cliente findCliente(Long clienteId, ClienteDAO clienteDAO) {
//        Optional<Cliente> optionalCliente = clienteDAO.findById(clienteId);
//        if (optionalCliente.isEmpty())
//            throw new ClienteNotFoundException("Cliente %d não existe".formatted(clienteId), Cliente.class);
//        return optionalCliente.get();
//    }
//
//    private Pedido criaPedido(Cliente cliente, List<ItemPedidoDto> items, ProdutoDAO produtoDAO) {
//
//
//        Pedido pedido = new Pedido(cliente);
//        items.stream()
//                .map(itemPedidoDto -> itemPedidoDto.convert(produtoDAO, pedido))
//                .forEach(pedido::addItemPedido);
//
//        return pedido;
//    }

}
