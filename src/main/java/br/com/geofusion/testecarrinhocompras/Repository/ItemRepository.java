package br.com.geofusion.testecarrinhocompras.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;

//Interface for decouple purpose
@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long>{
    boolean existsById(Long id);
}
