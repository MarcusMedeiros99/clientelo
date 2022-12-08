package br.com.alura.clientelo.controller.dto;

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
