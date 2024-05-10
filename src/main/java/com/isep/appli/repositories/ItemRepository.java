package com.isep.appli.repositories;

import com.isep.appli.models.Item;
import com.isep.appli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	public List<Item> findById(long id);

	public List<Item> findByName(String name);

}
