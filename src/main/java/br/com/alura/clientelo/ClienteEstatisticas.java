package br.com.alura.clientelo;

import java.math.BigDecimal;
import java.util.Objects;

public class ClienteEstatisticas implements Comparable<ClienteEstatisticas> {
    private String nome;
    private Integer qtdPedidos;
    private BigDecimal montanteGasto;

    private ClienteEstatisticas(String nome, Integer qtdPedidos, BigDecimal montanteGasto) {
        this.nome = nome;
        this.qtdPedidos = qtdPedidos;
        this.montanteGasto = montanteGasto;
    }

    public ClienteEstatisticas(Pedido pedido) {
        this(pedido.getCliente(), pedido.getQuantidade(), pedido.getPreco());
    }

    public Integer getQtdPedidos() {
        return this.qtdPedidos;
    }

    public String getNome() {
        return this.nome;
    }

    public BigDecimal getMontanteGasto() {
        return montanteGasto;
    }

    public void adicionaPedido(Pedido pedido) {
        this.qtdPedidos += pedido.getQuantidade();
        this.montanteGasto = this.montanteGasto.add(pedido.getPreco());
    }

    @Override
    public int compareTo(ClienteEstatisticas clienteEstatisticas) {
        if (clienteEstatisticas != null) {
            if (this.qtdPedidos.equals(clienteEstatisticas.qtdPedidos)) return this.nome.compareTo(clienteEstatisticas.nome);
            return -this.qtdPedidos.compareTo(clienteEstatisticas.qtdPedidos);
        }

        throw new NullPointerException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEstatisticas that = (ClienteEstatisticas) o;
        return Objects.equals(nome, that.nome) && Objects.equals(qtdPedidos, that.qtdPedidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, qtdPedidos);
    }
}
