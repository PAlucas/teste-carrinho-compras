package br.com.geofusion.testecarrinhocompras.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.classes.Product;
import br.com.geofusion.testecarrinhocompras.classes.ShoppingCart;
import br.com.geofusion.testecarrinhocompras.services.ClientService;
import br.com.geofusion.testecarrinhocompras.services.ItemService;
import br.com.geofusion.testecarrinhocompras.services.ShoppingCartService;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Carrinho")
public class ShoppingCartControllerFactory {
    final ItemService itemService;
    final ClientService clientService;     
    public ShoppingCartControllerFactory(ClientService clientService, ItemService itemService){
        this.clientService = clientService;
        this.itemService = itemService;
    }
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param clientId
     * @return ShoppingCart
     */
    @PostMapping
    public ResponseEntity<Object> saveCarrinho(@RequestParam String idCliente){
        long client_id = Long.parseLong(idCliente);
        Optional<ClientModel> clientModelOptional =  clientService.findById(client_id);
        if(!clientModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe Cliente com esse Id");
        }
        Optional<ShoppingCartModel> shoppingCartModelAux = shoppingCartService.findByidClient(clientModelOptional.get());

        if(!shoppingCartModelAux.isPresent()){
            ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
            shoppingCartModel.setIdClient(clientModelOptional.get());
            shoppingCartService.save(shoppingCartModel);
            return  ResponseEntity.status(HttpStatus.CREATED).body("Carrinho criado");
        }
        
        ShoppingCart shoppingCart = new ShoppingCart();
        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModelAux.get());
        for (ItemModel itemModelAdd : itemModelList) {
            Product produtoAdd = new Product(itemModelAdd.getCodeProduto().getCode(), itemModelAdd.getCodeProduto().getDescription());
            shoppingCart.addItem(produtoAdd, itemModelAdd.getUnitPrice(), itemModelAdd.getQuantity());
        }   
        
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(shoppingCart.getItems());
    }

    /**
     * Pegar todos os itens do carrinho de um cliente
     * @param idCliente
     * @return ResponseEntity<Object>
     */
    @GetMapping("/{client_id}")
    public ResponseEntity<Object> mostraCarrinho(@PathVariable(value = "client_id") long idCliente){;
        Optional<ClientModel> clientModelOptional =  clientService.findById(idCliente);
        if(!clientModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe Cliente com esse Id");
        }

        Optional<ShoppingCartModel> shoppingCartModelAux = shoppingCartService.findByidClient(clientModelOptional.get());
        if(!shoppingCartModelAux.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Carrinho não existe");
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModelAux.get());
        for (ItemModel itemModelAdd : itemModelList) {
            Product produtoAdd = new Product(itemModelAdd.getCodeProduto().getCode(), itemModelAdd.getCodeProduto().getDescription());
            shoppingCart.addItem(produtoAdd, itemModelAdd.getUnitPrice(), itemModelAdd.getQuantity());
        }   
        
        return  ResponseEntity.status(HttpStatus.OK).body(shoppingCart.getItems());
    }
    /**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
    @GetMapping("/Total")
    public ResponseEntity<String> getAverageTicketAmount() {
        BigDecimal total = new BigDecimal(0);
        List<ShoppingCartModel> todosCarrinhos = shoppingCartService.findAll();
        for (ShoppingCartModel carrinho : todosCarrinhos) {
            List<ItemModel> itemModelList = itemService.findAllByIdShop(carrinho);
            ShoppingCart shoppingCart = new ShoppingCart();
            for (ItemModel itemModelAdd : itemModelList) {
                Product produtoAdd = new Product(itemModelAdd.getCodeProduto().getCode(), itemModelAdd.getCodeProduto().getDescription());
                shoppingCart.addItem(produtoAdd, itemModelAdd.getUnitPrice(), itemModelAdd.getQuantity());
            }   
            total = total.add(shoppingCart.getAmount());
        }
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(total.setScale(2, RoundingMode.HALF_EVEN).toString());
    }
    
    /**
     * Trazer todos os carrinhos com informações do id dele e do cliente que ele pertence
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ShoppingCartModel>> mostrarTodosOsCarrinhos(){
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartService.findAll());
    }

    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param clientId
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteCarrinho(@RequestParam String idCliente) {
        long client_id = Long.parseLong(idCliente);
        Optional<ClientModel> clientModelOptional =  clientService.findById(client_id);

        if(!clientModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }
        Optional<ShoppingCartModel> shoppingCartModelAux = shoppingCartService.findByidClient(clientModelOptional.get());
        if(!shoppingCartModelAux.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }

        List<ItemModel> itemModelList = itemService.findAllByIdShop(shoppingCartModelAux.get());
        ShoppingCart shoppingCart = new ShoppingCart();
        for (ItemModel itemModelAdd : itemModelList) {
            Product produtoAdd = new Product(itemModelAdd.getCodeProduto().getCode(), itemModelAdd.getCodeProduto().getDescription());
            shoppingCart.addItem(produtoAdd, itemModelAdd.getUnitPrice(), itemModelAdd.getQuantity());
            shoppingCart.removeItem(produtoAdd);
            itemService.delete(itemModelAdd);
        } 
        if(shoppingCart.semItens()){
            shoppingCartService.delete(shoppingCartModelAux.get());
        }
        
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }
}
