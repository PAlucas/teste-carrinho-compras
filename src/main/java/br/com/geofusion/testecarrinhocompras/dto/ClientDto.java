package br.com.geofusion.testecarrinhocompras.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientDto {
    @NotBlank
    @Size(max = 280)
    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
