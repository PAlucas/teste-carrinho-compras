package br.com.geofusion.testecarrinhocompras.classes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class ShoppingCart {
    private List<Item> items = new ArrayList<Item>();;

    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public void addItem(Product product, BigDecimal unitPrice, int quantity) {
        init(product, unitPrice, quantity);
    }
    
    /**
     * Método incializador privado.
     */
    private void init(Product product, BigDecimal unitPrice, int quantity){
        if(!mudaSeExisteProduto(product, unitPrice, quantity)){
            this.items.add(new Item(product, unitPrice, quantity));
        }
    }

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param product
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removeItem(Product product) {
        boolean foiRemovido = false;
        List<Item> itemComProduto = this.items.stream().filter((Item item) ->{
            return item.getProduct().equals(product);    
        }).collect(Collectors.toList());
        for (Item itemDelete : itemComProduto) {
            if(this.items.contains(itemDelete)){
                this.items.remove(itemDelete);
                foiRemovido = true;
            }
        }

        return foiRemovido;
    }

    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na
     * coleção, em que zero representa o primeiro item.
     *
     * @param itemIndex
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removeItem(int itemIndex) {
        int tamanho = this.items.size();
        if(tamanho>itemIndex){
            this.items.remove(itemIndex);
            return true;
        }
        return false;
    }

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        BigDecimal total = new BigDecimal(0);
        for (Item item : items) {
            total = total.add(item.getAmount());          
        }
        return total;
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return items
     */
    public Collection<Item> getItems() {
        return this.items;
    }

    private boolean mudaSeExisteProduto(Product product, BigDecimal unitPrice, int quantity){
        if(this.items != null){
            for (Item item : this.items) {
                if(item.getProduct().getCode().equals(product.getCode())){
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setUnitPrice(unitPrice);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Método para certificar que não possui nenhum item na lista
     * @return boolean
     */
    public boolean semItens(){
        if(this.items.size() == 0){
            return true;
        }
        return false;
    }
}
