package br.com.alura.clientelo;

import java.util.*;

public class PedidosEstatisticas {
    private final Set<ProdutoEstatisticas> produtos;
    private final Map<String, ProdutoEstatisticas> produtoToEstatisticas;
    private final Set<String> categorias;
    private final Map<String, CategoriaEstatisticas> categoriaToEstatisticas;
    private final Set<ClienteEstatisticas> clientes;
    private final Map<String, ClienteEstatisticas> clienteToEstatisticas;
    private final Set<ClienteEstatisticas> clientesMaisLucrativos;

    public PedidosEstatisticas() {
        this.produtos = new TreeSet<>();
        this.produtoToEstatisticas = new HashMap<>();
        this.categoriaToEstatisticas = new HashMap<>();
        this.categorias = new TreeSet<>();
        this.clienteToEstatisticas = new HashMap<>();
        this.clientes = new TreeSet<>();
        this.clientesMaisLucrativos = new TreeSet<>(
                Comparator.comparing(ClienteEstatisticas::getMontanteGasto)
                        .reversed()
                        .thenComparing(ClienteEstatisticas::getNome));
    }

    public void addPedidos(Pedido[] pedidos) {
        List<Pedido> pedidosList = Arrays.asList(pedidos);

        pedidosList
                .stream()
                .filter(pedido -> pedido != null)
                .forEach((this::addPedido));
    }

    public void addPedido(Pedido pedido) {
        insertProduto(pedido, pedido.getProduto());
        insertCategoria(pedido, pedido.getCategoria());
        insertCliente(pedido, pedido.getCliente());
    }

    private void insertCliente(Pedido pedido, String cliente) {
        if (!clienteToEstatisticas.containsKey(cliente)) {
            insertNovoCliente(pedido, cliente);
        }
        else {
            updateCliente(pedido, cliente);
        }
    }

    private void updateCliente(Pedido pedido, String cliente) {
        ClienteEstatisticas clienteEstatisticas = clienteToEstatisticas.get(cliente);
        clientes.remove(clienteEstatisticas);
        clientesMaisLucrativos.remove(clienteEstatisticas);
        
        clienteEstatisticas.adicionaPedido(pedido);
        
        clientes.add(clienteEstatisticas);
        clientesMaisLucrativos.add(clienteEstatisticas);
    }

    private void insertNovoCliente(Pedido pedido, String cliente) {
        ClienteEstatisticas clienteEstatisticas = new ClienteEstatisticas(pedido);
        clientes.add(clienteEstatisticas);
        clientesMaisLucrativos.add(clienteEstatisticas);
        clienteToEstatisticas.put(cliente, clienteEstatisticas);
    }

    private void insertCategoria(Pedido pedido, String categoria) {
        if (!categorias.contains(categoria)) {
            insertNovaCategoria(pedido, categoria);
        }
        else {
            updateCategoria(pedido, categoria);
        }
    }

    private void updateCategoria(Pedido pedido, String categoria) {
        CategoriaEstatisticas categoriaEstatisticas = categoriaToEstatisticas.get(categoria);
        categoriaEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovaCategoria(Pedido pedido, String categoria) {
        CategoriaEstatisticas categoriaEstatisticas = new CategoriaEstatisticas(pedido);
        categorias.add(categoria);
        categoriaToEstatisticas.put(categoria, categoriaEstatisticas);
    }

    private void insertProduto(Pedido pedido, String produto) {
        if (!produtoToEstatisticas.containsKey(produto)) insertNovoProduto(pedido, produto);
        else updateProduto(pedido, produto);
    }

    private void updateProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas produtoEstatisticas = produtoToEstatisticas.get(produto);
        produtoEstatisticas.adicionaNVendas(pedido.getQuantidade());
    }

    private void insertNovoProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas produtoEstatisticas = new ProdutoEstatisticas(pedido);
        produtos.add(produtoEstatisticas);
        produtoToEstatisticas.put(produto, produtoEstatisticas);
    }

    public List<ProdutoEstatisticas> produtosMaisVendidos(int n) {
        return produtos.stream()
                .limit(n)
                .toList();
    }

    public List<CategoriaEstatisticas> vendasPorCategoria() {
        return categorias.stream()
                .map(categoriaToEstatisticas::get)
                .toList();
    }

    public List<ClienteEstatisticas> vendasPorCliente() {
        return clientes.stream()
                .map(cliente -> clienteToEstatisticas.get(cliente.getNome()))
                .toList();
    }

    public List<ClienteEstatisticas> clientesMaisLucrativos(int n) {
        return clientesMaisLucrativos.stream()
                .limit(n)
                .sorted(Comparator.comparing(ClienteEstatisticas::getNome))
                .toList();
//        List<ClienteEstatisticas> maisLucrativos = new ArrayList<>(n);
//
//        Iterator<ClienteEstatisticas> iterator = clientesMaisLucrativos.iterator();
//        int i = 0;
//        while (iterator.hasNext() && i < n) {
//            maisLucrativos.add(iterator.next());
//            i++;
//        }
//
//        maisLucrativos.sort(Comparator.comparing(ClienteEstatisticas::getNome));
//
//        return maisLucrativos;
    }
}
