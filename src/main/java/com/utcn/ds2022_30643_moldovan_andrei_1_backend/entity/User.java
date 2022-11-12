package com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean userType;

    @OneToMany
    @ToString.Exclude
    private List<EnergyDevice> devices;

    public void addDevice(EnergyDevice device){
        devices.add(device);
    }

    public void removeDevice(EnergyDevice device){
        devices.remove(device);
    }

    public User(String username, String password, Boolean role){
        this.username = username;
        this.password = password;
        this.userType = role;
        this.devices = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
