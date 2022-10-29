package br.com.geofusion.testecarrinhocompras.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class ItemTest {
    /**
     * Teste para ver se o item vai ter como preço total
     * quantidade * preço unitário
     */
    @Test
    public void testProdutosPrecoQuantidadePreco(){
        long testId = 12;
        Product produto1 = new Product(testId, "Produto Teste");
        BigDecimal bigDecimal = new BigDecimal("10.52");
        Item itemCriado = new Item(produto1, bigDecimal, 5);

        BigDecimal total = new BigDecimal("52.60");
        assertEquals(total, itemCriado.getAmount());
    }
}
