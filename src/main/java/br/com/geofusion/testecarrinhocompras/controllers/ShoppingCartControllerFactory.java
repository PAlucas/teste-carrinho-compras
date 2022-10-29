package br.com.geofusion.testecarrinhocompras.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
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


    @PostMapping
    public ResponseEntity<Object> saveCarrinho(@RequestParam String idCliente){
        long client_id = Long.parseLong(idCliente);
        Optional<ClientModel> clientModelOptional =  clientService.findById(client_id);
        if(!clientModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Não existe Cliente com esse Id");
        }
        Optional<ShoppingCartModel> shoppingCartModelAux = shoppingCartService.findByidClient(clientModelOptional.get());
        if(shoppingCartModelAux.isPresent()){
            
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(shoppingCartModelAux.get());
        }
        
        
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(idCliente);
    }
    //    /**
    //  * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
    //  *
    //  * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
    //  *
    //  * @param clientId
    //  * @return ShoppingCart
    //  */
    // public ShoppingCart create(String clientId) {
    //     return null;
    // }
    // /**
    //  * Retorna o valor do ticket médio no momento da chamada ao método.
    //  * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
    //  * pela quantidade de carrinhos de compra.
    //  * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
    //  * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
    //  *
    //  * @return BigDecimal
    //  */
    // public BigDecimal getAverageTicketAmount() {
    //     return null;
    // }

    // /**
    //  * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
    //  * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
    //  *
    //  * @param clientId
    //  * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
    //  * e false caso o cliente não possua um carrinho.
    //  */
    // public boolean invalidate(String clientId) {
    //     return false;
    // }
}
