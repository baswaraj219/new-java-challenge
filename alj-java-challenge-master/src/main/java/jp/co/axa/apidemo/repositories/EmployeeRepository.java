package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface<BR>
 * Employee repository to handle crud operations using JpaRepository.
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
