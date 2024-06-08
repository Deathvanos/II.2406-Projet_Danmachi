package com.isep.appli.repositories;

import com.isep.appli.dbModels.Inventory;
import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findById(long id);

    List<Inventory> findByCharacter(Personnage character);

    List<Inventory> findByItem(Item item);
}
