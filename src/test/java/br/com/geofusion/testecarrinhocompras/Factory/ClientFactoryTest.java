package br.com.geofusion.testecarrinhocompras.Factory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.classes.Client;
import br.com.geofusion.testecarrinhocompras.controllers.ClientControllerFactory;
import br.com.geofusion.testecarrinhocompras.dto.ClientDto;
import br.com.geofusion.testecarrinhocompras.services.ClientService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ClientFactoryTest {

    @Autowired
    private MockMvc mockMvc;
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    /**
     * Teste para ver que quando feito um posto no endpoint /Cliente com 
     * as informações corretas o status vai ser correto
     * @throws Exception
     */
    @Test
    public void retornaPostStatusCorreto() throws Exception{
        ClientDto clientDto = new ClientDto();
        ClientModel clientRequest = new ClientModel();
        clientDto.setNome("Ana");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(clientDto);
        this.mockMvc.perform(post("/Cliente").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated());
    }

    /**
     * Teste para ver que quando feito um posto no endpoint /Cliente com 
     * as informações corretas o status vai ser correto e vai retornar 
     * as informações corretas 
     * @throws Exception
     */
    @Test
    public void retornaPostClient() throws Exception{
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
}
