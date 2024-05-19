package com.isep.appli.services;


import com.isep.appli.dbModels.Inventory;
import com.isep.appli.dbModels.Item;
import com.isep.appli.models.Shop;
import com.isep.appli.models.enums.ItemCategory;
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

	public Map<Item, Shop> getAllShop(){
		List<Shop> allShopList = (List<Shop>) getAll();
		Map<Item, Shop> allShop = new HashMap<>();
		for (Shop shopCell: allShopList ) {
			Item item = itemService.getById(shopCell.getIdItem()).get();
			allShop.put(item, shopCell);
		}
		return allShop;
	}

	public Map<Item, Shop> getShopByItemName(Long playerId, String itemName){

		Map<Item, Shop> allShop = getAllShop();
		Map<Item, Shop> shopWithFilter = new HashMap<>();

		for (Item item: allShop.keySet()) {
			if(item.getName().toLowerCase().contains(itemName.toLowerCase())){
				shopWithFilter.put(item, allShop.get(item));
			}
		}
		return shopWithFilter;
	}

	public Map<Item, Shop> getShopByItemCategory(Long playerId, ItemCategory itemCategory){

		Map<Item, Shop> allShop = getAllShop();
		Map<Item, Shop> shopWithFilter = new HashMap<>();

		for (Item item: allShop.keySet()) {
			if(item.getCategory().equals(itemCategory)){
				shopWithFilter.put(item, allShop.get(item));
			}
		}
		return shopWithFilter;
	}

	public Map<Item, Shop> getShopByItemNameAndItemCategory(Long playerId, String itemName, ItemCategory itemCategory){

		Map<Item, Shop> allShop = getAllShop();
		Map<Item, Shop> shopWithFilter = new HashMap<>();

		for (Item item: allShop.keySet()) {
			if(item.getName().toLowerCase().contains(itemName.toLowerCase()) && item.getCategory().equals(itemCategory)){
				shopWithFilter.put(item, allShop.get(item));
			}
		}
		return shopWithFilter;
	}

}
