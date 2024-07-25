package com.spring_security_jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private String roleName;

    private String roleDescription;

}
