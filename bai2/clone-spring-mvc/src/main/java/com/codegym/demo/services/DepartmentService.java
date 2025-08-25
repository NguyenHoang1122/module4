package com.codegym.demo.services;

import com.codegym.demo.dto.DepartmentDTO;
import com.codegym.demo.models.Department;
import com.codegym.demo.repositories.IDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    private final IDepartmentRepository departmentRepository;

    public DepartmentService(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> list = new ArrayList<>();
        for (Department department : departments) {
            list.add(new DepartmentDTO(department.getId(), department.getName()));
        }
        return list;
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
        return new DepartmentDTO(department.getId(), department.getName());
    }

    public void addDepartment(DepartmentDTO departmentDTO) {
        if (departmentRepository.existsByName(departmentDTO.getName())) {
            throw new RuntimeException("Tên phòng ban đã tồn tại");
        }
        Department department = new Department();
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }

    public void updateDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(departmentDTO.getId())
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentDTO.getId()));
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
        departmentRepository.delete(department);
    }
}
