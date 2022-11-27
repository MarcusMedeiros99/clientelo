package br.com.alura.clientelo.controller.dto;

import java.util.Collections;
import java.util.List;

public class AllProdutosDto {
    List<ProdutoResumeDto> produtos;
    Integer currentPage;
    Integer totalPages;
    Integer pageSize;

    public AllProdutosDto(List<ProdutoResumeDto> produtos, Integer currentPage, Integer totalPages, Integer pageSize) {
        this.produtos = produtos;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public List<ProdutoResumeDto> getProdutos() {
        return Collections.unmodifiableList(produtos);
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
