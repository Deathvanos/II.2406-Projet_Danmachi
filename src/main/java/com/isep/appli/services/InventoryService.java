package com.isep.appli.services;

import com.isep.appli.dbModels.Inventory;
import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ItemService itemService;

    public Iterable<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getById(Long id) {
        return inventoryRepository.findById(id);
    }

    public List<Inventory> getPlayerInventory(Personnage character) {
        return inventoryRepository.findByCharacter(character);
    }

    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void delete(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    public void removeItemInInventory(Inventory inventory, int quantityToRemove) {
        int newQuantity = inventory.getQuantity() - quantityToRemove;
        if (newQuantity > 0) {
            inventory.setQuantity(newQuantity);
        } else {
            delete(inventory);
        }
    }

    public void addItemForPlayer(Personnage personnage, Item item, int quantityToAdd){
        Inventory inventory = findByItemId(personnage, item);
        if(inventory.getId() != null){
            int currentQuantity = inventory.getQuantity();
            inventory.setQuantity(currentQuantity + quantityToAdd);
        } else {
            inventory = new Inventory();
            inventory.setItem(item);
            inventory.setCharacter(personnage);
            inventory.setQuantity(quantityToAdd);
        }
        save(inventory);
    }

    public List<Inventory> findById(long id) {
        return this.inventoryRepository.findById(id);
    }

    public List<Inventory> getPlayerInventoryByItemName(Personnage character, Item item) {

        List<Inventory> playerInventory = getPlayerInventory(character);
        List<Inventory> filterInventory = new ArrayList<>();

        for (Inventory inventory : playerInventory) {
            Item currentItem = inventory.getItem();
            if (currentItem.getName().toLowerCase().contains(item.getName().toLowerCase())) {
                filterInventory.add(inventory);
            }
        }
        return filterInventory;
    }

    public Inventory findByItemId(Personnage character, Item item) {
        List<Inventory> playerInventory = inventoryRepository.findByCharacter(character);
        Inventory returnedInventory = new Inventory();
        for (Inventory inventory : playerInventory) {
            if (inventory.getItem().getId() == item.getId()) {
                return inventory;
            }
        }
        return returnedInventory;
    }

    public List<Inventory> getPlayerInventoryByItemNameAndItemCategory(Personnage character, Item itemToFind) {
        List<Inventory> playerInventory = getPlayerInventory(character);
        List<Inventory> filterInventory = new ArrayList<>();
        for (Inventory inventory : playerInventory) {
            Item item = inventory.getItem();
            if (item.getName().toLowerCase().contains(itemToFind.getName().toLowerCase()) && item.getCategory().equals(itemToFind.getCategory())) {
                filterInventory.add(inventory);
            }
        }
        return filterInventory;
    }

    public List<Inventory> getPlayerInventoryByItemCategory(Personnage character, Item itemToFind) {
        List<Inventory> playerInventory = getPlayerInventory(character);
        List<Inventory> filterInventory = new ArrayList<>();
        for (Inventory inventory : playerInventory) {
            Item item = inventory.getItem();
            if (item.getCategory().equals(itemToFind.getCategory())) {
                filterInventory.add(inventory);
            }
        }
        return filterInventory;
    }
}
