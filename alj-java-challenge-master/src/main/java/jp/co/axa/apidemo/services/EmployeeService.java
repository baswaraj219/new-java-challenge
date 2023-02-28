package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeService Interface<BR>
 * Serves for Employee save, update, delete, get, getAll
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Optional<Employee> getEmployeeById(Long employeeId);

    public Employee saveOrUpdateEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);
}