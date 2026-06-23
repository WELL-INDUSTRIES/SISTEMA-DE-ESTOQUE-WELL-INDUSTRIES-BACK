package com.avaliacao.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MercadoriaQuantidadeDTO {

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidade;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
