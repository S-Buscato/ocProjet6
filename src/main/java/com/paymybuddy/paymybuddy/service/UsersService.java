package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.controller.UsersController;
import com.paymybuddy.paymybuddy.dto.UserSubscribeOkDTO;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersMinimalsInfoDTO;
import com.paymybuddy.paymybuddy.dto.UsersSubscribeDTO;
import com.paymybuddy.paymybuddy.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.mapper.UsersSubscribeMApper;
import com.paymybuddy.paymybuddy.mapper.UsersSubscribeOkMapper;
import com.paymybuddy.paymybuddy.exception.ExistingEmailException;
import com.paymybuddy.paymybuddy.exception.UserAllReadyExistException;
import com.paymybuddy.paymybuddy.exception.UsersNotInFriendsListException;
import com.paymybuddy.paymybuddy.exception.UsersNotFoundException;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IUsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService implements IUsersService {

    static Logger logger = Logger.getLogger(UsersService.class);

    @Autowired
    UsersRepository usersRepository;

    @Override
    @Transactional
    public List<UsersDTO> findall() {
        List<Users> usersList = StreamSupport.stream(usersRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
        logger.debug("findAll");
        List<UsersDTO> usersDTOList = UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersList);
        return usersDTOList;
    }

    @Override
    @Transactional
    public UsersDTO findUsersFriends(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            logger.debug("findUsersFriends : " + id);
            return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.findById(id).get());
        }{
            logger.error("findUsersFriends error not found : " + id);
            throw new UsersNotFoundException();
        }
    }

    @Override
    @Transactional
    public UsersDTO findUserInfo(String email) throws UsersNotFoundException {
        if(usersRepository.findByEmail(email).isPresent()){
            logger.debug("users findUserInfo : " + email);
            return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.findByEmail(email).get());
        }
        else{
            logger.error("findUserInfo error not found : " + email);
            throw new UsersNotFoundException();
        }
    }


    @Override
    @Transactional
    public Users findById(Long id) {
        logger.debug("findById " + id);
        return usersRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Long deleteById(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            logger.debug("findDeleteId " + id);
            usersRepository.deleteById(id);
            return id;
        }else{
            logger.error("deleteById error not found : " +id);
            throw new UsersNotFoundException();
        }
    }

    @Override
    public UsersDTO findCurrentUserInfo(Long id) throws UsersNotFoundException {
        if(usersRepository.findById(id).isPresent()){
            logger.debug("findCurrentUserInfo : " +id);
            return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.findById(id).get());
        }
        else{
            logger.error("findCurrentUserInfo not found : " +id);
            throw new UsersNotFoundException();
        }
    }

    @Override
    @Transactional
    public Users save(Users users) {
        logger.debug("save : " + users);
        return usersRepository.save(users);
    }

    @Override
    @Transactional
    public UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException {
        if(!usersRepository.findByEmail(usersSubscribeDTO.getEmail()).isPresent()){
            Users users = UsersSubscribeMApper.INSTANCE.convertUsersSubscribeDTOToUsers(usersSubscribeDTO);
            users.setTotalAmount(0.0);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            users.setPassword(encoder.encode(users.getPassword()));
            logger.debug("subscribe success : " + usersSubscribeDTO);
            return UsersSubscribeOkMapper.INSTANCE.convertUsersToUserSubscribeOkDTO(usersRepository.save(users));
        }else{
            logger.error("subscribe error : " + usersSubscribeDTO.getEmail());
            throw new ExistingEmailException();
        }
    }


    @Override
    @Transactional
    public UsersMinimalsInfoDTO  addFriends(Long userId, String email) throws UserAllReadyExistException, UsersNotFoundException {
        Users users = findById(userId);
        if (usersRepository.findByEmail(email).isPresent()) {
            Users userFriends = usersRepository.findByEmail(email).get();
            if (!users.getFriends().contains(userFriends) && !userFriends.getFriends().contains(users)) {
                users.getFriends().add(userFriends);
                userFriends.getFriends().add(users);
                usersRepository.save(userFriends);
                logger.debug("addFriends : " + email);
                return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.save(users));
            } else {
                logger.error("addFriends error existing friend : " + email);
                throw new UserAllReadyExistException();
            }
        } else {
            logger.error("addFriends error not found: " + email);
            throw new UsersNotFoundException();
        }
    }


    @Override
    @Transactional
    public UsersDTO removeFriends(Long userId, String email) throws UsersNotFoundException, UsersNotInFriendsListException {
        Users users = usersRepository.findById(userId).get();
        if(usersRepository.findByEmail(email).isPresent()){
            logger.error("removeFriends : " + email);
            Users usersfriends = usersRepository.findByEmail(email).get();
            if(users.getFriends().contains(usersfriends) && usersfriends.getFriends().contains(users)){
                users.getFriends().remove(usersfriends);
                usersfriends.getFriends().remove(users);
                logger.debug("removeFriends : " + email);
                return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.save(users));
            }else{
                logger.error("removeFriends : " + email);
                throw new UsersNotInFriendsListException();
            }
        }else{
            logger.error("removeFriends : " + email);
            throw new UsersNotFoundException();
        }
    }


    @Override
    @Transactional
    public Optional<Users> findByEmail(String email){
        logger.debug("findByEmail : " + email);
        return usersRepository.findByEmail(email);
    };

    @Override
    @Transactional
    public UsersDTO update(UsersDTO usersDTO, Long id) throws UsersNotFoundException {
       if(usersRepository.findById(id).isPresent()){
           logger.debug("update : " + id);
           Users users = usersRepository.findById(id).get();
           return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.save(users));
        }else{
           logger.error("update : " + id);
           throw new UsersNotFoundException();
       }
    }


}
