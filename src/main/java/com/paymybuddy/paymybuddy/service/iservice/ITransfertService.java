package com.paymybuddy.paymybuddy.service.iservice;

import com.paymybuddy.paymybuddy.dto.UserTransfertMoneyDTO;

public interface ITransfertService {

    Object receiveMoneyFromBankAccount(UserTransfertMoneyDTO userTransfertMoneyDTO);

}
