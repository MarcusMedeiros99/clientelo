package br.com.alura.clientelo.web.controller.produto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class AllProdutosDto {
    List<ProdutoResumeDto> produtos;
    Integer currentPage;
    Integer totalPages;
    Integer pageSize;

    @JsonCreator
    public AllProdutosDto(
            @JsonProperty("produtos") List<ProdutoResumeDto> produtos,
            @JsonProperty("currentPage") Integer currentPage,
            @JsonProperty("totalPages") Integer totalPages,
            @JsonProperty("pageSize") Integer pageSize) {
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
