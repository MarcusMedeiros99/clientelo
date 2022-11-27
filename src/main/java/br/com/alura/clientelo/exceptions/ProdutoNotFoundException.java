package br.com.alura.clientelo.exceptions;

public class ProdutoNotFoundException extends ClienteloEntityNotFoundException {

    public ProdutoNotFoundException(String message, Class clazz) {
        super(message, clazz);
    }
}
