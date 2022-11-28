package br.com.alura.clientelo.controller.dto;

import java.util.List;

public class PedidosListDto {
    List<PedidoOnListDto> pedidos;
    Integer currentPage;
    Integer totalPages;
    Integer pageSize;

    public PedidosListDto(List<PedidoOnListDto> pedidos, Integer currentPage, Integer totalPages, Integer pageSize) {
        this.pedidos = pedidos;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public List<PedidoOnListDto> getPedidos() {
        return pedidos;
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
