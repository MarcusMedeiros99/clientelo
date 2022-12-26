package br.com.alura.clientelo.web.controller.categoria;

import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.CategoriaStatus;

public class CategoriaDto {
    private Long id;
    private CategoriaStatus status;
    private String nome;

    public CategoriaDto(Categoria categoria) {
        this.status = categoria.getStatus();
        this.nome = categoria.getNome();
        this.id = categoria.getId();
    }

    public CategoriaStatus getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
}
