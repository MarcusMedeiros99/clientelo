package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.models.Address;
import br.com.alura.clientelo.models.Cliente;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClienteCreateForm {
    @NotNull @NotEmpty
    private String nome;
    @NotNull @Size(min = 11, max = 11)
    private String cpf;
    @NotNull @NotEmpty
    private String telefone;
    @NotNull @NotEmpty
    private String rua;
    @NotNull
    @Min(1)
    private Integer numero;
    private String complemento;
    @NotNull
    @NotEmpty
    private String bairro;
    @NotNull
    @NotEmpty
    private String cidade;
    @NotNull
    @Size(min = 2, max = 2)
    private String estado;

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

    public Cliente convert() {
        Address address = new Address();
        address.setEstadoUF(estado);
        address.setRua(rua);
        address.setComplemento(complemento);
        address.setNumero(numero);
        address.setBairro(bairro);
        address.setCidade(cidade);

        return new Cliente
                .Builder()
                .withNome(nome)
                .withCPF(cpf)
                .withTelefone(telefone)
                .withAddress(address)
                .build();
    }
}
