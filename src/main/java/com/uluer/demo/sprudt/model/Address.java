package com.uluer.demo.sprudt.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@UserDefinedType //must be same
public class Address {

    private String street;
    private String zip;
    private String city;

}