package br.com.alura.clientelo.controller.dto.error;

public class CategoriaCreationErrorDto {
    public String erro;

    public CategoriaCreationErrorDto(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }
}
