package br.com.alura.clientelo.controller.produto;

import br.com.alura.clientelo.core.usecase.produto.ProdutoCreationDto;
import br.com.alura.clientelo.core.entity.produto.Produto;

public interface ProdutoCreationUseCase {

    Produto createFrom(ProdutoCreationDto dto);
}
