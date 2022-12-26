package br.com.alura.clientelo.web.core.usecase.pedido;

import br.com.alura.clientelo.web.controller.pedido.PedidoCreationUseCase;
import br.com.alura.clientelo.web.core.entity.cliente.Cliente;
import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.dao.cliente.ClienteDAO;
import br.com.alura.clientelo.web.dao.pedido.PedidoDAO;
import br.com.alura.clientelo.web.dao.produto.ProdutoDAO;
import br.com.alura.clientelo.web.core.exceptions.ClienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoCreationUseCaseImpl implements PedidoCreationUseCase {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private PedidoDAO pedidoDAO;

    public Pedido createPedido(PedidoCreationDto dto) {
        Cliente cliente = findCliente(dto.getClienteId());
        Pedido pedido = criaPedido(cliente, dto.getItems());

        pedidoDAO.save(pedido);
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

}
