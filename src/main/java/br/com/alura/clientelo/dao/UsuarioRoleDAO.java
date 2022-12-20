package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.core.entity.usuario.RoleEnum;
import br.com.alura.clientelo.core.entity.usuario.UsuarioRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRoleDAO extends DAO<Long, UsuarioRole> {

    Optional<UsuarioRole> findByName(RoleEnum role);
}
