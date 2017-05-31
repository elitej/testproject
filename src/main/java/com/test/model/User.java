package com.test.model;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "users")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @NotBlank(message = "Field login can't be blank")
    @Column(name = "user_login")
    private String login;

    @NotBlank(message = "Field password can't be blank")
    @Column(name = "user_password", unique = true)
    private String password;

    @Email
    @Column(name = "user_email", unique = true)
    private String email;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;


    public User(Long id, String login, String password, String email, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
