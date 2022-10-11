package br.com.alura.clientelo;

import java.util.*;

public class PedidosEstatisticas {
    private final Set<ProdutoEstatisticas> produtosEstatisticas;
    private final Map<String, ProdutoEstatisticas> produtos;
    private final Set<String> categorias;
    private final Map<String, CategoriaEstatisticas> categoriasEstatisticas;
    private final Set<String> clientes;
    private final Map<String, ClienteEstatisticas> clientesEstatisticas;

    public PedidosEstatisticas() {
        this.produtosEstatisticas = new TreeSet<>();
        this.produtos = new HashMap<>();
        this.categoriasEstatisticas = new HashMap<>();
        this.categorias = new TreeSet<>();
        this.clientesEstatisticas = new HashMap<>();
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
        if (!clientes.contains(cliente)) {
            insertNovoCliente(pedido, cliente);
        }
        else {
            updateCliente(pedido, cliente);
        }
    }

    private void updateCliente(Pedido pedido, String cliente) {
        ClienteEstatisticas clienteEstatisticas = clientesEstatisticas.get(cliente);
        clienteEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovoCliente(Pedido pedido, String cliente) {
        ClienteEstatisticas clienteEstatisticas = new ClienteEstatisticas(pedido);
        clientes.add(cliente);
        clientesEstatisticas.put(cliente, clienteEstatisticas);
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
        CategoriaEstatisticas categoriaEstatisticas = categoriasEstatisticas.get(categoria);
        categoriaEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovaCategoria(Pedido pedido, String categoria) {
        CategoriaEstatisticas categoriaEstatisticas = new CategoriaEstatisticas(pedido);
        categorias.add(categoria);
        categoriasEstatisticas.put(categoria, categoriaEstatisticas);
    }

    private void insertProduto(Pedido pedido, String produto) {
        if (!produtos.containsKey(produto)) insertNovoProduto(pedido, produto);
        else updateProduto(pedido, produto);
    }

    private void updateProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas produtoEstatisticas = produtos.get(produto);
        produtoEstatisticas.adicionaNVendas(pedido.getQuantidade());
    }

    private void insertNovoProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas produtoEstatisticas = new ProdutoEstatisticas(pedido);
        produtosEstatisticas.add(produtoEstatisticas);
        produtos.put(produto, produtoEstatisticas);
    }

    public List<ProdutoEstatisticas> produtosMaisVendidos(int n) {
        List<ProdutoEstatisticas> maisVendidos = new ArrayList<>(n);

        Iterator<ProdutoEstatisticas> iterator = produtosEstatisticas.iterator();
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
            answer.add(categoriasEstatisticas.get(categoria));
        }
        return answer;
    }

    public List<ClienteEstatisticas> vendasPorCliente() {
        List<ClienteEstatisticas> answer = new ArrayList<>();
        for (String cliente: clientes) {
            answer.add(clientesEstatisticas.get(cliente));
        }
        return answer;
    }
}
