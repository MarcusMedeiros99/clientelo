package br.com.alura.clientelo.controller.dto;

public class ProdutoCreationErrorDto {
    private String error;

    public ProdutoCreationErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
