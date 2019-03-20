package com.example.reactivemongo.infrastructure;

import com.example.reactivemongo.domain.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveEmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
