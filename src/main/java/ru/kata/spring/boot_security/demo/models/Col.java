package ru.kata.spring.boot_security.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

public class Col {
    private Long id;

    private String name;

    private Set<User> users;


}
