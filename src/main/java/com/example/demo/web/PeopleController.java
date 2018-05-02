package com.example.demo.web;

import com.example.demo.dao.PeopleRepository;
import com.example.demo.entity.People;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PeopleController {
    @Autowired
    PeopleRepository peopleRepository;
    @Autowired
    Configuration configuration;

    @RequestMapping("/test")
    public  String testDb(Map<String,Object> model) throws Exception{
        People people = peopleRepository.findPeopleById(1);
        Template t = configuration.getTemplate("people.html"); // freeMarker template
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, people);
        return content;
    }

}
