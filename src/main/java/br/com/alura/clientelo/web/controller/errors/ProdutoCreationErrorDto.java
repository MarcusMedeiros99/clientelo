package br.com.alura.clientelo.web.controller.errors;

public class ProdutoCreationErrorDto {
    private String error;

    public ProdutoCreationErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
