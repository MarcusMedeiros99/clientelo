package br.com.alura.clientelo.relatorios.processors;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;

public interface FileProcessor {

    PedidoDTO[] processaArquivo();
}
