package br.com.alura.clientelo;

import java.util.*;

public class PedidosEstatisticas {
    private final Set<ProdutoEstatisticas> produtos;
    private final Map<String, ProdutoEstatisticas> produtoToEstatisticas;
    private final Set<String> categorias;
    private final Map<String, CategoriaEstatisticas> categoriaToEstatisticas;
    private final Set<ClienteEstatisticas> clientes;
    private final Map<String, ClienteEstatisticas> clienteToEstatisticas;

    public PedidosEstatisticas() {
        this.produtos = new TreeSet<>();
        this.produtoToEstatisticas = new HashMap<>();
        this.categoriaToEstatisticas = new HashMap<>();
        this.categorias = new TreeSet<>();
        this.clienteToEstatisticas = new HashMap<>();
        this.clientes = new TreeSet<>();
    }

    public void addPedidos(Pedido[] pedidos) {
        for (Pedido pedido:
             pedidos) {
            if (pedido != null)  {
                String produto = pedido.getProduto();
                String categoria = pedido.getCategoria();
                String cliente = pedido.getCliente();
                insertProduto(pedido, produto);
                insertCategoria(pedido, categoria);
                insertCliente(pedido, cliente);
            }
        }
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
        clienteEstatisticas.adicionaPedido(pedido);
        clientes.add(clienteEstatisticas);
    }

    private void insertNovoCliente(Pedido pedido, String cliente) {
        ClienteEstatisticas clienteEstatisticas = new ClienteEstatisticas(pedido);
        clientes.add(clienteEstatisticas);
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
        List<ProdutoEstatisticas> maisVendidos = new ArrayList<>(n);

        Iterator<ProdutoEstatisticas> iterator = produtos.iterator();
        int i = 0;
        while (iterator.hasNext() && i < 3) {
            maisVendidos.add(iterator.next());
            i++;
        }

        return maisVendidos;
    }

    public List<CategoriaEstatisticas> vendasPorCategoria() {
        List<CategoriaEstatisticas> answer = new ArrayList<>();
        for (String categoria :
                categorias) {
            answer.add(categoriaToEstatisticas.get(categoria));
        }
        return answer;
    }

    public List<ClienteEstatisticas> vendasPorCliente() {
        List<ClienteEstatisticas> answer = new ArrayList<>();
        for (ClienteEstatisticas cliente: clientes) {
            answer.add(clienteToEstatisticas.get(cliente.getNome()));
        }
        return answer;
    }
}
