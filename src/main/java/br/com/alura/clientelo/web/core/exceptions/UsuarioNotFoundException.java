package br.com.alura.clientelo.web.core.exceptions;

import br.com.alura.clientelo.web.core.entity.auth.Usuario;

public class UsuarioNotFoundException extends ClienteloEntityNotFoundException{
    public UsuarioNotFoundException(String message) {
        super(message, Usuario.class);
    }
}
