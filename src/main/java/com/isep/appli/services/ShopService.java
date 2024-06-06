package com.isep.appli.services;


import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Shop;
import com.isep.appli.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ItemService itemService;

    public Iterable<Shop> getAll() {
        return shopRepository.findAll();
    }

    public Optional<Shop> getById(Long id) {
        return shopRepository.findById(id);
    }

    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    public void delete(Shop shop) {
        shopRepository.delete(shop);
    }

    public List<Shop> findById(long id) {
        return this.shopRepository.findById(id);
    }


    public List<Shop> getShopByItemName(Item itemToFind) {

        List<Shop> allShop = (List<Shop>) getAll();
        List<Shop> shopWithFilter = new ArrayList<>();

        for (Shop shop : allShop) {
            Item item = shop.getItem();
            if (item.getName().toLowerCase().contains(itemToFind.getName().toLowerCase())) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> getShopByItemCategory(Item itemToFind) {

        List<Shop> allShop = (List<Shop>) getAll();
        List<Shop> shopWithFilter = new ArrayList<>();

        for (Shop shop : allShop) {
            Item item = shop.getItem();
            if (item.getCategory().equals(itemToFind.getCategory())) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> getShopByItemNameAndItemCategory(Item itemToFind) {

        List<Shop> allShop = (List<Shop>) getAll();
        List<Shop> shopWithFilter = new ArrayList<>();

        for (Shop shop : allShop) {
            Item item = shop.getItem();
            if (item.getName().toLowerCase().contains(itemToFind.getName().toLowerCase()) && item.getCategory().equals(itemToFind.getCategory())) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

}
