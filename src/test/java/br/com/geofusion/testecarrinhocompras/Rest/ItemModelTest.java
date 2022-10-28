package br.com.geofusion.testecarrinhocompras.Rest;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ProductRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemModelTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;

    /**
     * Teste para ver se quando colocado o id correto de um carrinho e o code 
     * correto de um produto é possível criar um item
     */
    @Test
    public void testItemModel(){
        long testId = 1L; 

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Cliente teste");
        clientModelAux = clientRepository.save(clientModel);

        ShoppingCartModel shop = new ShoppingCartModel();
        ShoppingCartModel shopAux = new ShoppingCartModel();
        shop.setShopId(testId);
        shop.setIdClient(clientModelAux);
        shopAux = shoppingCartRepository.save(shop);

        ProductModel productModel = new ProductModel();
        ProductModel productModelTest = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto Teste");
        productModelTest = productRepository.save(productModel);
        
        ItemModel itemModel = new ItemModel();
        ItemModel itemModelAux = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModelTest);
        itemModel.setIdShop(shopAux);
        itemModel.setQuantity(2);

        
        itemModelAux = itemRepository.save(itemModel);
        assertNotNull(itemRepository.findById(itemModelAux.getId()).get());
    }

    /**
     * Teste para ver se quando não o id correto de um carrinho e o code 
     * correto de um produto não é possível criar um item
     */
    @Test
    public void testItemModelError(){
        long testId = 1L; 
        ShoppingCartModel shop = new ShoppingCartModel();

        ProductModel productModel = new ProductModel();
        ProductModel productModelAux = new ProductModel();
        productModel.setCode(testId);
        productModel.setDescription("Produto Teste");
        productModelAux =  productRepository.save(productModel);

        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModelAux);
        itemModel.setIdShop(shop);
        assertThrows(RuntimeException.class, () -> itemRepository.save(itemModel));
    }

    /**
     * Teste para ver se quando não o id correto de um carrinho e o code 
     * correto de um produto não é possível criar um item
     */
    @Test
    public void testProdutoModelError(){
        long testId = 1L; 

        ClientModel clientModel = new ClientModel();
        ClientModel clientModelAux = new ClientModel();
        clientModel.setClientId(testId);
        clientModel.setNome("Cliente teste");
        clientModelAux = clientRepository.save(clientModel);
        
        ShoppingCartModel shop = new ShoppingCartModel();
        ShoppingCartModel shopAux = new ShoppingCartModel();
        shop.setShopId(testId);
        shop.setIdClient(clientModelAux);
        shopAux = shoppingCartRepository.save(shop);

        ProductModel productModel = new ProductModel();

        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(productModel);
        itemModel.setIdShop(shopAux);
        assertThrows(RuntimeException.class, () -> itemRepository.save(itemModel));
    }
}
