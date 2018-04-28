package com.example.demo.dao;

import com.example.demo.entity.People;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CrudRepository<People,Integer> {
    People findPeopleById(Integer id);
}
