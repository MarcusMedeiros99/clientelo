package br.com.alura.clientelo.web.dao.categoria;

import java.math.BigDecimal;

public class VendasPorCategoriaVO {
    private String nomeCategoria;
    private Long quantidade;
    private BigDecimal montante;

    public VendasPorCategoriaVO(String nomeCategoria, Long quantidade, BigDecimal montante) {
        this.nomeCategoria = nomeCategoria;
        this.quantidade = quantidade;
        this.montante = montante;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Long getQuantidade() {
        return quantidade == null ? 0 : quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getMontante() {
        return montante == null? BigDecimal.ZERO.setScale(2) : montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    @Override
    public String toString() {
        return "VendasPorCategoriaVO{" +
                "nomeCategoria='" + nomeCategoria + '\'' +
                ", quantidade=" + quantidade +
                ", montante=" + montante +
                '}';
    }
}
