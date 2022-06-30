package com.uluer.demo.sprudt.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("person_table")
public class Person {

    @Id
    int id;

    String firstname, lastname;
    Address current;
    List<Address> previous;

}
