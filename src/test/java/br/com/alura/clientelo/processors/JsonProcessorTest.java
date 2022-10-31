package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonProcessorTest {



    @Test
    void shouldProcessarArquivo() throws URISyntaxException {

        String path = "./pedidos.json";

        JsonProcessor processor = new JsonProcessor(path);
        Pedido[] pedidos = processor.processaArquivo();

        assertEquals(16, pedidos.length);
    }
}