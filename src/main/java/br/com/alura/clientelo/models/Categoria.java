package br.com.alura.clientelo.models;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaStatus status;

    public Categoria() {};

    public Categoria(String nome, CategoriaStatus status) {
        this.nome = nome;
        this.status = status;
    }

    public Categoria(String nome) {
        this.nome = nome;
        this.status = CategoriaStatus.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaStatus getStatus() {
        return status;
    }

    public void setStatus(CategoriaStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
