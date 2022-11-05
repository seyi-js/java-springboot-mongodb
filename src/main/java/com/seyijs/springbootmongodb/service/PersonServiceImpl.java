package com.seyijs.springbootmongodb.service;

import com.seyijs.springbootmongodb.collection.Person;
import com.seyijs.springbootmongodb.exceptions.NotFoundException;
import com.seyijs.springbootmongodb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;
    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> getPerson(String id) throws Exception {
        try{
            Optional<Person> result = personRepository.findById(id);

            System.out.println(result);

            if(result.isEmpty()){
                throw new NotFoundException("User not found.");
            }



            return result;
        }catch (Exception error){
            throw error;
        }
    }

    @Override
    public List<Person> getPersons() {
        List<Person> result = personRepository.findAll();

        return result;
    }
}
