package br.com.geofusion.testecarrinhocompras.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.geofusion.testecarrinhocompras.Model.ItemModel;
import br.com.geofusion.testecarrinhocompras.Repository.ItemRepository;

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

    public boolean existsById(long id) {
        return itemRepository.existsById(id);
    }

    public Optional<ItemModel> findById(long id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public long delete(ItemModel itemModel) {
        long id = itemModel.getId();
        itemRepository.delete(itemModel);
        return id;
    }
}
