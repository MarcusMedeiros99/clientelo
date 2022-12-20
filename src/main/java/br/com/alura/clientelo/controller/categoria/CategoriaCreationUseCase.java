package br.com.alura.clientelo.controller.categoria;

import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.usecase.categoria.CategoriaCreationDto;

public interface CategoriaCreationUseCase {
    Categoria create(CategoriaCreationDto dto);
}
