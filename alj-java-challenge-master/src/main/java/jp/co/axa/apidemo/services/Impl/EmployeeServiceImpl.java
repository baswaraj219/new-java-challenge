package jp.co.axa.apidemo.services.Impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

/**
 * Service Class<BR>
 * Serves for Employee save, update, delete, get, getAll
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    // autowired employee repository.
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * To get all employees data
     *
     * @return List<Employee> - list of employee data as response
     */
    @Cacheable(value="employees")
    public List<Employee> getAllEmployees() {
        log.info("EmployeeServiceImpl: getAllEmployees");
        return employeeRepository.findAll();
    }

    /**
     * employee search by id
     *
     * @param employeeId - employee id
     * @return Optional<Employee> - employee data as search result
     */
    @Cacheable(value="employee")
    public Optional<Employee> getEmployeeById(Long employeeId) {
        log.info("EmployeeServiceImpl: getEmployeeById");
        return employeeRepository.findById(employeeId);
    }

    /**
     * saves or update employee
     *
     * @param employee - employee data to save or update
     * @return Employee - saved or updated employee data as response
     */
    @Caching(evict = {
            @CacheEvict(value="employee", allEntries=true),
            @CacheEvict(value="employees", allEntries=true)})
    public Employee saveOrUpdateEmployee(Employee employee){
        log.info("EmployeeServiceImpl: saveOrUpdateEmployee");
        return employeeRepository.save(employee);
    }

    /**
     * To delete employee of passed employee id.
     *
     * @param employeeId - employee Id
     * @return void - none
     */
    @Caching(evict = {
            @CacheEvict(value="employee", allEntries=true),
            @CacheEvict(value="employees", allEntries=true)})
    public void deleteEmployee(Long employeeId){
        log.info("EmployeeServiceImpl: deleteEmployee");
        employeeRepository.deleteById(employeeId);
    }
}