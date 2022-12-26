package br.com.alura.clientelo.web.controller.auth;

import br.com.alura.clientelo.web.core.entity.auth.Usuario;

import java.util.Collection;

public class UserInfoResponse {
    private Long id;
    private String email;
    private Collection<String> roles;

    public UserInfoResponse(Long id, String email, Collection<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public UserInfoResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail(), usuario.getAuthoritiesAsStrings());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Collection<String> getRoles() {
        return roles;
    }
}
