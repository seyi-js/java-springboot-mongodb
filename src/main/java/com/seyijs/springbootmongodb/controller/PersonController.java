package com.seyijs.springbootmongodb.controller;

import com.seyijs.springbootmongodb.collection.Person;
import com.seyijs.springbootmongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")

public class PersonController {

    @Autowired
    private PersonService personService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person){
        return  personService.createPerson(person);
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

    @GetMapping("/age")
    public List<Person> findByAge(@RequestParam("minAge") Integer minAge, @RequestParam("maxAge") Integer maxAge){

        return personService.findByAge(minAge,maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPersons(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String hobby,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){

        Pageable pageable = PageRequest.of(page,size);

        return  personService.search(firstName,lastName, minAge,maxAge,city,hobby, pageable);

    }

    @GetMapping("/oldestPersonInCityByAge")
    public  List<Document> getoldestPersonInCityByAge(){
        return personService.getOldestPersonInCityByAge();
    }

    @GetMapping("populationByCity")
    public List<Document> getPopulationByCity(){
        return personService.getPopulationByCity();
    }

}
