package com.isep.appli.services;

import com.isep.appli.dbModels.Item;
import com.isep.appli.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public Iterable<Item> getAll() {
		return itemRepository.findAll();
	}
	
	public Optional<Item> getById(Long id) {
		return itemRepository.findById(id);
	}
	
	public Item save(Item item) {
		if (item.getId() == null) {
			item.setCreatedAt(Instant.now());
		}
		item.setUpdatedAt(Instant.now());
		return itemRepository.save(item);
	}
	
	public void delete(Item item) {
		itemRepository.delete(item);
	}
	
	public List<Item> findById(long id) {
		return this.itemRepository.findById(id);
	}
}
