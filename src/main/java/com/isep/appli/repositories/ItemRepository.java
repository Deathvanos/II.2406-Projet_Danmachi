package com.isep.appli.repositories;

import com.isep.appli.dbModels.Item;
import com.isep.appli.models.enums.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	public Item findById(long id);

	public List<Item> findByName(String name);

	public List<Item> findByCategory(ItemCategory itemCategory);

}
