package com.seyijs.springbootmongodb.service;

import com.seyijs.springbootmongodb.collection.Person;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person save(Person person);

    Optional<Person> getPerson(String id) throws Exception;

    List<Person> getPersons();
}
