package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class PedidoDeserializer {
    private String categoria;
    private String produto;
    private String cliente;
    private BigDecimal preco;
    private int quantidade;
    private LocalDate data;

    @JsonCreator
    public PedidoDeserializer(
            @JsonProperty("categoria") String categoria,
            @JsonProperty("produto") String produto,
            @JsonProperty("cliente") String cliente,
            @JsonProperty("preco") BigDecimal preco,
            @JsonProperty("quantidade") int quantidade,
            @JsonProperty("data") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data) {
        this.categoria = categoria;
        this.produto = produto;
        this.cliente = cliente;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
    }

    Pedido toPedido() {
        return new Pedido(
                categoria,
                produto,
                cliente,
                preco,
                quantidade,
                data
        );
    }
}
