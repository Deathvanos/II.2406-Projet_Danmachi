package com.isep.appli.services;

import com.isep.appli.dbModels.Inventory;
import com.isep.appli.dbModels.Item;
import com.isep.appli.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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

	public Map<Item, Inventory> getPlayerInventory(Long playerId){
		Map<Item, Inventory> inventory = new HashMap<>();
		List<Inventory> inventoryList = inventoryRepository.findByIdPlayer(playerId);
		for(Inventory inventorySlots : inventoryList){
			Item item = itemService.getById(inventorySlots.getIdItem()).get();
			inventory.put(item, inventorySlots);
		}
		return inventory;
	}
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	
	public void delete(Inventory inventory) {
		inventoryRepository.delete(inventory);
	}

	public void removeItemInInventory(Inventory inventory, int quantityToRemove){
		int newQuantity = inventory.getQuantity() - quantityToRemove;
		if(newQuantity > 0){
			inventory.setQuantity(newQuantity);
		} else {
			delete(inventory);
		}
	}
	
	public List<Inventory> findById(long id) {
		return this.inventoryRepository.findById(id);
	}

	public Map<Item, Inventory> getPlayerInventoryByItemName(Long playerId, String itemName){
		Map<Item, Inventory> playerInventory = getPlayerInventory(playerId);
		Map<Item, Inventory> inventory = new HashMap<>();
		for (Item item: playerInventory.keySet()) {
			if(item.getName().toLowerCase().contains(itemName.toLowerCase())){
				inventory.put(item, playerInventory.get(item));
			}
		}
		return inventory;
	}

	public List<Inventory> getByItemId(long playerId, long itemId){
		List<Inventory> playerInventory = inventoryRepository.findByIdPlayer(playerId);
		List<Inventory> returnedList = new ArrayList<>();
		for (Inventory inventory: playerInventory ) {
			if(inventory.getIdItem() == itemId){
				returnedList.add(inventory);
			}
		}
		return  returnedList;
	}
}
