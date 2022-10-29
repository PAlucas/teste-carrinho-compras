package br.com.geofusion.testecarrinhocompras.Factory;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.dto.ClientDto;
import br.com.geofusion.testecarrinhocompras.services.ClientService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ClientFactoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    /**
     * Teste para ver que quando feito um posto no endpoint /Cliente com 
     * as informações corretas o status vai ser correto
     * @throws Exception
     */
    @Test
    public void testPostStatusCorreto() throws Exception{
        ClientDto clientDto = new ClientDto();
        clientDto.setNome("Ana");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(clientDto);
        this.mockMvc.perform(post("/Cliente").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated());
    }

    /**
     * Teste para ver que quando feito um post no endpoint /Cliente com 
     * as informações corretas o status vai ser correto e vai retornar 
     * as informações corretas 
     * @throws Exception
     */
    @Test
    public void testPostClient() throws Exception{
        ClientDto clientDto = new ClientDto();
        clientDto.setNome("Lucas");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(clientDto);
        char aspas = '"';
        String stringFinal = "{"+aspas+"Nome"+aspas+":"+aspas+"Lucas"+aspas+"}";
        this.mockMvc.perform(post("/Cliente").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated()).andExpect(content().string(stringFinal));
    }
    
    /**
     * Teste para ver se o get vai ter o status correto
     * @throws Exception
     */
    @Test
    public void retornaGetClient() throws Exception{
        this.mockMvc.perform(get("/Cliente"))
        .andExpect(status().isOk());
    }

    /**
     * Teste para ver que quando feito um patch no endpoint /Cliente com 
     * as informações corretas o status vai ser correto e vai retornar 
     * as informações corretas 
     * @throws Exception
     */
    @Test
    public void testPatchClient() throws Exception{

        long testId = 1L; 
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas Teste");
        clientModelAux = clientService.save(clientModel);
        
        ClientDto clientDto = new ClientDto();
        clientDto.setNome("Lucas Novo");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(clientDto);

        char aspas = '"';
        String stringFinal = "{"+aspas+"Novo nome"+aspas+":"+aspas+"Lucas Novo"+aspas+"}";

        this.mockMvc.perform(patch("/Cliente/"+clientModelAux.getClientId()).contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated()).andExpect(content().string(stringFinal));
    }

    /**
     * Teste para ver que quando feito um patch no endpoint /Cliente sem 
     * as informações corretas o status vai ser de não modificado
     * @throws Exception
     */
    @Test
    public void testErroPatchClient() throws Exception{
        
        ClientDto clientDto = new ClientDto();
        clientDto.setNome("Lucas Novo");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(clientDto);

        this.mockMvc.perform(patch("/Cliente/0").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isNotModified());
    }

    /**
     * Teste para ver que quando feito um delete no endpoint /Cliente com 
     * as informações corretas o status vai ser correto e vai retornar 
     * o cliente que foi deletado
     * @throws Exception
     */
    @Test
    public void testDeleteClient() throws Exception{

        long testId = 1L; 
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas Teste");
        clientModelAux = clientService.save(clientModel);
        

        char aspas = '"';
        String stringFinal = "{"+aspas+"Cliente Deletado"+aspas+":"+aspas+"Lucas Teste"+aspas+"}";

        this.mockMvc.perform(delete("/Cliente/"+clientModelAux.getClientId()))
        .andExpect(status().isAccepted()).andExpect(content().string(stringFinal));
    }

    /**
     * Teste para ver que quando feito um delete no endpoint /Cliente sem
     * as informações corretas o status vai ser de não tem conteúdo
     * @throws Exception
     */
    @Test
    public void testDeleteErroClient() throws Exception{

        this.mockMvc.perform(delete("/Cliente/0"))
        .andExpect(status().isNoContent());
    }
}
