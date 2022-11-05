package br.com.alura.clientelo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "rua", nullable = false, length = 50)
    String rua;
    @Column(name = "numero", nullable = false)
    Integer numero;
    @Column(name = "complemento", length = 50)
    String complemento;
    @Column(name = "bairro", nullable = false, length = 30)
    String bairro;
    @Column(name = "cidade", nullable = false, length = 30)
    String cidade;
    @Column(name = "estado", nullable = false, length = 2)
    String estadoUF;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstadoUF() {
        return estadoUF;
    }

    public void setEstadoUF(String estadoUF) {
        this.estadoUF = estadoUF;
    }
}
