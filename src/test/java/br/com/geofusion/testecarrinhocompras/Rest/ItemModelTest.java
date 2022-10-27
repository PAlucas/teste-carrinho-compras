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
        long testId = 10; 

        ShoppingCartModel shop = new ShoppingCartModel();
        shop.setShopId(testId);

        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(testId);
        itemModel.setIdShop(shop);
        itemModel.setQuantity(2);

        shoppingCartTest.save(shop);
        itemRepository.save(itemModel);
        assertNotNull(itemRepository.findById(testId).get());
    }

    @Test
    public void testItemModelError(){
        long testId = 10; 
        ShoppingCartModel shop = new ShoppingCartModel();
        ItemModel itemModel = new ItemModel();
        itemModel.setId(testId);
        itemModel.setCodeProduto(testId);
        itemModel.setIdShop(shop);
        assertThrows(RuntimeException.class, () -> itemRepository.save(itemModel));
    }
}
