package br.com.geofusion.testecarrinhocompras.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Model.ShoppingCartModel;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;
@Service
public class ItemService {
    final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemModel save(ItemModel itemModel) {
        return itemRepository.save(itemModel);
    }

    public List<ItemModel> findAll(){
        return itemRepository.findAll();
    }
    
    // public List<ItemModel> findAllById(Iterable<ShoppingCartModel> idShop) {
    //     List<ItemModel> results = new ArrayList<ItemModel>();

    //     for (ShoppingCartModel idShops : idShop) {
    //         findById(idShops).ifPresent(results::add);
    //     }

	// 	return results;

	// }

    public boolean existsById(long id) {
        return itemRepository.existsById(id);
    }

    public Optional<ItemModel> findById(long id) {
        return itemRepository.findById(id);
    }

    // public Optional<ItemModel> findByIdShop(ShoppingCartModel id) {
    //     return itemRepository.findByIdShop(id);
    // }

    @Transactional
    public long delete(ItemModel itemModel) {
        long id = itemModel.getId();
        itemRepository.delete(itemModel);
        return id;
    }
}
