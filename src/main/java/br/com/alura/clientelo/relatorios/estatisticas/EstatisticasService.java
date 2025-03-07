package br.com.alura.clientelo.relatorios.estatisticas;

import br.com.alura.clientelo.relatorios.repositories.EstatisticasRepository;
import br.com.alura.clientelo.relatorios.repositories.CategoriaEstatisticasRepository;
import br.com.alura.clientelo.relatorios.repositories.ClienteEstatisticasRepository;
import br.com.alura.clientelo.relatorios.repositories.ProdutoEstatisticasRepository;

import java.math.BigDecimal;
import java.util.*;

public class EstatisticasService {
    private final EstatisticasRepository<ProdutoEstatisticas> produtos;
    private final EstatisticasRepository<CategoriaEstatisticas> categorias;
    private final EstatisticasRepository<ClienteEstatisticas> clientes;
    private final EstatisticasRepository<ClienteEstatisticas> clientesMaisLucrativos;
    private final List<PedidoDTO> pedidos;

    public EstatisticasService() {
        this.produtos = new ProdutoEstatisticasRepository();
        this.categorias = new CategoriaEstatisticasRepository();
        this.clientes = new ClienteEstatisticasRepository();
        this.clientesMaisLucrativos = new ClienteEstatisticasRepository(
                Comparator.comparing(ClienteEstatisticas::getMontanteGasto)
                        .reversed());
        this.pedidos = new ArrayList<>();
    }

    public EstatisticasService(PedidoDTO[] pedidos) {
        this();
        addPedidos(pedidos);
    }

    public void addPedidos(PedidoDTO[] pedidos) {
        List<PedidoDTO> pedidosList = Arrays.asList(pedidos);

        pedidosList
                .stream()
                .filter(pedido -> pedido != null)
                .forEach((this::addPedido));
    }

    private void addPedido(PedidoDTO pedido) {
        produtos.insert(pedido);
        categorias.insert(pedido);
        clientes.insert(pedido);
        clientesMaisLucrativos.insert(pedido);
        pedidos.add(pedido);
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

    public Optional<PedidoDTO> pedidoMaisCaro() {
        return pedidos.stream().max(Comparator.comparing(PedidoDTO::getValorTotal));
    }

    public Optional<PedidoDTO> pedidoMaisBarato() {
        return pedidos.stream().min(Comparator.comparing(PedidoDTO::getValorTotal));
    }

    public BigDecimal montanteTotal() {
        return pedidos.stream().map(PedidoDTO::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer totalDeCategorias() {
        return categorias.size();
    }

    public Integer totalDeProdutosVendidos() {
        return pedidos.stream().map(PedidoDTO::getQuantidade).reduce(0, Integer::sum);
    }

    public Integer totalDePedidos() {
        return pedidos.size();
    }
}
