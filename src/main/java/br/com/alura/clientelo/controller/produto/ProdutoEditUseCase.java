package br.com.alura.clientelo.controller.produto;

import br.com.alura.clientelo.core.usecase.produto.ProdutoEditDto;
import br.com.alura.clientelo.core.entity.produto.Produto;

public interface ProdutoEditUseCase {

    Produto edit(Long produtoId, ProdutoEditDto dto);
}
