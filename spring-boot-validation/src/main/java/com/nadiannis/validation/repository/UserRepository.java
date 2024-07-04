package com.nadiannis.validation.repository;

import com.nadiannis.validation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}