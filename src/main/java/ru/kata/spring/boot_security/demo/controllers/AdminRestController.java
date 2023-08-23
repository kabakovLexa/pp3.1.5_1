package ru.kata.spring.boot_security.demo.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    private User removeHibernateProxy(User user) {
        if (user instanceof org.hibernate.proxy.HibernateProxy) {
            user = (User) Hibernate.unproxy(user);
        }
        return user;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable Long id) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getUserRoles() {

        return ResponseEntity.ok(roleService.allRoles());
    }

}