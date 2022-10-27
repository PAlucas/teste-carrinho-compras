package br.com.geofusion.testecarrinhocompras.Rest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
@SpringBootTest
public class ProducModelTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testCliente(){
        long testId = 10; 
        ProductModel productModel = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Lucas");
        productRepository.save(productModel);
        assertNotNull(productRepository.findById(testId).get());
    }
}
