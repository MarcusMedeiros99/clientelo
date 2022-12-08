package br.com.alura.clientelo.models;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
public class UsuarioRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum name;

    public UsuarioRole() {
    }

    public UsuarioRole(Integer id, RoleEnum name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public RoleEnum getName() {
        return name;
    }
}
