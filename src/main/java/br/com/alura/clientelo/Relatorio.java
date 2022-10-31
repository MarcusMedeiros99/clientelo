package br.com.alura.clientelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class Relatorio {
    private EstatisticasService estatisticasService;
    private static DecimalFormat decimalFormatter;

    static {
        decimalFormatter = new DecimalFormat();
        DecimalFormatSymbols dfs = decimalFormatter.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        decimalFormatter.applyPattern("R$ #,###.00");
    }

    private Relatorio(EstatisticasService estatisticasService) {
        this.estatisticasService = estatisticasService;
    }

    public Relatorio(Pedido[] pedidos) {
        this(new EstatisticasService(pedidos));
    }

    public String produtosMaisVendidos(int n) {
        StringBuffer buffer = new StringBuffer("\n");
        List<ProdutoEstatisticas> produtos = estatisticasService.produtosMaisVendidos(n);

        for (int i = 0; i < n; i++) {
            ProdutoEstatisticas produto = produtos.get(i);
            buffer.append((i + 1) + "º PRODUTO MAIS VENDIDO: " + produto.getProduto() + "(" + produto.getQtdDeVendas() + " vendas)\n" );
        }
        return buffer.toString();
    }

    public String vendasPorCategoria() {
        StringBuffer buffer = new StringBuffer("\nVENDAS POR CATEGORIA\n");
        List<CategoriaEstatisticas> categorias = estatisticasService.vendasPorCategoria();

        for (CategoriaEstatisticas categoria : categorias) {
            buffer.append("CATEGORIA: " + categoria.getCategoria() + "\n");
            buffer.append("QUANTIDADE VENDIDA: " + categoria.getQtdVendas() + "\n");
            buffer.append("MONTANTE: " + decimalFormatter.format(categoria.getMontante()) + "\n\n");
        }
        return buffer.toString();
    }

    public String maisCaroPorCategoria() {
        StringBuffer buffer = new StringBuffer("\nMAIS CARO POR CATEGORIA\n");
        List<CategoriaEstatisticas> categorias = estatisticasService.vendasPorCategoria();

        for (CategoriaEstatisticas categoria : categorias) {
            buffer.append("CATEGORIA: " + categoria.getCategoria() + "\n");
            buffer.append("PRODUTO: " + categoria.getProdutoMaisCaro() + "\n");
            buffer.append("PREÇO: " + decimalFormatter.format(categoria.getPrecoMaisCaro()) + "\n\n");
        }

        return buffer.toString();
    }

    public String pedidosPorCliente() {
        StringBuffer buffer = new StringBuffer("\nVENDAS POR CLIENTE\n");
        List<ClienteEstatisticas> clientes = estatisticasService.vendasPorCliente();

        for (ClienteEstatisticas cliente : clientes) {
            buffer.append("Nº DE PEDIDOS: " + cliente.getQtdPedidos() + "\n");
            buffer.append("NOME: " + cliente.getNome() + "\n\n");
        }
        return buffer.toString();
    }

    public String clientesMaisLucrativos() {
        StringBuffer buffer = new StringBuffer("\nCLIENTES MAIS LUCRATIVOS\n");
        List<ClienteEstatisticas> clientes = estatisticasService.clientesMaisLucrativos(2);

        for (ClienteEstatisticas cliente : clientes) {
            buffer.append("NOME: " + cliente.getNome() + "\n");
            buffer.append("Nº DE PEDIDOS: " + cliente.getQtdPedidos() + "\n");
            buffer.append("VALOR GASTO: " + decimalFormatter.format(cliente.getMontanteGasto()) + "\n\n");
        }
        return buffer.toString();
    }

    public String totalDePedidos() {
        return "TOTAL DE PEDIDOS REALIZADOS: " + estatisticasService.totalDePedidos();
    }

    public String totalDeProdutosVendidos() {
        return "TOTAL DE PRODUTOS VENDIDOS: " + estatisticasService.totalDeProdutosVendidos();
    }

    public String totalDeCategorias() {
        return "TOTAL DE CATEGORIAS: " + estatisticasService.totalDeCategorias();
    }

    public String montanteDeVendas() {
        return "MONTANTE DE VENDAS: " +  decimalFormatter.format(estatisticasService.montanteTotal());
    }

    public String pedidoMaisBarato() {
        Pedido maisBarato = estatisticasService.pedidoMaisBarato().get();
        return "PEDIDO MAIS BARATO: " + decimalFormatter.format(maisBarato.getValorTotal()) + " (" + maisBarato.getProduto() + ")";
    }

    public String pedidoMaisCaro() {
        Pedido maisCaro = estatisticasService.pedidoMaisCaro().get();
        return "PEDIDO MAIS BARATO: " + decimalFormatter.format(maisCaro.getValorTotal()) + " (" + maisCaro.getProduto() + ")";
    }
}
