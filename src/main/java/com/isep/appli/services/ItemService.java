package com.isep.appli.services;

import com.isep.appli.dbModels.Item;
import com.isep.appli.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Base64;
import java.util.List;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ImageService imageService;

    public Iterable<Item> getAll() {
        return itemRepository.findAll();
    }


    public Item save(Item item, InputStream file){
        if (item.getId() == null) {
            item.setCreatedAt(Instant.now());
        }
        if(file != null){
            try {
                byte[] compressedFile = imageService.compressImage(file);
                item.setUrlImage(Base64.getEncoder().encodeToString(compressedFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        item.setUpdatedAt(Instant.now());
        return itemRepository.save(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public Item findById(long id) {
        return this.itemRepository.findById(id);
    }

    public List<Item> findByName(String name) {
        return this.itemRepository.findByName(name);
    }

    public List<Item> findAllItems() {return this.itemRepository.findAll();}

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public void updateItem(Long id, Item newItem) {
        Item itemOld = itemRepository.findById(id).orElse(null);
        assert itemOld != null;
        itemOld.setName(newItem.getName());
        itemOld.setCategory(newItem.getCategory());
        itemOld.setCanUse(newItem.isCanUse());
        itemOld.setDescription(newItem.getDescription());
        itemOld.setUseDescription(newItem.getUseDescription());
        itemRepository.save(itemOld);
    }
}
