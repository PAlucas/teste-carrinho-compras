package br.com.geofusion.testecarrinhocompras.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ItemDto {
    @NotBlank
    @Size(max = 280)
    private String code;
    @NotBlank
    @Size(max = 10)
    private String quantity;
    @NotBlank
    @Size(max = 100)
    private String unitPrice;

    public String getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
}
