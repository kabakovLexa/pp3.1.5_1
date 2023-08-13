package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private RoleService roleService;
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getAllShowUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
//        model.addAttribute("allRoles", roleService.allRoles());
        return "allUsers";
    }

    @PostMapping("/search")
    public String searchUserId(@RequestParam Long id, Model model) {
        User user = userService.getUserId(id);
        model.addAttribute("user", user);
        return "showUserId";
    }

    @GetMapping("/addUser")
    public String newPerson(@ModelAttribute("user") User user) {

        return "/addUser";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserId(id));
        model.addAttribute("roles", roleService.allRoles());
        return "edit";
    }

    @PutMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "/edit";
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
