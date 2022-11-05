package com.seyijs.springbootmongodb.controller;

import com.seyijs.springbootmongodb.collection.Person;
import com.seyijs.springbootmongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;


    @PostMapping
    public Person save(@RequestBody Person person){
        return  personService.save(person);
    }

    @GetMapping("/{id}")
    public Optional<Person> findPerson(@PathVariable String id) throws Exception {

        try{
            return personService.getPerson(id);
        }catch (Error | Exception error){
            throw error;
        }
    }

    @GetMapping
    public List<Person> findPersons(){
        return personService.getPersons();
    }

}
