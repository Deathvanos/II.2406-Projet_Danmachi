package com.isep.appli.repositories;
import com.isep.appli.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
