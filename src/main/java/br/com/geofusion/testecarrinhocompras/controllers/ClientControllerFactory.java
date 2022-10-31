package br.com.geofusion.testecarrinhocompras.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.classes.Client;
import br.com.geofusion.testecarrinhocompras.classes.Product;
import br.com.geofusion.testecarrinhocompras.classes.ShoppingCart;
import br.com.geofusion.testecarrinhocompras.dto.ClientDto;
import br.com.geofusion.testecarrinhocompras.services.ClientService;
import br.com.geofusion.testecarrinhocompras.services.ItemService;
import br.com.geofusion.testecarrinhocompras.services.ShoppingCartService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Cliente")
public class ClientControllerFactory {
    final ShoppingCartService shoppingCartService;     
    final ItemService itemService;   
    public ClientControllerFactory(ShoppingCartService shoppingCartService, ItemService itemService){
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
    }
    @Autowired
    ClientService clientService;

    /**
     * Método post para adicionar um cliente no banco de dados e esse cliente no banco de dados
     * ele recebe um json com o parâmetro Nome recebendo o nome escolhido.
     * { "nome": "Lucas"}
     * @param clienteDto
     * @return ResponseEntity<Object>
     * @throws ParseException
     */
    @PostMapping
    public @ResponseBody ResponseEntity<Object> saveCliente (@RequestBody @Valid ClientDto clienteDto) throws ParseException {

        ClientModel clientModel = new ClientModel();
        BeanUtils.copyProperties(clienteDto, clientModel);
        clientService.save(clientModel);
        Client client = new Client(clientModel.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(client.json());
    }

    /**
     * Método get que mostra todos os clientes que foram cadastrados no banco de dados
     * @return ResponseEntity<List<Client>>
     */
    @GetMapping
    public @ResponseBody ResponseEntity<List<Client>> todosClientes() {
        List<Client> listaDeClientes = new ArrayList<Client>();
        clientService.findAll().stream().forEach((ClientModel cliente)->{
            var clienteAdd = new Client(cliente.getNome());
            listaDeClientes.add(clienteAdd);
        } );

        return ResponseEntity.status(HttpStatus.OK).body(listaDeClientes);
    }

    /**
     * Método patch que mostra troca o nome do cliente com base no id passado na url
     * @return ResponseEntity<Object>
     */
    @PatchMapping("/{client_id}")
    public @ResponseBody ResponseEntity<Object> trocarNomeCliente (@PathVariable(value = "client_id") long id,@RequestBody @Valid ClientDto clienteDto) throws ParseException{
        Optional<ClientModel> clientModelOptional = clientService.findById(id);
        if(!clientModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Cliente não foi encontrado");
        }
        BeanUtils.copyProperties(clienteDto, clientModelOptional.get());
        clientService.save(clientModelOptional.get());
        Client client = new Client(clientModelOptional.get().getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(client.nomeNovoJson());
    }

    /**
     * Método delete que deleta cliente e mostra cliente deletado
     * @return ResponseEntity<Object>
     */
    @DeleteMapping("/{client_id}")
    public @ResponseBody ResponseEntity<Object> DeletarCliente (@PathVariable(value = "client_id") long id){
        Optional<ClientModel> clientModelOptional = clientService.findById(id);
        if(!clientModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não foi encontrado");
        }
        Client client = new Client(clientModelOptional.get().getNome());


        Optional<ShoppingCartModel> shoppingCartModelAux = shoppingCartService.findByidClient(clientModelOptional.get());
        if(!shoppingCartModelAux.isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(client.nomeDeletadoJson());
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
        clientService.delete(clientModelOptional.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(client.nomeDeletadoJson());
    }


}
