package com.seyijs.springbootmongodb.service;

import com.seyijs.springbootmongodb.collection.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.bson.Document;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person createPerson(Person person);

    Optional<Person> getPerson(String id) throws Exception;

    List<Person> getPersons();

    List<Person> findByAge(Integer minAge, Integer maxAge);

    Page<Person> search(String name, String lastName, Integer minAge, Integer maxAge, String city, String hobby, Pageable pageable);


    List<Document> getOldestPersonInCityByAge();
}
