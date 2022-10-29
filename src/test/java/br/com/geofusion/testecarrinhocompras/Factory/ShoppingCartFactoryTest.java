package br.com.geofusion.testecarrinhocompras.Factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ShoppingCartFactoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Teste para ver que quando feito um post no endpoint /Carrinho com 
     * o idincorreto o status vai ser conflito e vai retornar não existe esse id
     * @throws Exception
     */
    @Test
    public void testPostCarrinhoClienteIdIncorreto() throws Exception{
        
        this.mockMvc.perform(post("/Carrinho?idCliente=0"))
        .andExpect(status().isConflict()).andExpect(content().string("Não existe Cliente com esse Id"));
    }

    /**
     * Teste para ver que quando feito um post no endpoint /Carrinho com 
     * o id correto e o cliente já possui carrinho, isso vai retornar 
     * "carrinho criado" e o status criado
     * @throws Exception
     */
    @Test
    public void testPostCarrinhoClienteIdCorretoNaoExiste() throws Exception{
        long testId = 1L; 
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);
        this.mockMvc.perform(post("/Carrinho?idCliente="+clientModelAux.getClientId()))
        .andExpect(status().isCreated()).andExpect(content().string("Carrinho criado"));
    }
}
