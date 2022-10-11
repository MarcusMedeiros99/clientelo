package br.com.alura.clientelo;

import java.util.List;

public class Relatorio {
    private PedidosEstatisticas pedidosEstatisticas;

    public Relatorio(PedidosEstatisticas pedidosEstatisticas) {
        this.pedidosEstatisticas = pedidosEstatisticas;
    }

    public String produtosMaisVendidos(int n) {
        StringBuffer buffer = new StringBuffer("\n");
        List<ProdutoEstatisticas> produtos = pedidosEstatisticas.produtosMaisVendidos(n);

        for (int i = 0; i < n; i++) {
            ProdutoEstatisticas produto = produtos.get(i);
            buffer.append((i + 1) + "º PRODUTO MAIS VENDIDO: " + produto.getProduto() + "(" + produto.getQtdDeVendas() + " vendas)\n" );
        }
        return buffer.toString();
    }

    public String vendasPorCategoria() {
        StringBuffer buffer = new StringBuffer("\nVENDAS POR CATEGORIA\n");
        List<CategoriaEstatisticas> categorias = pedidosEstatisticas.vendasPorCategoria();

        for (int i = 0; i < categorias.size(); i++) {
            CategoriaEstatisticas categoria = categorias.get(i);
            buffer.append("CATEGORIA: " + categoria.getCategoria() + "\n");
            buffer.append("QUANTIDADE VENDIDA: " + categoria.getQtdVendas() + "\n");
            buffer.append("MONTANTE: " + categoria.getMontante() + "\n\n");
        }
        return buffer.toString();
    }

    public String maisCaroPorCategoria() {
        StringBuffer buffer = new StringBuffer("\nMAIS CARO POR CATEGORIA\n");
        List<CategoriaEstatisticas> categorias = pedidosEstatisticas.vendasPorCategoria();

        for (int i = 0; i < categorias.size(); i++) {
            CategoriaEstatisticas categoria = categorias.get(i);
            buffer.append("CATEGORIA: " + categoria.getCategoria() + "\n");
            buffer.append("PRODUTO: " + categoria.getProdutoMaisCaro() + "\n");
            buffer.append("PREÇO: " + categoria.getPrecoMaisCaro().toPlainString() + "\n\n");
        }

        return buffer.toString();
    }

    public String pedidosPorCliente() {
        StringBuffer buffer = new StringBuffer("\nVENDAS POR CLIENTE\n");
        List<ClienteEstatisticas> clientes = pedidosEstatisticas.vendasPorCliente();

        for (int i = 0; i < clientes.size(); i++) {
            ClienteEstatisticas cliente = clientes.get(i);
            buffer.append("Nº DE PEDIDOS: " + cliente.getQtdPedidos() + "\n");
            buffer.append("NOME: " + cliente.getNome() + "\n\n");
        }
        return buffer.toString();
    }

    public String clientesMaisLucrativos() { StringBuffer buffer = new StringBuffer("\nCLIENTES MAIS LUCRATIVOS\n");
        List<ClienteEstatisticas> clientes = pedidosEstatisticas.clientesMaisLucrativos(2);

        for (int i = 0; i < clientes.size(); i++) {
            ClienteEstatisticas cliente = clientes.get(i);
            buffer.append("NOME: " + cliente.getNome() + "\n");
            buffer.append("Nº DE PEDIDOS: " + cliente.getQtdPedidos() + "\n");
            buffer.append("NOME: " + cliente.getMontanteGasto() + "\n\n");
        }
        return buffer.toString();
    }
}
