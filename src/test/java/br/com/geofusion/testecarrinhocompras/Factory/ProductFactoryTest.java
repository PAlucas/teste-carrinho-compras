package br.com.geofusion.testecarrinhocompras.Factory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import br.com.geofusion.testecarrinhocompras.dto.ProductDto;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProductFactoryTest {
    @Autowired
    private MockMvc mockMvc;
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    /**
     * Teste para ver que quando feito um post no endpoint /Produto com 
     * as informações corretas o status vai ser correto e vai retornar 
     * as informações corretas 
     * @throws Exception
     */
    @Test
    public void testPostProduto() throws Exception{
        ProductDto productDto = new ProductDto();
        productDto.setDescription("Produto Teste");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(productDto);
        this.mockMvc.perform(post("/Produto").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated());
    }

    /**
     * Teste para ver se o get vai ter o status correto
     * @throws Exception
     */
    @Test
    public void retornaGetClient() throws Exception{
        this.mockMvc.perform(get("/Produto"))
        .andExpect(status().isOk());
    }
}
