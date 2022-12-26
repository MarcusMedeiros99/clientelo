package br.com.alura.clientelo.web.dao.auth;

import br.com.alura.clientelo.web.core.entity.auth.RoleEnum;
import br.com.alura.clientelo.web.core.entity.auth.UsuarioRole;
import br.com.alura.clientelo.web.dao.DAO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRoleDAO extends DAO<Long, UsuarioRole> {

    Optional<UsuarioRole> findByName(RoleEnum role);
}
