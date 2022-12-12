package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private BigDecimal total;
    private BigDecimal totalComDesconto;
    private LocalDate data;

    PedidoDTO () {}

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTotalComDesconto() {
        return totalComDesconto;
    }

    public LocalDate getData() {
        return data;
    }

    public PedidoDTO(Pedido pedido) {
        this.total = pedido.getTotal().setScale(2, RoundingMode.HALF_UP);
        this.totalComDesconto = pedido.getTotalComDesconto().setScale(2, RoundingMode.HALF_UP);
        this.data = pedido.getData();
        this.id = pedido.getId();
        this.clienteId = pedido.getCliente().getId();
    }
}
