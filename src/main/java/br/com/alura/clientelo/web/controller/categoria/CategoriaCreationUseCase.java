package br.com.alura.clientelo.web.controller.categoria;

import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.usecase.categoria.CategoriaCreationDto;

public interface CategoriaCreationUseCase {
    Categoria create(CategoriaCreationDto dto);
}
