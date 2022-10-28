package br.com.geofusion.testecarrinhocompras.Rest;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemModelTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ShoppingCartRepository shoppingCartTest;

    @Test
    public void testItemModel(){
        long testId = 1L; 

        ShoppingCartModel shop = new ShoppingCartModel();
        ShoppingCartModel shopAux = new ShoppingCartModel();
        shop.setShopId(testId);
        shopAux = shoppingCartTest.save(shop);
        
        ItemModel itemModel = new ItemModel();
        ItemModel itemModelAux = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(testId);
        itemModel.setIdShop(shopAux);
        itemModel.setQuantity(2);

        
        itemModelAux = itemRepository.save(itemModel);
        assertNotNull(itemRepository.findById(itemModelAux.getId()).get());
    }

    @Test
    public void testItemModelError(){
        long testId = 1L; 
        ShoppingCartModel shop = new ShoppingCartModel();
        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(testId);
        itemModel.setIdShop(shop);
        assertThrows(RuntimeException.class, () -> itemRepository.save(itemModel));
    }
}
