package br.com.geofusion.testecarrinhocompras.Rest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShoppingCardModelTest {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ClientRepository clientRepository;
    /**
     * Teste para ver se quando possui o cliente passado
     * está sendo inserindo o carrinho no banco de dados
     */
    @Test
    public void testShopping(){
        long testId = 1L; 
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        ShoppingCartModel shoppingCartModelAux = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModelAux);
        shoppingCartModelAux = shoppingCartRepository.save(shoppingCartModel);
        
        assertNotNull(shoppingCartRepository.findById(shoppingCartModelAux.getShopId()).get());
    }
    /**
     * Teste para ver se quando não possui o cliente passado 
     * não está sendo inserindo o carrinho no banco de dados
     */
    @Test
    public void testErroShopping(){
        long testId = 1L; 
        ClientModel clientModel = new ClientModel();

        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModel);
        
        assertThrows(RuntimeException.class, () ->shoppingCartRepository.save(shoppingCartModel));
    }

}
