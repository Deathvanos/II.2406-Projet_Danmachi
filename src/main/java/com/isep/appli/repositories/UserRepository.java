package com.isep.appli.repositories;
import com.isep.appli.dbModels.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmail(String email);

	User findUserByUsername(String username);


	List<User> findAllByIsLoginIsTrue();




}
