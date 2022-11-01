package br.com.geofusion.testecarrinhocompras.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShoppingCartTest {
    /**
     * Teste para ver se vai somar quando existe dois itens com o mesmo produto
     */
    @Test
    public void testSomaProdutosQuantidade(){
        long testId = 12;

        Product produto1 = new Product(testId, "Produto Teste");
        BigDecimal bigDecimal = new BigDecimal("10.52");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(produto1, bigDecimal, 5);
        shoppingCart.addItem(produto1, bigDecimal, 3);

        Collection<Item> itemAux = shoppingCart.getItems();
        Object[] itens = itemAux.toArray();
        Item itemFinal = (Item)itens[0];

        assertEquals(8, itemFinal.getQuantity());
    }

    /**
     * Teste para ver se vai ficar com o último preço quando são
     * dois itens com o mesmo produto
     */
    @Test
    public void testSomaProdutosPreco(){
        long testId = 12;

        Product produto1 = new Product(testId, "Produto Teste");
        BigDecimal bigDecimal = new BigDecimal("10.52");
        BigDecimal bigDecimal2 = new BigDecimal("101.52");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(produto1, bigDecimal, 5);
        shoppingCart.addItem(produto1, bigDecimal2, 3);

        Collection<Item> itemAux = shoppingCart.getItems();
        Object[] itens = itemAux.toArray();
        Item itemFinal = (Item)itens[0];
        
        assertEquals(bigDecimal2, itemFinal.getUnitPrice());
    }

    /**
     * Teste para ver se quando o carrinho está sem itens
     * o método semItems vai retornar true
     */
    @Test
    public void testSemItems(){
        ShoppingCart shoppingCart = new ShoppingCart();
        
        assertEquals(true, shoppingCart.semItens());
    }

    /**
     * Teste para ver se quando o carrinho está com itens
     * o método removeItem recebendo um produto vai
     * retirar item com esse produto
     */
    @Test
    public void testRemoveItemProduto(){
        long testId = 12;
        BigDecimal bigDecimal = new BigDecimal("10.52");
        ShoppingCart shoppingCart = new ShoppingCart();
        Product produto = new Product(testId, "Produto Teste");
        shoppingCart.addItem(produto, bigDecimal, 3);

        
        assertEquals(true, shoppingCart.removeItem(produto));
    }

    /**
     * Teste para ver se quando o carrinho está com itens
     * o método removeItem recebendo um inteiro 
     * vai retirar o item nessa posição
     */
    @Test
    public void testRemoveItemInt(){
        long testId = 12;
        BigDecimal bigDecimal = new BigDecimal("10.52");
        ShoppingCart shoppingCart = new ShoppingCart();
        Product produto = new Product(testId, "Produto Teste");
        shoppingCart.addItem(produto, bigDecimal, 3);

        
        assertEquals(true, shoppingCart.removeItem(0));
    }
}
