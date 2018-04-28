package com.example.demo.web;

import com.example.demo.dao.PeopleRepository;
import com.example.demo.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PeopleController {
    @Autowired
    PeopleRepository peopleRepository;
    @RequestMapping("/test")
    public  People testDb(Map<String,Object> model){
        People people = peopleRepository.findPeopleById(1);
        return people;
    }

}
