package com.demospringboot.controllers;

import com.demospringboot.dto.DepartmentDTO;
import com.demospringboot.services.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Danh sách
    @GetMapping("")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments/list";
    }

    // Hiển thị form create
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        return "departments/create";
    }

    // Lưu mới
    @PostMapping("/store")
    public String storeDepartment(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.addDepartment(departmentDTO);
        return "redirect:/admin/department";
    }

    // Hiển thị form edit
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "departments/edit";
    }

    // Cập nhật
    @PostMapping("/{id}/update")
    public String updateDepartment(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.updateDepartment(departmentDTO);
        return "redirect:/admin/department";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return "redirect:/admin/department";
    }
}
