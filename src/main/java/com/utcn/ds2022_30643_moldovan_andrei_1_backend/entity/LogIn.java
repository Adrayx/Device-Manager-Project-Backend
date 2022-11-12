package com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogIn {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, unique = true)
    private String token;

    @ManyToOne
    private User user;

    public LogIn(String token, User user){
        this.token = token;
        this.user = user;
    }
}
