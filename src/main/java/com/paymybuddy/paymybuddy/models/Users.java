package com.paymybuddy.paymybuddy.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_friends",
            joinColumns = {
                    @JoinColumn(name = "users_friends_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "users_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private List<Users> friends;


    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Transfert> transferts;

    @OneToMany(mappedBy = "id")
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> ReceivedTransactions;

    @OneToMany(mappedBy = "emmeter")
    private List<Transaction> EmmetedTransactions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Users> getFriends() {
        return friends;
    }

    public void setFriends(List<Users> friends) {
        this.friends = friends;
    }

    public List<Transfert> getTransferts() {
        return transferts;
    }

    public void setTransferts(List<Transfert> transferts) {
        this.transferts = transferts;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<Transaction> getReceivedTransactions() {
        return ReceivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        ReceivedTransactions = receivedTransactions;
    }

    public List<Transaction> getEmmetedTransactions() {
        return EmmetedTransactions;
    }

    public void setEmmetedTransactions(List<Transaction> emmetedTransactions) {
        EmmetedTransactions = emmetedTransactions;
    }
}
