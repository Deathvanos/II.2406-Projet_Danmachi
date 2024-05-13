package com.isep.appli.repositories;

import com.isep.appli.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
	List<Shop> findById(long id);
	List<Shop> findByIdPlayer(long id);
}
