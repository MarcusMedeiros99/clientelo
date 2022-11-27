package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Produto;

import java.math.BigDecimal;

public class ProdutoResumeDto {
    private Long id;
    private String nome;
    private String nomeCategoria;
    private BigDecimal preco;

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
