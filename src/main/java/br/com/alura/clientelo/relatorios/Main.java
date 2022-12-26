package br.com.alura.clientelo.relatorios;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;
import br.com.alura.clientelo.relatorios.processors.FileProcessor;
import br.com.alura.clientelo.relatorios.processors.ProcessorFactory;
import br.com.alura.clientelo.relatorios.relatorio.Relatorio;
import br.com.alura.clientelo.relatorios.relatorio.RelatorioPresenter;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {



    public static void main(String[] args) throws IOException, URISyntaxException {

        FileProcessor fileProcessor = new ProcessorFactory().from("pedidos.json");
        PedidoDTO[] pedidos = fileProcessor.processaArquivo();

        Relatorio relatorio = new Relatorio(pedidos);
        RelatorioPresenter relatorioPresenter = new RelatorioPresenter(relatorio);
        relatorioPresenter.show();

    }
}
