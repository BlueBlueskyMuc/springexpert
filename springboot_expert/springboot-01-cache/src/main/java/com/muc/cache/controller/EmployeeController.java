package com.muc.cache.controller;

import com.muc.cache.pojo.Employee;
import com.muc.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmpById(id);
        return employee;
    }

    @GetMapping("/emp")
    public Employee updateEmployee(Employee employee){
        Employee emp = employeeService.updateEmp(employee);
        return emp;
    }

    @GetMapping("/delemp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastName/{lastName}")
    public Employee selectEmployeeByLastName(@PathVariable("lastName") String lastName){
        return employeeService.selectEmpByLastName(lastName);
    }
}
