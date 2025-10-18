package com.example.springsecurity6tutorial.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleType; // e.g. ROLE_ADMIN or ROLE_USER


    public Role() {}

    public Role(Long id, String roleType) {
        this.id = id;
        this.roleType = roleType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
