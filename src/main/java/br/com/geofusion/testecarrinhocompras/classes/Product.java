package br.com.geofusion.testecarrinhocompras.classes;

/**
 * Classe que representa um produto que pode ser adicionado
 * como item ao carrinho de compras.
 *
 * Importante: Dois produtos são considerados iguais quando ambos possuem o
 * mesmo código.
 */
public class Product {
    private Long code;
    private String description;

    /**
     * Construtor da classe Produto.
     *
     * @param code
     * @param description
     */
    public Product(Long code, String description) {

    }

    /**
     * Retorna o código da produto.
     *
     * @return Long
     */
    public Long getCode() {
        return this.code;
    }

    /**
     * Retorna a descrição do produto.
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }
}
