package com.example.reactivemongo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Employee {

    @Id
    public String id;
    public String name;
    public int salary;
}
