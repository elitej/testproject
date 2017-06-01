package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Pattern(regexp = "^(ADMIN|USER)$", message = "The user role can be either \"ADMIN\" or \"USER\"")
    private String name;

}
