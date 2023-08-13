package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleId(Long id) {
        return null;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public void updateRole(Role user) {

    }

    @Override
    public void deleteRole(Long id) {

    }

    @Override
    public Role getRoleByUsername(String username) {
        return null;
    }
}
