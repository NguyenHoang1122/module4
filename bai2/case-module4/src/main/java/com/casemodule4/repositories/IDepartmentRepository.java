package com.casemodule4.repositories;

import com.casemodule4.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    // Additional query methods can be defined here if needed
}
