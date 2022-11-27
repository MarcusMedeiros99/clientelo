package br.com.alura.clientelo.exceptions;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}
