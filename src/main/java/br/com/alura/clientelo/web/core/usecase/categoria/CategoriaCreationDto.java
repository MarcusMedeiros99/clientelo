package br.com.alura.clientelo.web.core.usecase.categoria;

import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.CategoriaStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoriaCreationDto {

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
