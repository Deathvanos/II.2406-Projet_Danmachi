package com.isep.appli.services;

import com.isep.appli.models.Inventory;
import com.isep.appli.models.Item;
import com.isep.appli.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Map<Item, Integer> getPlayerInventory(Long playerId){
		Map<Item, Integer> inventory = new HashMap<>();
		List<Inventory> inventoryList = inventoryRepository.findByIdPlayer(playerId);
		for(Inventory inventorySlots : inventoryList){
			Item item = itemService.getById(inventorySlots.getIdItem()).get();
			inventory.put(item, inventorySlots.getQuantity());
		}
		return inventory;
	}
	public Inventory save(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	
	public void delete(Inventory inventory) {
		inventoryRepository.delete(inventory);
	}
	
	public List<Inventory> findById(long id) {
		return this.inventoryRepository.findById(id);
	}
}
