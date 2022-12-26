package br.com.alura.clientelo.web.controller.produto;

import br.com.alura.clientelo.web.core.usecase.produto.ProdutoCreationDto;
import br.com.alura.clientelo.web.core.entity.produto.Produto;

public interface ProdutoCreationUseCase {

    Produto createFrom(ProdutoCreationDto dto);
}
