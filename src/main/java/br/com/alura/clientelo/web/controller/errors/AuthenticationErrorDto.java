package br.com.alura.clientelo.web.controller.errors;

public class AuthenticationErrorDto {
    public String erro;

    public AuthenticationErrorDto(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }
}
