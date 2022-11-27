package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.ClienteNotFoundException;
import br.com.alura.clientelo.models.Cliente;
import br.com.alura.clientelo.models.ItemPedido;
import br.com.alura.clientelo.models.Pedido;
import br.com.alura.clientelo.models.TipoDescontoPedido;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PedidoCreateForm {
    private Long clienteId;
    private List<ItemPedidoDto> items;

    public Long getClienteId() {
        return clienteId;
    }

    public List<ItemPedidoDto> getItems() {
        return items;
    }

    public Pedido convert(ClienteDAO clienteDAO, ProdutoDAO produtoDAO, PedidoDAO pedidoDAO) {
        Cliente cliente = findCliente(clienteDAO);
        Pedido pedido = criaPedido(cliente, produtoDAO);
        darDescontoFidelidade(pedido, cliente, pedidoDAO);

        return pedido;
    }

    private Cliente findCliente(ClienteDAO clienteDAO) {
        Optional<Cliente> optionalCliente = clienteDAO.findById(clienteId);
        if (optionalCliente.isEmpty())
            throw new ClienteNotFoundException("Cliente %d não existe".formatted(clienteId), Cliente.class);
        return optionalCliente.get();
    }

    private void darDescontoFidelidade(Pedido pedido, Cliente cliente, PedidoDAO pedidoDAO) {
        Pedido pedidoExample = new Pedido();
        pedidoExample.setCliente(cliente);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        if (pedidoDAO.count(Example.of(pedidoExample, matcher)) >= 5l) {
            pedido.setTipoDesconto(TipoDescontoPedido.FIDELIDADE);
            pedido.setDesconto(pedido.getTotal().multiply(BigDecimal.valueOf(0.05)));
        }
    }

    private Pedido criaPedido(Cliente cliente, ProdutoDAO produtoDAO) {
        Pedido pedido = new Pedido();

        pedido.setData(LocalDate.now());
        pedido.setTipoDesconto(TipoDescontoPedido.NENHUM);
        pedido.setDesconto(BigDecimal.ZERO);
        pedido.setCliente(cliente);

        items.stream()
                .map(itemPedidoDto -> itemPedidoDto.convert(produtoDAO, pedido))
                .forEach(pedido::addItemPedido);

        return pedido;
    }
}
