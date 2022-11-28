package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Cliente;

public class ClienteOnListDto {
    private String nome;
    private String cpf;
    private String telefone;
    private String local;

    public ClienteOnListDto(Cliente cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.local = cliente.getAddress().getCidade() + "/" + cliente.getAddress().getEstadoUF();
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

    public String getLocal() {
        return local;
    }
}
