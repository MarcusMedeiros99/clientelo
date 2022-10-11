package br.com.alura.clientelo;

import java.util.Objects;

public class ClienteEstatisticas implements Comparable<ClienteEstatisticas> {
    private String nome;
    private Integer qtdPedidos;

    private ClienteEstatisticas(String nome, Integer qtdPedidos) {
        this.nome = nome;
        this.qtdPedidos = qtdPedidos;
    }

    public ClienteEstatisticas(Pedido pedido) {
        this(pedido.getCliente(), pedido.getQuantidade());
    }

    public Integer getQtdPedidos() {
        return this.qtdPedidos;
    }

    public String getNome() {
        return this.nome;
    }

    public void adicionaPedido(Pedido pedido) {
        this.qtdPedidos += pedido.getQuantidade();
    }

    @Override
    public int compareTo(ClienteEstatisticas clienteEstatisticas) {
        if (clienteEstatisticas != null) {
//            if (!this.qtdPedidos.equals(clienteEstatisticas.qtdPedidos))
//                return -this.qtdPedidos.compareTo(clienteEstatisticas.qtdPedidos);
//            return this.nome.compareTo(clienteEstatisticas.nome);

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
