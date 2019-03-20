package com.example.reactivemongo.infrastructure;

import com.example.reactivemongo.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlainEmployeeRepository extends MongoRepository<Employee, String> {
}

