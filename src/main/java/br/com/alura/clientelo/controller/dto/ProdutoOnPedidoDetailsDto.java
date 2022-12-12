package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.ItemPedido;

import java.math.BigDecimal;

public class ProdutoOnPedidoDetailsDto {
    private Long id;
    private String nome;
    private String categoria;
    private Long quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valor;
    private BigDecimal desconto;

    ProdutoOnPedidoDetailsDto() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public ProdutoOnPedidoDetailsDto(ItemPedido itemPedido) {
        this.id = itemPedido.getProduto().getId();
        this.nome = itemPedido.getProduto().getNome();
        this.categoria = itemPedido.getProduto().getCategoria().getNome();
        this.quantidade = itemPedido.getQuantidade();
        this.precoUnitario = itemPedido.getPrecoUnitario();
        this.valor = itemPedido.getTotalComDesconto();
        this.desconto = itemPedido.getDesconto();
    }
}
