package br.com.alura.clientelo.models;

import javax.persistence.*;

@Entity
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cpf", nullable = false, columnDefinition = "CHAR(11)", unique = true)
    private String cpf;
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;
    @Embedded
    private Address address;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    public static class Builder {

        private String nome;
        private Address address;
        private String cpf;
        private String telefone;

        public Cliente build() {
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setAddress(address);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);

            return cliente;
        }

        public Builder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder withCPF(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder withTelefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }
    }
}
