package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminByUsername(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("user", userService.getUserByUsername(userDetails.getUsername()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/edit")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
