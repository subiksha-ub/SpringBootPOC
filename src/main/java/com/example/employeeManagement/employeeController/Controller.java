package com.example.employeeManagement.employeeController;
import com.example.employeeManagement.CacheConfig.RedisConfig;
import com.example.employeeManagement.Services.Countries;
import com.example.employeeManagement.Services.Services;
import com.example.employeeManagement.Services.Servicesdto;
import com.example.employeeManagement.employeeException.EmployeeNotFoundException;
import com.example.employeeManagement.employeeModel.Employee;
import com.example.employeeManagement.employeeRepository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/poc/api/employee-management/")
public class Controller {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<?> createEmployees(@Valid @RequestBody Employee employee) {
        Date currentDate = new Date();
//        String code = redisConfig.fetchFromRedis(employee.getCountryCode());
//        employee.setCountryCode(code);
//        employee.setCountryCode(fetchIso.externalApi(employee.getCountryCode()).toString());
        employee.setCreatedDate(currentDate);
        employeeRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public List<Employee> fetchEmployees() {
        return employeeRepository.findAll();
    }

    @PutMapping("/empId/{id}")
    public Employee updateEmployees(@PathVariable("id") long id, @RequestBody Employee employee) {
        if (employeeRepository.findById(id).isEmpty())
            throw new EmployeeNotFoundException("Cannot find employee");
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow();
        Date currentDate = new Date();
        updateEmployee.setEmployeeId(id);
        updateEmployee.setFirstName(employee.getFirstName());
        updateEmployee.setLastName((employee.getLastName()));
        updateEmployee.setEmail(employee.getEmail());
        updateEmployee.setDepartmentId(employee.getDepartmentId());
        updateEmployee.setPhoneNumber(employee.getPhoneNumber());
        updateEmployee.setCreatedDate(updateEmployee.getCreatedDate());
        updateEmployee.setUpdatedDate(currentDate);
        return employeeRepository.save(updateEmployee);
    }

    @GetMapping("/empId/{id}")
    public Optional<Employee> findEmployeeByEmpId(@PathVariable("id") long id) {
        if (employeeRepository.findById(id).isEmpty())
            throw new EmployeeNotFoundException("Cannot find employee");
        return employeeRepository.findById(id);
    }

    @DeleteMapping("/empId/{id}")
    public ResponseEntity<Employee> deleteEmployeeByEmpId(@PathVariable("id") long id) {
        if (employeeRepository.findById(id).isEmpty())
            throw new EmployeeNotFoundException("Cannot find employee");
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchName/{search}")
    public List<Employee> findEmployeeByName(@PathVariable("search") String search) {
        return employeeRepository.searchByName(search);
    }

    @GetMapping("/searchDeptId/{id}")
    public List<Employee> findEmployeeByDeptId(@PathVariable("id") long id) {
        if (employeeRepository.findById(id).isEmpty())
            throw new EmployeeNotFoundException("Cannot find employee");
        return employeeRepository.searchByDepartment(id);
    }

    @GetMapping("external")
    public void external(){
        //Services service = new Services();
        //RedisConfig redisConfig = new RedisConfig();
        //service.callExternalApi();

        String url = "https://countriesnow.space/api/v0.1/countries/iso";

        Servicesdto data = WebClient.create().get()
                .uri(url)
                .retrieve()
                .bodyToMono(Servicesdto.class)
                .block();

        for(Countries countries: data.getData()){
            System.out.println("key : " + countries.getName() + " :: value : " + countries.getIso2());
        }

    }
//@GetMapping("ISO/{name}")
//    public String externalApi(@PathVariable("name") String name) {
//        Services service = new Services();
//        String variable = service.callExternalApi()
//                .map(Servicesdto::getData)
//                .flatMapMany(Flux::fromIterable)
//                .map(obj -> (Map<?, ?>) obj)
//                .filter(map -> name.equalsIgnoreCase((String)map.get("name")))
//                .map(map -> (String) map.get("Iso3"))
//                .single()
//                .block();
//
//    return variable;
//
//    }
}

//redis