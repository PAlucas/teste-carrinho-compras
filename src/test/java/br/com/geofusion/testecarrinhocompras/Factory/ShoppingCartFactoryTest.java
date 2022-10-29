package br.com.geofusion.testecarrinhocompras.Factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ShoppingCartFactoryTest {
    @Autowired
    private MockMvc mockMvc;
    
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
}
