package br.com.geofusion.testecarrinhocompras.Factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ShoppingCartFactoryTest {
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
     * o id correto e o cliente não possui carrinho, isso vai retornar 
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

    /**
     * Teste para ver que quando feito um post no endpoint /Carrinho com 
     * o id correto e o cliente já possui carrinho, isso vai retornar 
     * status aceito
     * @throws Exception
     */
    @Test
    public void testPostCarrinhoClienteIdCorretoExiste() throws Exception{
        long testId = 1L;

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        shoppingCartModel.setShopId(testId);
        shoppingCartModel.setIdClient(clientModelAux);
        shoppingCartRepository.save(shoppingCartModel);

        this.mockMvc.perform(post("/Carrinho?idCliente="+clientModelAux.getClientId()))
        .andExpect(status().isAccepted());
    }

    /**
     * Teste para ver que quando feito um get no endpoint /Carrinho/Total 
     * vai ser retornado o status accept
     * @throws Exception
     */
    @Test
    public void testGetCarrinhoTotal() throws Exception{
        this.mockMvc.perform(get("/Carrinho/Total"))
        .andExpect(status().isAccepted());
    }

    /**
     * Teste para ver que quando feito um delete no endpoint /Carrinho/Total
     * tendo o id do cliente correto e o cliente tendo carrinho
     * o carrinho vai ser deletado e se o carrinho tiver itens 
     * os itens do carrinho vão ser deletados 
     * vai ser retornado o status accept
     * @throws Exception
     */
    @Test
    public void testDeleteCarrinho() throws Exception{
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

        ProductModel productModelAux = new ProductModel();
        ProductModel productModel = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto teste");
        productModelAux = productRepository.save(productModel);
        

        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModelAux);
        itemModel.setIdShop(shoppingCartModelAux);
        BigDecimal bigDecimal = new BigDecimal("10.52");
        itemModel.setUnitPrice(bigDecimal);
        itemRepository.save(itemModel);
        
        this.mockMvc.perform(delete("/Carrinho?idCliente="+clientModelAux.getClientId()))
        .andExpect(status().isAccepted());
    }

    /**
     * Teste para ver que quando feito um delete no endpoint /Carrinho/Total
     * tendo o id do cliente correto e o cliente não tendo carrinho
     * o status vai retornar conflito e nava vai acontecer
     * @throws Exception
     */
    @Test
    public void testDeleteCarrinhoSemCarrinho() throws Exception{
        long testId = 1L;

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Lucas");
        clientModelAux = clientRepository.save(clientModel);
        
        this.mockMvc.perform(delete("/Carrinho?idCliente="+clientModelAux.getClientId()))
        .andExpect(status().isConflict());
    }

    /**
     * Teste para ver que quando feito um get no endpoint /Carrinho/{id_client}
     * tendo o id do cliente correto e o cliente tendo carrinho com itens
     * os itens do cliente vão ser mostrados
     * @throws Exception
     */
    @Test
    public void testMostraItensCarrinho() throws Exception{
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

        ProductModel productModelAux = new ProductModel();
        ProductModel productModel = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto teste");
        productModelAux = productRepository.save(productModel);
        

        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModelAux);
        itemModel.setIdShop(shoppingCartModelAux);
        BigDecimal bigDecimal = new BigDecimal("10.52");
        itemModel.setUnitPrice(bigDecimal);
        itemRepository.save(itemModel);
        
        this.mockMvc.perform(get("/Carrinho/"+clientModelAux.getClientId()))
        .andExpect(status().isOk());
    }
}
