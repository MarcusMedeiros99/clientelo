package br.com.alura.clientelo.controller.cliente;

import br.com.alura.clientelo.core.entity.cliente.Cliente;

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
