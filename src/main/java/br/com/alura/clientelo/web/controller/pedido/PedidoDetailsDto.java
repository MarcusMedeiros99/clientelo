package br.com.alura.clientelo.web.controller.pedido;

import br.com.alura.clientelo.web.core.entity.pedido.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDetailsDto {
    private LocalDate dataDoPedido;
    private BigDecimal valor;
    private BigDecimal desconto;
    private List<ProdutoOnPedidoDetailsDto> items;
    private Long clienteId;
    private String clienteNome;

    public PedidoDetailsDto() {}

    public LocalDate getDataDoPedido() {
        return dataDoPedido;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public List<ProdutoOnPedidoDetailsDto> getItems() {
        return items;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public PedidoDetailsDto(Pedido pedido) {
        this.dataDoPedido = pedido.getData();
        this.valor = pedido.getTotalComDesconto();
        this.desconto = pedido.getDescontoTotal();
        this.clienteId = pedido.getCliente().getId();
        this.clienteNome = pedido.getCliente().getNome();

        this.items =
                pedido.getItemPedidos()
                        .stream().map(ProdutoOnPedidoDetailsDto::new)
                        .collect(Collectors.toList());

    }
}
