package br.com.alura.clientelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        Pedido[] pedidos = ProcessadorDeCsv.processaArquivo("pedidos.csv");

        EstatisticasService estatisticasService = new EstatisticasService();
        estatisticasService.addPedidos(pedidos);

        Relatorio relatorio = new Relatorio(estatisticasService);

        logger.info("##### RELATÓRIO DE VALORES TOTAIS #####");
        logger.info(relatorio.totalDePedidos());
        logger.info(relatorio.totalDeProdutosVendidos());
        logger.info(relatorio.totalDeCategorias());
        logger.info(relatorio.montanteDeVendas());
        logger.info(relatorio.pedidoMaisBarato());
        logger.info(relatorio.pedidoMaisCaro());

        logger.info(relatorio.produtosMaisVendidos(3));
        logger.info(relatorio.vendasPorCategoria());
        logger.info(relatorio.maisCaroPorCategoria());
        logger.info(relatorio.pedidosPorCliente());
        logger.info(relatorio.clientesMaisLucrativos());
        logger.info("### FIM DO RELATÓRIO ###");

    }
}
