package com.seyijs.springbootmongodb.repository;

import com.seyijs.springbootmongodb.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {

//    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);
    @Query(value = "{ 'age':{ $gte: ?0, $lte: ?1 } }", fields = "{addresses: 0}")
    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);
}
