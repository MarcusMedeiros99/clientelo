package br.com.alura.clientelo.web.controller.produto;

import br.com.alura.clientelo.web.core.usecase.produto.ProdutoEditDto;
import br.com.alura.clientelo.web.core.entity.produto.Produto;

public interface ProdutoEditUseCase {

    Produto edit(Long produtoId, ProdutoEditDto dto);
}
