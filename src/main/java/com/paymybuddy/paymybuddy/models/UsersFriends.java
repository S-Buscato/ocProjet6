package com.paymybuddy.paymybuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="users_friends")
public class UsersFriends {
    private Long usersFriendsId;
    private Long usersId;
}
