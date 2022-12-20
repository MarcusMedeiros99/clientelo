package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.core.entity.usuario.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends DAO<Long, Usuario> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
