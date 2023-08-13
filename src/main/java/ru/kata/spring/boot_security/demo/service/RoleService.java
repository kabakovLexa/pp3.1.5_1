package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> allRoles();
    public Role getRoleId(Long id);
    public void addRole(Role role);
    public void updateRole(Role user);
    public void deleteRole(Long id);
    public Role getRoleByUsername(String username);
}
