package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/user")
    public String pageForUser (Model model, Principal principal) {
        model.addAttribute("user",userService.getUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/")
    public String getAllShowUsers(Model model,Principal principal) {
        List<Role> roles = roleService.allRoles();
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("roles", roles);
        model.addAttribute("user",userService.getUserByUsername(principal.getName()));
        model.addAttribute("currentUser", userService.getUserByUsername(principal.getName()));
        return "allUsers";
    }

    @GetMapping("/addUser")
    public String newPerson(@ModelAttribute("user") User user,Model model,Principal principal) {
        model.addAttribute("user",userService.getUserByUsername(principal.getName()));

        model.addAttribute("roles", roleService.allRoles());

        return "/addUser";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<Long> roleIds) {
        user.setRoles(roleService.getRolesByIdIn(roleIds));
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(ModelMap model, @PathVariable("id") Long id,@RequestParam("roles") Set<Long> roleIds) {
        model.addAttribute("user", userService.getUserId(id));
        model.addAttribute("roles", roleService.allRoles());
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/";
        }

//        if (user.getPassword() == null) {
//            user.setPassword("root");
//        }
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}
