package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Produto;

import java.math.BigDecimal;

public class ProdutoDto {
    private Long id;
    private String nomeCategoria;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEmEstoque;
    private String nome;

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
