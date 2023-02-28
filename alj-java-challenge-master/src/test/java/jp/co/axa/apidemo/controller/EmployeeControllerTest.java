package jp.co.axa.apidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * EmployeeControllerTest Class<BR>
 * EmployeeControllerTest class test the APIs with different test cases.
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * JUnit Test for save employee method to create employee REST API
     */
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
        Employee employee = Employee.builder()
                .name("Sachin")
                .salary(20000)
                .department("software")
                .build();
        given(employeeService.saveOrUpdateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(employee.getName())))
                .andExpect(jsonPath("$.salary",
                        is(employee.getSalary())))
                .andExpect(jsonPath("$.department",
                        is(employee.getDepartment())));

    }


    /**
     * JUnit test for Get All employees REST API
     */
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().name("Sachin").salary(20000).department("software").build());
        listOfEmployees.add(Employee.builder().name("Kumar").salary(15000).department("accounts").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    /**
     * JUnit test for GET employee by id REST API
     * positive scenario - valid employee id
     */
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .name("Sachin")
                .salary(20000)
                .department("software")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())));

    }

    /**
     * JUnit test for GET employee by id REST API
     * negative scenario - valid employee id
     */
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .name("Sachin")
                .salary(20000)
                .department("software")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    /**
     * JUnit test for update employee REST API - positive scenario
     */
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .name("Sachin")
                .salary(20000)
                .department("software")
                .build();

        Employee sourceEmployee = Employee.builder()
                .name("Ram")
                .salary(25000)
                .department("accounts")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.saveOrUpdateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sourceEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(sourceEmployee.getName())))
                .andExpect(jsonPath("$.salary", is(sourceEmployee.getSalary())))
                .andExpect(jsonPath("$.department", is(sourceEmployee.getDepartment())));
    }

    /**
     * JUnit test for update employee REST API - negative scenario
     */
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;

        Employee sourceEmployee = Employee.builder()
                .name("Ram")
                .salary(25000)
                .department("accounts")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.saveOrUpdateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sourceEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    /**
     * JUnit test for delete employee REST API
     */
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{employeeId}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
