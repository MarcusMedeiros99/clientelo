package br.com.alura.clientelo.exceptions;

import br.com.alura.clientelo.core.entity.produto.Produto;

public class ProdutoNotFoundException extends ClienteloEntityNotFoundException {

    public ProdutoNotFoundException(String message) {
        super(message, Produto.class);
    }
}
