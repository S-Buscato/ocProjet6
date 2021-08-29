package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.constant.TransfertType;
import com.paymybuddy.paymybuddy.controller.TransfertController;
import com.paymybuddy.paymybuddy.dto.BankTransfertDTO;
import com.paymybuddy.paymybuddy.dto.TransfertDTO;
import com.paymybuddy.paymybuddy.mapper.TransfertMapper;
import com.paymybuddy.paymybuddy.exception.BadTransfertTypeException;
import com.paymybuddy.paymybuddy.exception.InsuffisientBalanceException;
import com.paymybuddy.paymybuddy.exception.NotActiveBankAccountException;
import com.paymybuddy.paymybuddy.models.BankAccount;
import com.paymybuddy.paymybuddy.models.Transfert;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.paymybuddy.repository.TransfertRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.ITransfertService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransfertService implements ITransfertService {

    @Autowired
    TransfertRepository transfertRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    static Logger logger = Logger.getLogger(TransfertService.class);


    @Override
    public TransfertDTO receiveMoneyFromBankAccount(BankTransfertDTO bankTransfertDTO) throws NotActiveBankAccountException, BadTransfertTypeException {
        if(!bankTransfertDTO.getTransfertType().equals((TransfertType.VIR_FROM_BANK_ACCOUNT).toString())){
            logger.error("receiveMoneyFromBankAccount");
            throw  new BadTransfertTypeException();
        }
        Transfert transfert = new Transfert();
        Users users = usersRepository.findByEmail(bankTransfertDTO.getUserEmail()).get();
        BankAccount bankAccount = bankAccountRepository.findByIban(bankTransfertDTO.getIban());
        if(bankAccount.isActif()){
            setTransfert(bankTransfertDTO, transfert, users, bankAccount);
            try{
                transfertRepository.save(transfert);
                users.setTotalAmount(users.getTotalAmount() + transfert.getAmount());
                logger.info("receiveMoneyFromBankAccount : " + bankTransfertDTO.getAmount());
                usersRepository.save(users);
            }catch (Exception e){
                logger.error("receiveMoneyFromBankAccount " );
                throw e;
            }
        }else{
            logger.error("receiveMoneyFromBankAccount " );
            throw new NotActiveBankAccountException();
        }
        return TransfertMapper.INSTANCE.convertTransfertToTransfertDTO(transfert);
    }

    @Override
    public TransfertDTO sendMoneyToBankAccount(BankTransfertDTO bankTransfertDTO)
        throws NotActiveBankAccountException, InsuffisientBalanceException, BadTransfertTypeException {
        logger.info("sendMoneyToBankAccount : " + bankTransfertDTO.getAmount());
        if(!bankTransfertDTO.getTransfertType().equals((TransfertType.VIR_TO_BANK_ACCOUNT).toString())){
            logger.error("receiveMoneyFromBankAccount " );
            throw  new BadTransfertTypeException();
        }
        Transfert transfert = new Transfert();
        Users users = usersRepository.findByEmail(bankTransfertDTO.getUserEmail()).get();
        BankAccount bankAccount = bankAccountRepository.findByIban(bankTransfertDTO.getIban());
        if(users.getTotalAmount() - bankTransfertDTO.getAmount() >= 0){
            if(bankAccount.isActif()){
                setTransfert(bankTransfertDTO, transfert, users, bankAccount);
                try{
                    transfertRepository.save(transfert);
                    users.setTotalAmount(users.getTotalAmount() - transfert.getAmount());
                    usersRepository.save(users);
                }catch (Exception e){
                    logger.error("receiveMoneyFromBankAccount " );
                    throw e;
                }
            }else{
                logger.error("receiveMoneyFromBankAccount " );
                throw new NotActiveBankAccountException();
            }
        }
        return TransfertMapper.INSTANCE.convertTransfertToTransfertDTO(transfert);
    }

    @Override
    public List<Transfert> findAllByUsersId(long usersId) {
        logger.info("findAllByUsersId : " + usersId);
        return transfertRepository.findAllByUsersId(usersId);
    }

    @Override
    public TransfertDTO findById(long id) {
        logger.info("findById : " + id);
        return TransfertMapper.INSTANCE.convertTransfertToTransfertDTO(transfertRepository.findById(id).get());
    }

    private void setTransfert(BankTransfertDTO bankTransfertDTO, Transfert transfert, Users users, BankAccount bankAccount) {
        logger.info("setTransfert");
        transfert.setDate(new Date());
        transfert.setUsers(users);
        transfert.setBankAccount(bankAccount);
        transfert.setTransfertType(bankTransfertDTO.getTransfertType());
        transfert.setAmount(bankTransfertDTO.getAmount());
        transfert.setBankAccount(bankAccount);
        transfert.setUsers(users);
    }

}
