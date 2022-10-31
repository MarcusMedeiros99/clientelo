package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;

public interface FileProcessor {

    Pedido[] processaArquivo();
}
