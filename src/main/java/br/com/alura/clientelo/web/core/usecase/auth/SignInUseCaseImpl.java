package br.com.alura.clientelo.web.core.usecase.auth;

import br.com.alura.clientelo.web.controller.auth.SignInUseCase;
import br.com.alura.clientelo.web.core.entity.auth.Usuario;
import br.com.alura.clientelo.web.core.exceptions.UsuarioNotFoundException;
import br.com.alura.clientelo.web.dao.auth.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SignInUseCaseImpl implements SignInUseCase {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public Usuario signIn(LoginDto loginDto) {
        Usuario user = usuarioDAO.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario não encontrado"));
        Authentication authentication = new TestingAuthenticationToken(user, loginDto.getPassword(), "USUARIO_ROLE");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario userDetails = (Usuario) authentication.getPrincipal();

        return userDetails;
    }
}
