package br.com.alura.clientelo.web.controller.errors;

public class ProdutoEditErrorDto {
    private String error;

    public ProdutoEditErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
