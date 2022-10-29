package br.com.geofusion.testecarrinhocompras.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;

//Interface for decouple purpose
@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long>{
    boolean existsById(Long id);
    //Optional<ItemModel> findByIdShop(ShoppingCartModel id);
}
