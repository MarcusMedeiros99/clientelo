package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JsonProcessor implements FileProcessor {
    private final String path;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public JsonProcessor(String path) {
        this.path = path;
    }

    @Override
    public Pedido[] processaArquivo() {
        try {
            URL recursoJson = ClassLoader.getSystemResource(path);
            File file = new File(recursoJson.toURI());
            List<PedidoDeserializer> pedidos = objectMapper.readValue(file, new TypeReference<List<PedidoDeserializer>>() {});
            Object[] pedidosArray = pedidos.stream().map(PedidoDeserializer::toPedido).toArray();
            return Arrays.copyOf(pedidosArray, pedidosArray.length, Pedido[].class);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found %s", path));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to parse json from %s", path));
        }


    }

}
