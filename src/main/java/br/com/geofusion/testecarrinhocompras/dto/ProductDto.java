package br.com.geofusion.testecarrinhocompras.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductDto {
    @NotBlank
    @Size(max = 280)
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String nome) {
        this.description = nome;
    }

}