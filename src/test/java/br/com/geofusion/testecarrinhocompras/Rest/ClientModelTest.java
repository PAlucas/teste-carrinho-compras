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

    /**
     * Teste para ver se quando colocado o id correto o cliente e o nome o cliente
     * vai ser criado
     */
    @Test
    public void testCliente(){
        long testId = 1L; 
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);
        assertNotNull(clientRepository.findById(clientModelAux.getClientId()).get());
    }

}
