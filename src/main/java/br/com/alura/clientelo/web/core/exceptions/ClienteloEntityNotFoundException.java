package br.com.alura.clientelo.web.core.exceptions;

public class ClienteloEntityNotFoundException extends RuntimeException {
    private Class clazz;

    public ClienteloEntityNotFoundException(String message, Class clazz) {
        super(message);
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
