package com.isep.appli.services;


import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Shop;
import com.isep.appli.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ItemService itemService;

    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    public void delete(Shop shop) {
        shopRepository.delete(shop);
    }

    public Shop findById(long id) {
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

    public void removeItemInShop(Shop shop, int quantityToRemove) {
        int newQuantity = shop.getQuantity() - quantityToRemove;
        if (newQuantity > 0) {
            shop.setQuantity(newQuantity);
        } else {
            delete(shop);
        }
    }

    public List<Shop> findAllShopWhereItemNameIsNot(List<Shop> allShop, Item itemToFind) {
        List<Shop> shopWithFilter = new ArrayList<>();
        String itemNameToFind = itemToFind.getName().toLowerCase();
        for (Shop shop : allShop) {
            Item item = shop.getItem();
            String itemName = item.getName().toLowerCase();
            if (!itemName.contains(itemNameToFind)) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> findAllShopWhereItemCategoryIsNot(List<Shop> allShop, Item itemToFind) {
        List<Shop> shopWithFilter = new ArrayList<>();

        for (Shop shop : allShop) {
            Item item = shop.getItem();
            if (!item.getCategory().equals(itemToFind.getCategory())) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> findAllShopWhereMaxPriceIsNot(List<Shop> allShop, Long maxPrice) {
        List<Shop> shopWithFilter = new ArrayList<>();

        for (Shop shop : allShop) {
            if (shop.getPrice() > maxPrice) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> findAllShopWhereMinPriceIsNot(List<Shop> allShop, Long minPrice) {
        List<Shop> shopWithFilter = new ArrayList<>();
        for (Shop shop : allShop) {
            if (shop.getPrice() < minPrice) {
                shopWithFilter.add(shop);
            }
        }
        return shopWithFilter;
    }

    public List<Shop> getPlayerShop(Personnage seller) {
        return shopRepository.findBySeller(seller);
    }
}
