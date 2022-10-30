package br.com.geofusion.testecarrinhocompras.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
     * Cria e retorna um novo produto
     *
     * @return Produto
     */
    @PostMapping
    public ResponseEntity<Object> saveProduto(@RequestBody @Valid ItemDto itemDto, @RequestParam String idCart) throws ParseException {
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

        Product productFinal = new Product(productModel.get().getCode(), productModel.get().getDescription());
        Item itemFinal = new Item(productFinal, valorUnitarioItem, quantidadeItens);
        
        return  ResponseEntity.status(HttpStatus.CREATED).body(itemFinal.json());
    }
}
