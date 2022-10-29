package br.com.geofusion.testecarrinhocompras.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.testecarrinhocompras.Model.ClientModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;

//Interface for decouple purpose
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartModel, Long>{
    boolean existsById(Long id);
    Optional<ShoppingCartModel> findByidClient(ClientModel idCliente);
}

