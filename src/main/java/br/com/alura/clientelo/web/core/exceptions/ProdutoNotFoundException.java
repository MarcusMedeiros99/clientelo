package br.com.alura.clientelo.web.core.exceptions;

import br.com.alura.clientelo.web.core.entity.produto.Produto;

public class ProdutoNotFoundException extends ClienteloEntityNotFoundException {

    public ProdutoNotFoundException(String message) {
        super(message, Produto.class);
    }
}
