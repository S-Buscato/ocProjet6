package com.paymybuddy.paymybuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="emmeter")
    private Users emmeter;

    @OneToOne
    @JoinColumn(name="receiver")
    private Users receiver;

    private double amount;
    private double fee;
    private String description;

}
