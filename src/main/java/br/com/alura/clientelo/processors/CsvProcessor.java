package br.com.alura.clientelo.processors;

import br.com.alura.clientelo.Pedido;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class CsvProcessor implements FileProcessor {
    private final String path;

    CsvProcessor(String path) {
        this.path = path;
    }

    public Pedido[] processaArquivo() {
        try {
            URL recursoCSV = ClassLoader.getSystemResource(path);
            File file = new File(recursoCSV.toURI());
            FileReader fileReader = new FileReader(file);

            List<PedidoDeserializer> beans
                    = new CsvToBeanBuilder<PedidoDeserializer>(fileReader)
                    .withSeparator(',').withType(PedidoDeserializer.class)
                    .build().parse();

            Object[] pedidos = beans.stream().map(PedidoDeserializer::toPedido).toArray();

            return Arrays.copyOf(pedidos, pedidos.length, Pedido[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Arquivo {} não localizado!", path));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir Scanner para processar arquivo!");
        }
    }
}
