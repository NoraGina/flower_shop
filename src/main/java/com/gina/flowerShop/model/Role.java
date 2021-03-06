package com.gina.flowerShop.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(name = "role_name")
    private String name;


    public Role() {
    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        if(idRole != null){
            return idRole.equals(role.idRole) &&
                    name.equals(role.name);
        }
        return
                name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + idRole +
                ", name='" + name + '\'' +
                '}';
    }
}
