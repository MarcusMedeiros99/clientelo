package br.com.alura.clientelo.controller.pedido;

import br.com.alura.clientelo.core.entity.pedido.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PedidoOnListDto {
    private Long id;
    private LocalDate data;
    private BigDecimal valor;
    private BigDecimal desconto;
    private Long quantidadeDeItems;
    private Long clienteId;
    private String clienteNome;

    public PedidoOnListDto(Pedido pedido) {
        this.id = pedido.getId();
        this.data = pedido.getData();
        this.valor = pedido.getTotalComDesconto();
        this.desconto = pedido.getDescontoTotal();
        this.quantidadeDeItems = pedido.getQuantidadeDeItens();
        this.clienteId = pedido.getCliente().getId();
        this.clienteNome = pedido.getCliente().getNome();
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Long getQuantidadeDeItems() {
        return quantidadeDeItems;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }
}
