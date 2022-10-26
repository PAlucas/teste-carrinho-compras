package br.com.geofusion.testecarrinhocompras.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ShoppingCartRepository;

public class ShoppingCartService {
    final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional
    public ShoppingCartModel save(ShoppingCartModel shoppingCartModel) {
        return shoppingCartRepository.save(shoppingCartModel);
    }

    public List<ShoppingCartModel> findAll(){
        return shoppingCartRepository.findAll();
    }

    public boolean existsByCode(long id) {
        return shoppingCartRepository.existsById(id);
    }

    public Optional<ShoppingCartModel> findByCode(long code) {
        return shoppingCartRepository.findById(code);
    }

    @Transactional
    public long delete(ShoppingCartModel shoppingCartModel) {
        long id = shoppingCartModel.getShopId();
        shoppingCartRepository.delete(shoppingCartModel);
        return id;
    }
}
