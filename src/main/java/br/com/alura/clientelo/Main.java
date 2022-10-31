package br.com.alura.clientelo;

import br.com.alura.clientelo.processors.FileProcessor;
import br.com.alura.clientelo.processors.ProcessorFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {



    public static void main(String[] args) throws IOException, URISyntaxException {

        FileProcessor fileProcessor = new ProcessorFactory().from("pedidos.json");
        Pedido[] pedidos = fileProcessor.processaArquivo();

        Relatorio relatorio = new Relatorio(pedidos);
        RelatorioPresenter relatorioPresenter = new RelatorioPresenter(relatorio);
        relatorioPresenter.show();

    }
}
