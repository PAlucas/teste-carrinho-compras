package br.com.geofusion.testecarrinhocompras.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ProductModel;
import br.com.geofusion.testecarrinhocompras.classes.Product;
import br.com.geofusion.testecarrinhocompras.dto.ProductDto;
import br.com.geofusion.testecarrinhocompras.services.ProductService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Produto")
public class ProductControllerFactory {
    @Autowired
    private ProductService productService;

    /**
     * Cria e retorna um novo produto
     *
     * @return Produto
     */
    @PostMapping
    public ResponseEntity<Object> saveProduto(@RequestBody @Valid ProductDto productDto) throws ParseException {
        ProductModel productModel = new ProductModel();
        ProductModel productModelAux = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModelAux = productService.save(productModel);

        Product product = new Product(productModelAux.getCode(), productModelAux.getDescription());
        return  ResponseEntity.status(HttpStatus.CREATED).body(product.json());
    }

    /**
     * Pega todos os produtos dispniveis e retorna a informação desejada deles
     *
     * @return lista de produtos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getTodosProdutos(){
        List<ProductModel> productList = productService.findAll(); 
        List<Product> listaProdutos = productList.stream().map((ProductModel prod)->{
            return new Product(prod.getCode(), prod.getDescription());
        }).collect(Collectors.toList());
        
        return  ResponseEntity.status(HttpStatus.OK).body(listaProdutos);
    }
}
