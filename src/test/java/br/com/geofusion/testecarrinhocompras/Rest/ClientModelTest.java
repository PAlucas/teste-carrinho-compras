package br.com.geofusion.testecarrinhocompras.Rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;

@SpringBootTest
public class ClientModelTest {
    
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testCliente(){
        ClientModel clientModel = new ClientModel();
        clientModel.setClientId((long)1);
        clientModel.setNome("Lucas");
        clientRepository.save(clientModel);
        assertNotNull(clientRepository.findById((long)1).get());
    }
}
