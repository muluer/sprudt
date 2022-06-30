package com.uluer.demo.sprudt.repository;

import com.uluer.demo.sprudt.model.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprUdtRepository extends CassandraRepository<Person, Integer> {
}
