package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;

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
