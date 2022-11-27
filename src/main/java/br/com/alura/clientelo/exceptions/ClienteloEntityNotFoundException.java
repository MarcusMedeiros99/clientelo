package br.com.alura.clientelo.exceptions;

public class ClienteloEntityNotFoundException extends RuntimeException {
    public ClienteloEntityNotFoundException(String message) {
        super(message);
    }
}
