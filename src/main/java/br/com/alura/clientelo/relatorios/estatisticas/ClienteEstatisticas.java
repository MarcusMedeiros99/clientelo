package br.com.alura.clientelo.relatorios.estatisticas;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

public class ClienteEstatisticas implements Comparable<ClienteEstatisticas> {
    private String nome;
    private Integer qtdPedidos;
    private BigDecimal montanteGasto;


    public ClienteEstatisticas(PedidoDTO pedido) {
        this.nome = pedido.getCliente();
        this.qtdPedidos = 1;
        this.montanteGasto = pedido.getValorTotal();
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

    public void adicionaPedido(PedidoDTO pedido) {
        this.qtdPedidos += 1;
        this.montanteGasto = this.montanteGasto
                .add(pedido.getValorTotal());
    }

    @Override
    public int compareTo(ClienteEstatisticas clienteEstatisticas) {
        if (clienteEstatisticas != null) {
            int result = -Integer.compare(qtdPedidos,clienteEstatisticas.qtdPedidos);
            if (result == 0) return this.nome.compareTo(clienteEstatisticas.nome);

            return Comparator.comparing(ClienteEstatisticas::getQtdPedidos).reversed()
                    .thenComparing(ClienteEstatisticas::getNome)
                    .compare(this, clienteEstatisticas);
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
