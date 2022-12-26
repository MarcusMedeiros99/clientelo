package br.com.alura.clientelo.web.core.exceptions;

public class ClienteNotFoundException extends ClienteloEntityNotFoundException {
    public ClienteNotFoundException(String message, Class clazz) {
        super(message, clazz);
    }
}
