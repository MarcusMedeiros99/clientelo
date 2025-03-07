package br.com.alura.clientelo.relatorios.processors;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;
import br.com.alura.clientelo.relatorios.processors.JsonProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonProcessorTest {
    @Test
    void shouldProcessarArquivo() {

        String path = "./pedidos.json";

        JsonProcessor processor = new JsonProcessor(path);
        PedidoDTO[] pedidos = processor.processaArquivo();

        assertEquals(16, pedidos.length);
    }

    @Test
    void shouldThrowRunTimeExceptionWhenFileNotFound() {
        String path = "/nao_existo.json";
        JsonProcessor processor = new JsonProcessor(path);
        assertThrows(RuntimeException.class, processor::processaArquivo);
    }
}