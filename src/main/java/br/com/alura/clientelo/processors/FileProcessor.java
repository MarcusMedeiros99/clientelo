package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.estatisticas.PedidoDTO;

public interface FileProcessor {

    PedidoDTO[] processaArquivo();
}
