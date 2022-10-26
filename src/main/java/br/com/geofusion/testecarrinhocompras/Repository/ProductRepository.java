package br.com.geofusion.testecarrinhocompras.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.testecarrinhocompras.Model.ProductModel;

//Interface for decouple purpose
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
    boolean existsById(Long id);
}
