package br.com.alura.clientelo.web.controller.cliente;

import br.com.alura.clientelo.web.core.entity.cliente.Cliente;

public class ClienteDto {
    private final Long id;
    private final String nome;
    private final String cpf;
    private final String telefone;
    private final String rua;
    private final Integer numero;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String estado;

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.rua = cliente.getAddress().getRua();
        this.numero = cliente.getAddress().getNumero();
        this.complemento = cliente.getAddress().getComplemento();
        this.bairro = cliente.getAddress().getBairro();
        this.cidade = cliente.getAddress().getCidade();
        this.estado = cliente.getAddress().getEstadoUF();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }
}
