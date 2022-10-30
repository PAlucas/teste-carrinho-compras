package br.com.geofusion.testecarrinhocompras.classes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa um item no carrinho de compras.
 */
public class Item {
    private Product product;
    private BigDecimal unitPrice;
    private int quantity;
    /**
     * Construtor da classe Item.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public Item(Product product, BigDecimal unitPrice, int quantity) {
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * Retorna o produto.
     *
     * @return Produto
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Retorna o valor unitário do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * adiciona novo valor para o preço do produto
     *
     */
    public void setUnitPrice(BigDecimal bigDecimal) {
        this.unitPrice = bigDecimal;
    }


    /**
     * Retorna a quantidade dos item.
     *
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retorna a quantidade dos item.
     *
     * @return int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retorna o valor total do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        BigDecimal total = BigDecimal.valueOf(this.quantity).multiply(this.unitPrice);
        return total;
    }

    /**
     * Método para criar um map retorna a chave e a descrição do produto,
     * a chave e preco unitário do produto, a chave e a quantidade final do produto
     * e a chave e preço total do produto
     * @param nome
     */
    public Map<String, String> json() {
        HashMap<String, String> json = new HashMap<>();
        json.put("Produto", this.product.getDescription());
        json.put("Preco Unidade", getUnitPrice().toString());
        json.put("Quantidade", Integer.toString(getQuantity()));
        json.put("Preco Total", getAmount().toString());
        return json;
    }
}
