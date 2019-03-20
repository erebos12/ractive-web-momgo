package com.example.reactivemongo.rest;


import com.example.reactivemongo.infrastructure.ReactiveEmployeeRepository;
import com.example.reactivemongo.domain.Employee;
import com.example.reactivemongo.infrastructure.PlainEmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/")
public class EmployeeResource {

    // reactive DB driver
    private ReactiveEmployeeRepository reactiveEmployeeRepository;
    // plain DB driver
    private PlainEmployeeRepository plainEmployeeRepository;

    public EmployeeResource(ReactiveEmployeeRepository reactiveEmployeeRepository, PlainEmployeeRepository plainEmployeeRepository) {
        this.reactiveEmployeeRepository = reactiveEmployeeRepository;
        this.plainEmployeeRepository = plainEmployeeRepository;
    }

    @GetMapping()
    public String sayHello() {
        return "Hello, I'm alive";
    }

    // sends a static list
    @GetMapping(value = "/plain/employees")
    public List<Employee> getAllPlain() {
        List<Employee> list = plainEmployeeRepository.findAll();
        return list;
    }

    // libe-connection = keeps the connection open for new events coming
    @GetMapping(value = "/reactive/employees", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> getAll() {
        return reactiveEmployeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Mono<Employee> getById(@PathVariable("id") final String id) {
        return reactiveEmployeeRepository.findById(id);
    }

    @DeleteMapping("/employees")
    public String delete() {
        reactiveEmployeeRepository.deleteAll().subscribe();
        return "Deleted all";
    }

    @PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String post(@RequestBody Employee employee) {
        reactiveEmployeeRepository.save(employee).subscribe();
        return "OK";
    }

    @PostMapping(value = "/save/employees")
    public void save(@RequestBody Mono<Employee> employeeMono) {
        employeeMono.subscribe(reactiveEmployeeRepository::save);
    }

    @PostMapping(value = "/save/employee/all")
    public void saveAll(Flux<Employee> employees) {
        reactiveEmployeeRepository.saveAll(employees);
    }

    // live-connection = client can keep pushing new values at any point in time
    @PostMapping(value = "/save/employees", consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public void save(Flux<Employee> employees) {
        reactiveEmployeeRepository.saveAll(employees);
    }
}

