package com.example.carpark.repository;

import com.example.carpark.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository  extends JpaRepository<Code, String> { //1-entity, 2-pazi se id
}
