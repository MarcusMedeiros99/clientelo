package br.com.alura.clientelo.exceptions;

public class ClienteNotFoundException extends ClienteloEntityNotFoundException {
    public ClienteNotFoundException(String message, Class clazz) {
        super(message, clazz);
    }
}
