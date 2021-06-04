package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.controller.UsersController;
import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.dto.mapper.UsersSubscribeMApper;
import com.paymybuddy.paymybuddy.dto.mapper.UsersSubscribeOkMapper;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IUsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService implements IUsersService {

    static Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<UsersDTO> findall() {
        List<Users> usersList = StreamSupport.stream(usersRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
        logger.info("users findAll");
        List<UsersDTO> usersDTOList = UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersList);
        return usersDTOList;
    }

    @Override
    public UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            logger.info("Find user Friend");
            return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
        }{
            throw new UsersNotFoundException();
        }
    }

    @Override
    public UsersDTO findUserInfo(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            UsersMinimalsInfoDTO usersMinimalsInfoDTO = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
            logger.info("users findAllFriends");
            return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.findById(id).get());
        }
        else{
            throw new UsersNotFoundException();
        }
    }


    @Override
    public Users findById(Long id) {
        logger.info("find by id " + id);
        return usersRepository.findById(id).get();
    }

    @Override
    public Long deleteById(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            usersRepository.deleteById(id);
            return id;
        }else{
            throw new UsersNotFoundException();
        }
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException {
        if(usersSubscribeDTO.getEmail() != null){
            Users users = UsersSubscribeMApper.INSTANCE.convertUsersSubscribeDTOToUsers(usersSubscribeDTO);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            users.setEmail(encoder.encode(users.getEmail()));
            return UsersSubscribeOkMapper.INSTANCE.convertUsersToUserSubscribeOkDTO(usersRepository.save(users));
        }else{
            throw new ExistingEmailException();
        }
    }



    @Override
    public UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UserAllReadyExistException, UsersNotFoundException {
        Users users = findById(userId);
        if (usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).isPresent()) {
            Users userFriends = usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).get();
            if (!users.getFriends().contains(userFriends)) {
                users.getFriends().add(userFriends);
                userFriends.getFriends().add(users);
                usersRepository.save(userFriends);
                return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.save(users));
            } else {
                throw new UserAllReadyExistException();
            }
        } else {
            throw new UsersNotFoundException();
        }
    }


    @Override
    public UsersMinimalsInfoDTO removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException {
        Users users = usersRepository.findById(userId).get();
        if(usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).isPresent()){
            Users usersfriends = usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).get();
                users.getFriends().remove(usersfriends);
                return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(users);
        }else{
            throw new UsersNotFoundException();
        }
    }


    @Override
    public Optional<Users> findByEmail(String email){
        return usersRepository.findByEmail(email);
    };

    @Override
    public UsersDTO update(UsersDTO usersDTO, Long id) throws UsersNotFoundException {
       if(usersRepository.findById(id).isPresent()){
           Users users = usersRepository.findById(id).get();
           return UsersMapper.INSTANCE.convertUsersToUsersDTO(users);
        }else{
           throw new UsersNotFoundException();
       }
    }


}
