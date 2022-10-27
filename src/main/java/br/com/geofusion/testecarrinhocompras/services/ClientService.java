package br.com.geofusion.testecarrinhocompras.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Repository.ClientRepository;
@Service
public class ClientService {
        final ClientRepository clientRepository;

        public ClientService(ClientRepository clientRepository) {
            this.clientRepository = clientRepository;
        }
    
        @Transactional
        public ClientModel save(ClientModel clientModel) {
            return clientRepository.save(clientModel);
        }
    
        public List<ClientModel> findAll(){
            return clientRepository.findAll();
        }
    
        public boolean existsById(long id) {
            return clientRepository.existsById(id);
        }
    
        public Optional<ClientModel> findById(long id) {
            return clientRepository.findById(id);
        }
    
        @Transactional
        public String delete(ClientModel clienteModel) {
            String nome = clienteModel.getNome();
            clientRepository.delete(clienteModel);
            return nome;
        }
}
