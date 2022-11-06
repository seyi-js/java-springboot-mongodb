package com.seyijs.springbootmongodb.service;

import com.seyijs.springbootmongodb.collection.Person;
import com.seyijs.springbootmongodb.exceptions.NotFoundException;
import com.seyijs.springbootmongodb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Person createPerson(Person person) {
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

    @Override
    public List<Person> findByAge(Integer minAge, Integer maxAge) {
        return personRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> search(String firstName, String lastName, Integer minAge, Integer maxAge, String city, String hobby, Pageable pageable) {

        Query query = new Query().with(pageable);

        List<Criteria> criteria = new ArrayList<>();

        if(firstName != null && !firstName.isEmpty()){
            criteria.add(Criteria.where("firstName").regex(firstName, "i"));
        }

        if(lastName != null && !lastName.isEmpty()){
            criteria.add(Criteria.where("lastName").regex(lastName, "i"));
        }

        if(minAge != null && maxAge != null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if(city != null && !city.isEmpty()){
            criteria.add(Criteria.where("addresses.city").is(city));

        }

        if(hobby != null && !hobby.isEmpty()){
            criteria.add(Criteria.where("hobbies").in(hobby));
        }

        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        List template =  mongoTemplate.find(query, Person.class);

        LongSupplier count = () -> mongoTemplate.count(query.skip(0).limit(0),Person.class);

        Page<Person> people = PageableExecutionUtils.getPage(template, pageable,count);

        return people;
    }

    @Override
    public List<Document> getOldestPersonInCityByAge() {

        //Flatten the addresses lists for all users
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");

        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation, groupOperation);

        List<Document> persons = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();

        return persons;


    }
}
