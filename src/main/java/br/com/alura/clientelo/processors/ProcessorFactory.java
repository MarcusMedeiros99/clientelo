package br.com.alura.clientelo.processors;

public final class ProcessorFactory {

    public FileProcessor from(String path) {
        String extension = getExtensionFrom(path);

        if (extension.equals("csv")) {
            return new CsvProcessor(path);
        }
        throw new IllegalArgumentException("Extension " + extension + " is not known.");
    }

    private String getExtensionFrom(String path) {
        String[] blocks = path.split("[.]");
        if (blocks.length < 2) throw new IllegalArgumentException("Path does not contain extension\n");

        return blocks[blocks.length - 1];
    }
}
