package br.com.geofusion.testecarrinhocompras.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientDto {
    @NotBlank
    @Size(max = 280)
    private String nome;
    @NotBlank
    @Size(max = 280)
    private String cartaoCredito;
    private String status;
    @NotBlank
    @Size(max = 280)
    private String limiteCartao;
    @NotBlank
    @Size(max = 280)
    private String data;
}
