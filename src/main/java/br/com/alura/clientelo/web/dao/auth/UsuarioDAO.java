package br.com.alura.clientelo.web.dao.auth;

import br.com.alura.clientelo.web.core.entity.auth.Usuario;
import br.com.alura.clientelo.web.dao.DAO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends DAO<Long, Usuario> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
