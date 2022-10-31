package br.com.geofusion.testecarrinhocompras.Factory;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;
import br.com.geofusion.testecarrinhocompras.dto.ItemDto;
import br.com.geofusion.testecarrinhocompras.services.ItemService;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ItemFactoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    /**
     * Teste para ver que quando feito um post no endpoint /Item
     * tendo o id do carrinho correto, o code do produto correto,
     * uma valor unitário e a quantidade vai ser criado um
     * item
     * @throws Exception
     */
    @Test
    public void testCriaItem() throws Exception{
        long testId = 1L;

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shoppingCartModelAux = new ShoppingCartModel();
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModelAux);
        shoppingCartModelAux = shoppingCartRepository.save(shoppingCartModel);

        ProductModel productModel = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto teste");
        productRepository.save(productModel);
        

        ItemDto itemDto = new ItemDto();
        itemDto.setCode(Long.toString(productModel.getCode()));
        itemDto.setQuantity("9");
        itemDto.setUnitPrice("10.90");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(itemDto);

        char aspas = '"';
        String stringFinal = "{"+aspas+"Preco Total"+aspas+":"+aspas+"98.10"+aspas+","+aspas+"Preco Unidade"+aspas+":"+aspas+"10.90"+aspas+","+aspas+"Produto"+aspas+":"+aspas+"Produto teste"+aspas+","+aspas+"Quantidade"+aspas+":"+aspas+"9"+aspas+"}";

        this.mockMvc.perform(post("/Item?idCart="+shoppingCartModelAux.getShopId()).contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isCreated()).andExpect(content().string(stringFinal));
    }

    /**
     * Teste para ver que quando feito um post no endpoint /Item
     * tendo o id do carrinho incorreto, o code do produto correto,
     * uma valor unitário e a quantidade não vai ser criado um
     * item e vai retornar um status conflito
     * @throws Exception
     */
    @Test
    public void testCriaItemSemCarrinho() throws Exception{
        long testId = 1L;

        ProductModel productModel = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto teste");
        productRepository.save(productModel);
        

        ItemDto itemDto = new ItemDto();
        itemDto.setCode(Long.toString(productModel.getCode()));
        itemDto.setQuantity("9");
        itemDto.setUnitPrice("10.90");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(itemDto);

        this.mockMvc.perform(post("/Item?idCart=0").contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isConflict()).andExpect(content().string("Não existe carrinho com esse Id"));
    }

    /**
     * Teste para ver que quando feito um post no endpoint /Item
     * tendo o id do carrinho correto, o code do produto incorreto,
     * uma valor unitário e a quantidade não vai ser criado um
     * item e vai retornar um status conflito
     * @throws Exception
     */
    @Test
    public void testCriaItemSemProduto() throws Exception{
        long testId = 1L;
        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shoppingCartModelAux = new ShoppingCartModel();
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModelAux);
        shoppingCartModelAux = shoppingCartRepository.save(shoppingCartModel);

        ItemDto itemDto = new ItemDto();
        itemDto.setCode("0");
        itemDto.setQuantity("9");
        itemDto.setUnitPrice("10.90");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(itemDto);

        this.mockMvc.perform(post("/Item?idCart="+shoppingCartModelAux.getShopId()).contentType(APPLICATION_JSON_UTF8).content(requestJson))
        .andExpect(status().isConflict()).andExpect(content().string("Não existe produto com esse código"));
    }

    /**
     * Teste para ver que quando feito um delete no endpoint /Item
     * tendo o id do carrinho correto e o id correto
     * todos os itens no carrinho que tem o 
     * produto igual ao item que teve seu id passado
     * vão ser apagados
     * @throws Exception
     */
    @Test
    public void testDeleteItem() throws Exception{
        long testId = 1L;

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shoppingCartModelAux = new ShoppingCartModel();
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModelAux);
        shoppingCartModelAux = shoppingCartRepository.save(shoppingCartModel);

        ProductModel productModel = new ProductModel();
        ProductModel productModelAux = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto teste");
        productModelAux = productRepository.save(productModel);
        

        ItemModel itemModel = new ItemModel();
        ItemModel itemModelAux = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModelAux);
        itemModel.setIdShop(shoppingCartModelAux);
        itemModel.setQuantity(9);
        BigDecimal valor = new BigDecimal("10.90");
        itemModel.setUnitPrice(valor);
        itemModelAux = itemRepository.save(itemModel);

        char aspas = '"';
        String stringFinal = "{"+aspas+"Preco Total"+aspas+":"+aspas+"98.1"+aspas+","+aspas+"Preco Unidade"+aspas+":"+aspas+"10.9"+aspas+","+aspas+"Produto"+aspas+":"+aspas+"Produto teste"+aspas+","+aspas+"Quantidade"+aspas+":"+aspas+"9"+aspas+"}";

        this.mockMvc.perform(delete("/Item/"+itemModelAux.getId()+"?idCart="+shoppingCartModelAux.getShopId()))
        .andExpect(status().isAccepted()).andExpect(content().string(stringFinal));
    }
}
