package br.com.alura.clientelo.web.controller.produto;

import br.com.alura.clientelo.web.core.entity.produto.Produto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProdutoDto {
    private Long id;
    private String nomeCategoria;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEmEstoque;
    private String nome;

    @JsonCreator
    public ProdutoDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nomeCategoria") String nomeCategoria,
            @JsonProperty("descricao") String descricao,
            @JsonProperty("preco") BigDecimal preco,
            @JsonProperty("quantidadeEmEstoque") Integer quantidadeEmEstoque,
            @JsonProperty("nome") String nome) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.nome = nome;
    }

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nomeCategoria = produto.getCategoria().getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidadeEmEstoque = produto.getQuantidadeEmEstoque();
        this.nome = produto.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public String getNome() {
        return nome;
    }

}
