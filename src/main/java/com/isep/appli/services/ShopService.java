package com.isep.appli.services;

import com.isep.appli.models.Inventory;
import com.isep.appli.models.Item;
import com.isep.appli.models.Shop;
import com.isep.appli.repositories.InventoryRepository;
import com.isep.appli.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}
