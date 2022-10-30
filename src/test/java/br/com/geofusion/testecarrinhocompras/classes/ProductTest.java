package br.com.geofusion.testecarrinhocompras.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {
    /**
     * Teste para ver se quando colocado a descricao do
     * produto ele vai retornar um map com a chave e o 
     * input correto
     */
    @Test
    public void testJsonProduto(){
        long test_id = 10;
        Product product = new Product(test_id, "Produto Teste");

        String stringFinal = "{Descricao=Produto Teste}";

        assertEquals(stringFinal, product.json().toString());
    }
}
