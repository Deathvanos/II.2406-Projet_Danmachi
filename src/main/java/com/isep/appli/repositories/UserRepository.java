package com.isep.appli.repositories;
import com.isep.appli.dbModels.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmail(String email);

	User findUserByUsername(String username);




}
