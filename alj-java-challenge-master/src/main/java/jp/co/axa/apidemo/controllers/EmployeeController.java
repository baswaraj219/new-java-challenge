package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController Class<BR>
 * Serves for Employee create, update, delete, get, getAll
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@Slf4j
@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * To get all employees data
     *
     * @return List<Employee> - list of employee data as response
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("EmployeeController: getAllEmployees");
        return employeeService.getAllEmployees();
    }

    /**
     * employee search by id
     *
     * @param employeeId - employee id
     * @return ResponseEntity<Employee> - employee data as search result
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(name="employeeId")Long employeeId) {
        log.info("EmployeeController: getEmployeeById");
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * saves employee
     *
     * @param employee - employee data to save or update
     * @return Employee - saved or updated employee data as response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        log.info("EmployeeController: createEmployee");
        Employee savedEmployee = employeeService.saveOrUpdateEmployee(employee);
        System.out.println("Employee Saved Successfully");
        return savedEmployee;
    }

    /**
     * To delete employee of passed employee id.
     *
     * @param employeeId - employee Id
     * @return ResponseEntity<String> - response message
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        log.info("EmployeeController: deleteEmployee");
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");
        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
    }

    /**
     * To update employee of passed employee id.
     *
     * @param employeeId - employee Id
     * @return ResponseEntity<String> - response message
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        log.info("EmployeeController: updateEmployee");
        return employeeService.getEmployeeById(employeeId)
                .map(fetchedEmployee -> {

                    fetchedEmployee.setName(employee.getName());
                    fetchedEmployee.setSalary(employee.getSalary());
                    fetchedEmployee.setDepartment(employee.getDepartment());

                    Employee updatedEmployee = employeeService.saveOrUpdateEmployee(fetchedEmployee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
