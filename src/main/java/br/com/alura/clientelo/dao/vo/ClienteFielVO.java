package br.com.alura.clientelo.dao.vo;

import java.math.BigDecimal;

public class ClienteFielVO {
    private String nome;
    private Long quantidadeDePedidos;
    private BigDecimal montanteGasto;

    public ClienteFielVO(String nome, Long quantidadeDePedidos, BigDecimal montanteGasto) {
        this.nome = nome;
        this.quantidadeDePedidos = quantidadeDePedidos;
        this.montanteGasto = montanteGasto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getQuantidadeDePedidos() {
        return quantidadeDePedidos;
    }

    public void setQuantidadeDePedidos(Long quantidadeDePedidos) {
        this.quantidadeDePedidos = quantidadeDePedidos;
    }

    public BigDecimal getMontanteGasto() {
        return montanteGasto;
    }

    public void setMontanteGasto(BigDecimal montanteGasto) {
        this.montanteGasto = montanteGasto;
    }

    @Override
    public String toString() {
        return "ClienteFielVO{" +
                "nome='" + nome + '\'' +
                ", quantidadeDePedidos=" + quantidadeDePedidos +
                ", montanteGasto=" + montanteGasto +
                '}';
    }
}
