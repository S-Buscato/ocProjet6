package com.paymybuddy.paymybuddy.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="users_friends")
public class UsersFriends {

    //@Column(name="users_friends_id")
    private Users usersFriendsId;

/*    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))*/
    private Users usersId;
}
