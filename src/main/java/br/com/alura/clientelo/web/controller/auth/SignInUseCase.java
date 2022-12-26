package br.com.alura.clientelo.web.controller.auth;

import br.com.alura.clientelo.web.core.entity.auth.Usuario;
import br.com.alura.clientelo.web.core.usecase.auth.LoginDto;

public interface SignInUseCase {

    Usuario signIn(LoginDto loginDto);
}
