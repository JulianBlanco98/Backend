package com.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);
}
