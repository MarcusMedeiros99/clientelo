package br.com.alura.clientelo.controller.dto;

public class ErrorDto {
    private String error;

    public ErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
