package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;


@Controller
public class UserController {

    private UserDAO userService;

    @Autowired
    public UserController(UserDAO userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllShowUsers(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
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

        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserId(id));
        return "edit";
    }

    @PutMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "/edit";
        userService.updateUser(user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

}
