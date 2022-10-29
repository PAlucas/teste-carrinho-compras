package br.com.geofusion.testecarrinhocompras.Rest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
@SpringBootTest
public class ProductModelTest {
    @Autowired
    ProductRepository productRepository;
    /**
     * Teste para ver se quando colocado id e descrição
     * está sendo inserido o item no banco de dados
     */
    @Test
    public void testCliente(){
        long testId = 1L; 
        ProductModel productModel = new ProductModel();
        ProductModel productModelAux = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Lucas");
        productModelAux = productRepository.save(productModel);
        assertNotNull(productRepository.findById(productModelAux.getCode()).get());
    }
}
