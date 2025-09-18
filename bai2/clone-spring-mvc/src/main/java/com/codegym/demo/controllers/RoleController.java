package com.codegym.demo.controllers;

import com.codegym.demo.dto.RoleDTO;
import com.codegym.demo.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "role/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("role", new RoleDTO());
        return "role/create";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        RoleDTO role = roleService.getById(id);
        if (role == null) return "redirect:/admin/roles";
        model.addAttribute("role", role);
        return "role/edit";
    }

    @PostMapping("/store")
    public String storeRole(@ModelAttribute("role") RoleDTO roleDTO) {
        roleService.createRole(roleDTO);
        return "redirect:/admin/roles";
    }


    @PostMapping("/{id}/update")
    public String updateRole(@PathVariable("id") Long id, @ModelAttribute("role") RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return "redirect:/admin/roles";
    }

    @GetMapping("/{id}/delete")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return "redirect:/admin/roles";
    }
}
