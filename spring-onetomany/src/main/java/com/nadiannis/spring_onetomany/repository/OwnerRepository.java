package com.nadiannis.spring_onetomany.repository;

import com.nadiannis.spring_onetomany.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
