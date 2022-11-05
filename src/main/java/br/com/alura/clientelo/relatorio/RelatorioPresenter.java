package br.com.alura.clientelo.relatorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelatorioPresenter {
    private final Relatorio relatorio;
    private final Logger logger = LoggerFactory.getLogger(RelatorioPresenter.class);

    public RelatorioPresenter(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public void show() {
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
