package br.com.alura.clientelo.service;

import br.com.alura.clientelo.controller.dto.ItemPedidoDto;
import br.com.alura.clientelo.controller.form.PedidoCreateForm;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.ClienteNotFoundException;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private PedidoDAO pedidoDAO;

    public Pedido convert(PedidoCreateForm form) {
        Cliente cliente = findCliente(form.getClienteId());
        Pedido pedido = criaPedido(cliente, form.getItems());

        return pedido;
    }

    private Cliente findCliente(Long clienteId) {
        Optional<Cliente> optionalCliente = clienteDAO.findById(clienteId);
        if (optionalCliente.isEmpty())
            throw new ClienteNotFoundException("Cliente %d não existe".formatted(clienteId), Cliente.class);
        return optionalCliente.get();
    }

    private Pedido criaPedido(Cliente cliente, List<ItemPedidoDto> items) {
        Pedido pedido = new Pedido(cliente);
        items.stream()
                .map(itemPedidoDto -> itemPedidoDto.convert(produtoDAO, pedido))
                .forEach(pedido::addItemPedido);

        return pedido;
    }

    private ItemPedido convert(ItemPedidoDto itemPedidoDto, Pedido pedido) {
        Optional<Produto> optionalProduto = produtoDAO.findById(itemPedidoDto.getProdutoId());

        if(optionalProduto.isEmpty())
            throw new ProdutoNotFoundException("Produto %d não existe".formatted(itemPedidoDto.getProdutoId()), Produto.class);

        Produto produto = optionalProduto.get();

        ItemPedido itemPedido = new ItemPedido(pedido, produto, itemPedidoDto.getQuantidade());

        return itemPedido;
    }
}
