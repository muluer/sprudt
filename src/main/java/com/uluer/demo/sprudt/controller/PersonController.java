package com.uluer.demo.sprudt.controller;

import com.uluer.demo.sprudt.model.Person;
import com.uluer.demo.sprudt.repository.SprUdtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    SprUdtRepository sprUdtRepository;

    @GetMapping("/get_all")
    public ResponseEntity<List<Person>> getAll() {
        List<Person> resp = sprUdtRepository.findAll();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Person>> getById(@PathVariable int id) {
        Optional<Person> resp = sprUdtRepository.findById(id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/save")
    public ResponseEntity<Person> saveById(@RequestBody Person person) {
        Person resp = sprUdtRepository.save(person);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Person person) {
        sprUdtRepository.delete(person);
        return ResponseEntity.ok("success");
    }
}
