package br.com.alura.clientelo.web.controller.cliente;

import java.util.List;

public class ClienteListDto {
    List<ClienteOnListDto> clientes;
    Integer currentPage;
    Integer totalPages;
    Integer pageSize;

    public ClienteListDto(List<ClienteOnListDto> clientes, Integer currentPage, Integer totalPages, Integer pageSize) {
        this.clientes = clientes;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public List<ClienteOnListDto> getClientes() {
        return clientes;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
