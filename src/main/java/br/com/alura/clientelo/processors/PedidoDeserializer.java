package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class PedidoDeserializer {
    @CsvBindByName(column = "categoria")
    private String categoria;
    @CsvBindByName(column = "produto")
    private String produto;
    @CsvBindByName(column = "cliente")
    private String cliente;
    @CsvBindByName(column = "preco")
    private BigDecimal preco;
    @CsvBindByName(column = "quantidade")
    private int quantidade;
    @CsvBindByName(column = "data")
    @CsvDate("dd/MM/yyyy")
    private LocalDate data;

    public PedidoDeserializer() {};

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
