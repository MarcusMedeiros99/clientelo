package br.com.alura.clientelo.controller.categoria;

import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.CategoriaStatus;

import java.util.List;

public interface CategoriaFilterUseCase {

    List<Categoria> findBy(String nome, CategoriaStatus status);
}
