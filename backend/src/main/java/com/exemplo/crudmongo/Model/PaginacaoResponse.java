package com.exemplo.crudmongo.Model;

import java.util.List;

/**
 * DTO simples para retorno de paginação com metadados.
 */
public class PaginacaoResponse {

    private List<Pessoa> pessoas;
    private int totalPages;
    private long totalElements;
    private int pageNumber; // 1-based para o cliente
    private int pageSize;

    public PaginacaoResponse(List<Pessoa> pessoas, int totalPages, long totalElements, int pageNumber, int pageSize) {
        this.pessoas = pessoas;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}