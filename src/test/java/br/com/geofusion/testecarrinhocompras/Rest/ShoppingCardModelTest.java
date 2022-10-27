package br.com.geofusion.testecarrinhocompras.Rest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;
@SpringBootTest
public class ShoppingCardModelTest {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Test
    public void testCliente(){
        long testId = 10; 
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartRepository.save(shoppingCartModel);
        assertNotNull(shoppingCartRepository.findById(testId).get());
    }
}
