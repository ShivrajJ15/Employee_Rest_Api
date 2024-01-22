package com.shiv.controller;

import com.shiv.entity.Employee;
import com.shiv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
public class StudentController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public String createNewEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return "Employee Succesfully saved in Database";

    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> empList=new ArrayList<>();
        employeeRepository.findAll().forEach(empList::add);
        return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);


    }
    @GetMapping("/employees/{emp_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long emp_id){
        Optional<Employee> emp= employeeRepository.findById(emp_id);
        if(emp.isPresent()) {
            return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);

        }
        else {

            return new ResponseEntity<Employee>(emp.get(),HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/employees/{emp_id}")
    public String updateEmployeeByID(@PathVariable Long emp_id,@RequestBody Employee employee) {
        Optional<Employee>emp= employeeRepository.findById(emp_id);
        if(emp.isPresent()) {
            Employee existEmp=emp.get();
            existEmp.setEmp_name(employee.getEmp_name());
            existEmp.setEmp_city(employee.getEmp_city());

            employeeRepository.save(existEmp);
            return "Employee Details aginst ID :" +emp_id+" Updated";

        }
        else {
            return "Employee details does not exist for employee ID : " +emp_id;
        }
    }
    @DeleteMapping("/employees/{emp_id}")
    public String deleteEmployeeByID(@PathVariable Long emp_id) {
        Optional<Employee>emp= employeeRepository.findById(emp_id);
        if(emp.isPresent()) {
            employeeRepository.deleteById(emp_id);
            return "Employee By this ID: "+emp_id+" Deleted successfully !!!";}
        else {
            return "The employee By requested ID does not exixts:";
        }
    }
}
