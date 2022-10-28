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
    /**
     * Teste para ver se está sendo inserido o carrinho no banco de dados
     */
    @Test
    public void testCliente(){
        long testId = 1L; 
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        ShoppingCartModel shoppingCartModelAux = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModelAux = shoppingCartRepository.save(shoppingCartModel);
        assertNotNull(shoppingCartRepository.findById(shoppingCartModelAux.getShopId()).get());
    }
}
