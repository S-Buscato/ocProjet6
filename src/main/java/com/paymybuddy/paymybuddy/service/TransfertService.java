package com.paymybuddy.paymybuddy.service;

import com.google.gson.Gson;
import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.dto.Mapper.TransfertMapper;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.dto.UserTransfertMoneyDTO;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Transfert;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.TransfertRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.ITransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransfertService implements ITransfertService {

    @Autowired
    TransfertRepository transfertRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public Object receiveMoneyFromBankAccount(UserTransfertMoneyDTO userTransfertMoneyDTO){
        Object resp;

        Users users = usersRepository.findByEmail(userTransfertMoneyDTO.getEmail());
        BankAccount bankAccount = bankAccountRepository.findByIban(userTransfertMoneyDTO.getIban());
        double amount = userTransfertMoneyDTO.getAmount();

        Boolean sendToBank = userTransfertMoneyDTO.getTransfertType().equals(TransfertType.VIR_TO_BANK_ACCOUNT.toString());
        Boolean receiveFromBank = userTransfertMoneyDTO.getTransfertType().equals(TransfertType.VIR_FROM_BANK_ACCOUNT.toString());
        Transfert transfert = new Transfert();
        transfert.setTransfertType(userTransfertMoneyDTO.getTransfertType());
        transfert.setDate(new Date());
        transfert.setAmount(amount);
        transfert.setBankAccount(bankAccount);
        transfert.setUsers(users);

        if(sendToBank){
            if(users.getTotalAmount() >= amount){
                users.setTotalAmount(users.getTotalAmount() - transfert.getAmount());
            }else{
            return resp  =  new Gson().toJson("Le montant à tranférer est supérieur à votre solde");
            }
        }
        else{
            users.setTotalAmount(users.getTotalAmount() + transfert.getAmount());
        }
        usersRepository.save(users);
        resp  = TransfertMapper.INSTANCE.convertTransfertToTransfertDTO(transfertRepository.save(transfert));
        return resp;
    }

    /*@Override
    public Object sendMoneyToBankAccount(Long usersId, Long usersBankAccount, double amount){
        Object resp;
        Users users = usersRepository.findById(usersId).get();
        if(users.getTotalAmount() >= amount){
            users.setTotalAmount(users.getTotalAmount() - amount);
            BankAccount bankAccount = bankAccountRepository.findById(usersBankAccount).get();
            Transfert transfert = new Transfert();
            transfert.setTransfertType(TransfertType.VIR_TO_BANK_ACCOUNT.toString());
            transfert.setDate(new Date());
            transfert.setAmount(amount);
            transfert.setBankAccount(bankAccount);
            transfert.setUsers(users);
            usersRepository.save(users);
            resp = TransfertMapper.INSTANCE.convertTransfertToTransfertDTO(transfertRepository.save(transfert));
        }else{
            resp  =  new Gson().toJson("Le montant à tranférer est supérieur à votre solde");
        }

        return resp;
    }*/
}
