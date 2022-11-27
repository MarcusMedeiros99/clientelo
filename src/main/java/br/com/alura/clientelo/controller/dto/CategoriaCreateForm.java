package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoriaCreateForm {

    @NotNull
    @Size(min = 2)
    private String nome;

    public String getNome() {
        return nome;
    }

    public Categoria convert() {
        return new Categoria(nome, CategoriaStatus.ATIVA);
    }
}
