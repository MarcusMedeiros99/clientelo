package br.com.alura.clientelo;

import br.com.alura.clientelo.repositories.CategoriaEstatisticasRepository;
import br.com.alura.clientelo.repositories.ClienteEstatisticasRepository;
import br.com.alura.clientelo.repositories.ProdutoEstatisticasRepository;

import java.util.*;

public class PedidosEstatisticas {
    private final ProdutoEstatisticasRepository produtos;
    private final CategoriaEstatisticasRepository categorias;
    private final ClienteEstatisticasRepository clientes;
    private final ClienteEstatisticasRepository clientesMaisLucrativos;

    public PedidosEstatisticas() {
        this.produtos = new ProdutoEstatisticasRepository();
        this.categorias = new CategoriaEstatisticasRepository();
        this.clientes = new ClienteEstatisticasRepository();
        this.clientesMaisLucrativos = new ClienteEstatisticasRepository(
                Comparator.comparing(ClienteEstatisticas::getMontanteGasto)
                        .reversed());
    }

    public void addPedidos(Pedido[] pedidos) {
        List<Pedido> pedidosList = Arrays.asList(pedidos);

        pedidosList
                .stream()
                .filter(pedido -> pedido != null)
                .forEach((this::addPedido));
    }

    public void addPedido(Pedido pedido) {
        produtos.insert(pedido);
        categorias.insert(pedido);
        clientes.insert(pedido);
        clientesMaisLucrativos.insert(pedido);
    }


    public List<ProdutoEstatisticas> produtosMaisVendidos(int n) {
        return produtos.getAll()
                .stream()
                .limit(n)
                .toList();
    }

    public List<CategoriaEstatisticas> vendasPorCategoria() {
        return categorias.getAll().stream()
                .toList();
    }

    public List<ClienteEstatisticas> vendasPorCliente() {
        return clientes.getAll().stream()
                .toList();
    }

    public List<ClienteEstatisticas> clientesMaisLucrativos(int n) {
        return clientesMaisLucrativos.getAll().stream()
                .limit(n)
                .sorted(Comparator.comparing(ClienteEstatisticas::getNome))
                .toList();
    }
}
