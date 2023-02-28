package jp.co.axa.apidemo.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity Class<BR>
 * Employee Entity represents name, salary and department
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name="DEPARTMENT")
    private String department;

}
