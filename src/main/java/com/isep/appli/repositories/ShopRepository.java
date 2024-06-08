package com.isep.appli.repositories;

import com.isep.appli.dbModels.Item;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findById(long id);

    List<Shop> findBySeller(Personnage character);

    Shop findBySellerAndItem(Personnage personnage, Item item);
}
