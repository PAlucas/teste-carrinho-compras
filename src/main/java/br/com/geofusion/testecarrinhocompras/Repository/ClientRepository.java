package br.com.geofusion.testecarrinhocompras.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;

//Interface for decouple purpose
@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long>{
    boolean existsById(Long id);
}