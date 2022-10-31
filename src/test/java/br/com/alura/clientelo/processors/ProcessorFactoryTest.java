package br.com.alura.clientelo.processors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorFactoryTest {
    private static final String CSV_PATH = "path.csv";
    private static final String NO_EXTENSION_PATH = "path_without_extension";
    private static final String PATH_WITH_UNKNOWN_EXTENSION = "path.unknown_extension";


    @Test
    void shouldCreateCsvProcessorWhenExtensionIsCsv() {
        FileProcessor fileProcessor = new ProcessorFactory().from(CSV_PATH);

        assertEquals(fileProcessor.getClass(), CsvProcessor.class);
    }

    @Test
    void shouldThrowInvallidArgumentExceptionWhenPathHasNoExtension() {
        assertThrows(IllegalArgumentException.class,
                () -> {new ProcessorFactory().from(NO_EXTENSION_PATH);
        });

    }

    @Test
    void shouldThrowInvallidArgumentExceptionWhenExtensionIsNotKnown() {
        assertThrows(IllegalArgumentException.class,
                () -> {new ProcessorFactory().from(PATH_WITH_UNKNOWN_EXTENSION);
        });
    }

}