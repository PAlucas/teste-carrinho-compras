package br.com.geofusion.testecarrinhocompras.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.testecarrinhocompras.services.ClientService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Cliente")
public class ClientController {
    
    @Autowired
    ClientService clientService;

}
