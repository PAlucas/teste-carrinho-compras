package br.com.geofusion.testecarrinhocompras.classes;

import java.math.BigDecimal;

/**
 * Classe que representa um item no carrinho de compras.
 */
public class Item {

    /**
     * Construtor da classe Item.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public Item(Product product, BigDecimal unitPrice, int quantity) {
    }

    /**
     * Retorna o produto.
     *
     * @return Produto
     */
    public Product getProduct() {
        return null;
    }

    /**
     * Retorna o valor unitário do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getUnitPrice() {
        return null;
    }

    /**
     * Retorna a quantidade dos item.
     *
     * @return int
     */
    public int getQuantity() {
        return 0;
    }

    /**
     * Retorna o valor total do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        return null;
    }
}
