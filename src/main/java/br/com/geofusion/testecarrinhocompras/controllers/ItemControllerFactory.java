package br.com.geofusion.testecarrinhocompras.controllers;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.classes.Item;
import br.com.geofusion.testecarrinhocompras.classes.Product;
import br.com.geofusion.testecarrinhocompras.classes.ShoppingCart;
import br.com.geofusion.testecarrinhocompras.dto.ItemDto;
import br.com.geofusion.testecarrinhocompras.services.ItemService;
import br.com.geofusion.testecarrinhocompras.services.ProductService;
import br.com.geofusion.testecarrinhocompras.services.ShoppingCartService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Item")
public class ItemControllerFactory {
    final ItemService itemService;
    final ShoppingCartService shoppingCartService;     
    public ItemControllerFactory(ShoppingCartService clientService, ItemService itemService){
        this.shoppingCartService = clientService;
        this.itemService = itemService;
    }

    @Autowired
    private ProductService productService;

    /**
     * Cria e retorna um novo Item
     *
     * @return Item
     */
    @PostMapping
    public ResponseEntity<Object> saveItem(@RequestBody @Valid ItemDto itemDto, @RequestParam String idCart) throws ParseException {
        long productCode = Long.parseLong(itemDto.getCode());
        Optional<ProductModel> productModel = productService.findByCode(productCode);
        if(!productModel.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe produto com esse código");
        }
        long idShoppingCart = Long.parseLong(idCart);
        Optional<ShoppingCartModel> shoppingCartModel = shoppingCartService.findById(idShoppingCart);
        if(!shoppingCartModel.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe carrinho com esse Id");
        }
        int quantidadeItens = Integer.parseInt(itemDto.getQuantity());
        ItemModel itemModel = new ItemModel();
        itemModel.setCodeProduto(productModel.get());
        itemModel.setIdShop(shoppingCartModel.get());
        itemModel.setQuantity(quantidadeItens);
        BigDecimal valorUnitarioItem = new BigDecimal(itemDto.getUnitPrice());
        itemModel.setUnitPrice(valorUnitarioItem);
        itemService.save(itemModel);
        
        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModel.get());
        for (ItemModel itemModelTrocarPreco : itemModelList) {
            if(itemModelTrocarPreco.getCodeProduto().getCode() == productModel.get().getCode()){
                itemModelTrocarPreco.setUnitPrice(valorUnitarioItem);
                itemService.save(itemModelTrocarPreco);
            };
        }

        Product productFinal = new Product(productModel.get().getCode(), productModel.get().getDescription());
        Item itemFinal = new Item(productFinal, valorUnitarioItem, quantidadeItens);
        
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemFinal.json());
    }

    /**
     * Deleta item
     *
     * @return item deletado
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> DeleteItem(@RequestParam String idCart, @PathVariable(value = "itemId") long itemId) throws ParseException {
        long shoppingCartId = Long.parseLong(idCart);
        Optional<ShoppingCartModel> shoppingCartModel = shoppingCartService.findById(shoppingCartId);
        if(!shoppingCartModel.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe carrinho com esse Id");
        }
        Optional<ItemModel> itemModelOptional = itemService.findById(itemId);
        if(!itemModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe item com esse Id no carrinho");
        }
        if(itemModelOptional.get().getIdShop().getShopId() != shoppingCartId){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe item com esse Id no carrinho");
        }
        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModel.get());

        Optional<ItemModel> itemModelAux = itemService.findById(itemId);
        List<ItemModel> itemModelListComId = itemModelList.stream().filter((ItemModel item) ->{
            return item.getCodeProduto().equals(itemModelAux.get().getCodeProduto());
        }).collect(Collectors.toList());

        ShoppingCart shoppingCartPegarItens = new ShoppingCart();
        Product newProduct = new Product(itemModelListComId.get(0).getCodeProduto().getCode(), itemModelListComId.get(0).getCodeProduto().getDescription());
        for (ItemModel itemModel : itemModelListComId) {
            shoppingCartPegarItens.addItem(newProduct, itemModel.getUnitPrice(), itemModel.getQuantity());
            itemService.delete(itemModel);
        }
        Collection<Item> listaItems = shoppingCartPegarItens.getItems();
        Object[] items = listaItems.toArray();
        Item itemExcluido = (Item)items[0];

        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(itemExcluido.json());
    }

    /**
     * Cria e retorna um novo Item
     *
     * @return Item
     */
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> mudarPrecoItem(@PathVariable(value = "itemId") long itemId,@RequestBody @Valid Map<Object, Object> itemDto, @RequestParam String idCart) throws ParseException {
        long idShoppingCart = Long.parseLong(idCart);
        Optional<ShoppingCartModel> shoppingCartModel = shoppingCartService.findById(idShoppingCart);
        if(!shoppingCartModel.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe carrinho com esse Id");
        }
        Optional<ItemModel> itemModelOptional = itemService.findById(itemId);
        if(!itemModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe item com esse Id no carrinho");
        }
        if(itemModelOptional.get().getIdShop().getShopId() != idShoppingCart){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe item com esse Id no carrinho");
        }
        if(!itemDto.containsKey("unitPrice")){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não contem preco de unidade");
        }

        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModel.get());
        
        Optional<ItemModel> itemModelAux = itemService.findById(itemId);
        List<ItemModel> itemModelListComId = itemModelList.stream().filter((ItemModel item) ->{
            return item.getCodeProduto().equals(itemModelAux.get().getCodeProduto());
        }).collect(Collectors.toList());

        BigDecimal valorTrocar = new BigDecimal(itemDto.get("unitPrice").toString());
        Product newProduct = new Product(itemModelListComId.get(0).getCodeProduto().getCode(), itemModelListComId.get(0).getCodeProduto().getDescription());
        ShoppingCart shoppingCartPegarItens = new ShoppingCart();
        for (ItemModel itemModelTrocarPreco : itemModelList) {
            if(itemModelTrocarPreco.getCodeProduto().getCode() == itemModelAux.get().getCodeProduto().getCode()){
                itemModelTrocarPreco.setUnitPrice(valorTrocar);
                shoppingCartPegarItens.addItem(newProduct, valorTrocar, itemModelTrocarPreco.getQuantity());
                itemService.save(itemModelTrocarPreco);
            };
        }
        Item itemFinal = (Item)shoppingCartPegarItens.getItems().toArray()[0];
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemFinal.json());
    }
}
