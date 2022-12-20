package br.com.alura.clientelo.controller.produto;

import br.com.alura.clientelo.core.entity.produto.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProdutoResumeDto {
    private Long id;
    private String nome;
    private String nomeCategoria;
    private BigDecimal preco;

    public ProdutoResumeDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nome") String nome,
            @JsonProperty("nomeCategoria") String nomeCategoria,
            @JsonProperty("preco") BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.nomeCategoria = nomeCategoria;
        this.preco = preco;
    }

    public ProdutoResumeDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.nomeCategoria = produto.getCategoria().getNome();
        this.preco = produto.getPreco();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
