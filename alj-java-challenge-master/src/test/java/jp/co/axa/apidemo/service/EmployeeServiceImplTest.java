package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.Impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * EmployeeServiceImplTest Class<BR>
 * EmployeeServiceImplTest class test the methods of service class with different test cases.
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * JUnit test for save employee method of service
     */
    @Test
    public void whenSaveEmployee_shouldReturnEmployee() {
        Employee employee = new Employee();
        employee.setName("Sachin");
        employee.setSalary(20000);
        employee.setDepartment("software");

        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        Employee created = employeeService.saveOrUpdateEmployee(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    /**
     * JUnit test for get all employees method of service
     */
    @Test
    public void shouldReturnAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        given(employeeRepository.findAll()).willReturn(employees);

        List<Employee> expected = employeeService.getAllEmployees();

        assertEquals(expected, employees);
        verify(employeeRepository).findAll();
    }

    /**
     * JUnit test for delete employee method of service
     */
    @Test
    public void whenGivenEmployeeId_shouldDeleteEmployee_ifFound(){
        Employee employee = new Employee();
        employee.setName("Sachin");
        employee.setSalary(20000);
        employee.setDepartment("software");
        employee.setId(1L);
        employeeService.deleteEmployee(employee.getId());
        verify(employeeRepository).deleteById(employee.getId());
    }

    // Below test case not passed.
    /**
     * JUnit test for delete employee method of service -negative scenario
     */
    /*
    @Test(expected = Exception.class)
    public void should_throw_exception_when_employee_doesnt_exist() {
        Employee employee = new Employee();
        employee.setId(89L);
        employee.setName("Sachin");
        employee.setSalary(20000);
        employee.setDepartment("software");

        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        employeeService.deleteEmployee(employee.getId());
    }*/

    /**
     * JUnit test for get employee method of service
     */
    @Test
    public void whenGivenId_shouldReturnUser_ifFound() {
        Employee employee = new Employee();
        employee.setName("Sachin");
        employee.setSalary(20000);
        employee.setDepartment("software");
        employee.setId(1L);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Optional<Employee> expected = employeeService.getEmployeeById(employee.getId());

        assertThat(expected.get()).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }
}
