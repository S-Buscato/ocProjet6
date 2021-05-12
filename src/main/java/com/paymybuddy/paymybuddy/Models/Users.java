package com.paymybuddy.paymybuddy.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
    private String password;
    private String email;

    @Column(name="total_amount")
    private Double totalAmount;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "id", cascade = { CascadeType.REMOVE })
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_friends",
            joinColumns = {
                    @JoinColumn(name = "users_friends_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "users_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Users> friends;
}
