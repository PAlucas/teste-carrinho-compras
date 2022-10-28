package br.com.geofusion.testecarrinhocompras.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.classes.Client;
import br.com.geofusion.testecarrinhocompras.dto.ClientDto;
import br.com.geofusion.testecarrinhocompras.services.ClientService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Cliente")
public class ClientControllerFactory {
    
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

}
