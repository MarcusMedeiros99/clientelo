package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.Produto;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProdutoCreateForm {
    @NotNull
    @Size(min = 2)
    private String nome;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal preco;
    private String descricao;
    @NotNull
    private Integer quantidadeEmEstoque;
    @NotNull
    private Long categoriaId;

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public Produto convert(CategoriaDAO categoriaDao) {
        Categoria categoria = categoriaDao.buscaPorId(categoriaId);


        return new Produto
                .Builder()
                .withNome(nome)
                .withPreco(preco)
                .withDescricao(descricao)
                .withQuantidadeEmEstoque(quantidadeEmEstoque)
                .withCategoria(categoria)
                .build();

    }
}
