package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.constant.FeeRate;
import com.paymybuddy.paymybuddy.controller.UsersController;
import com.paymybuddy.paymybuddy.dto.UsersDTO;
import com.paymybuddy.paymybuddy.dto.UsersFriendsDTO;
import com.paymybuddy.paymybuddy.dto.mapper.UsersMapper;
import com.paymybuddy.paymybuddy.models.Users;
import com.paymybuddy.paymybuddy.repository.UsersRepository;
import com.paymybuddy.paymybuddy.service.iservice.IUsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService implements IUsersService {

    static Logger logger = Logger.getLogger(UsersController.class);

   /* @Autowired
    UsersMapper usersMapper;
*/
    @Autowired
    UsersRepository usersRepository;

    @Override
<<<<<<< Updated upstream
    public List<Users> findall() {
        List<Users> ret = StreamSupport.stream(usersRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
        logger.info("users findAll");
        return ret;
=======
    public List<UsersDTO> findall() {
        List<Users> ret = StreamSupport.stream(usersRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
        logger.info("users findAll");

        return UsersMapper.INSTANCE.convertUsersToUsersDTOList(ret);
    }

    @Override
    public UsersMinimalsInfoDTO findUsersFriends(Long id) throws UsersNotFoundException {
        UsersMinimalsInfoDTO usersFriendsDTO = new UsersMinimalsInfoDTO();
        if(usersRepository.findById(id).isPresent()){
            usersFriendsDTO = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
            logger.info("users findAllFriends");
        }else{
            throw  new UsersNotFoundException();
        }
        return usersFriendsDTO;
>>>>>>> Stashed changes
    }

    @Override
    public UsersFriendsDTO findUsersFriends(Long id) {
        UsersFriendsDTO usersFriendsDTO = new UsersFriendsDTO();
        if(usersRepository.findById(id).isPresent()){
<<<<<<< Updated upstream
            usersFriendsDTO = UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(usersRepository.findById(id).get());
           // usersFriendsDTO.setFriendsList(UsersMapper.INSTANCE.convertUsersToUsersDTOList(usersRepository.findById(id).get().getFriends()));
            logger.info("users findAllFriends");
=======
            return UsersMapper.INSTANCE.convertUsersToUsersDTO(usersRepository.findById(id).get());
        }
        else{
            throw new UsersNotFoundException();
>>>>>>> Stashed changes
        }
        return usersFriendsDTO;
    }


    @Override
    public Optional<Users> findById(Long id) {
        logger.info("find by id " + id);
        return usersRepository.findById(id);
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
    public Users save(UsersDTO usersDTO) {
        Users users = UsersMapper.INSTANCE.convertUsersDTOToUsers(usersDTO);
        return usersRepository.save(users);
    }

    @Override
<<<<<<< Updated upstream
    public Users addFriends(Long userId, Long usersFriendId){
        Users users = findById(userId).get();
        Users userFriends = findById(usersFriendId).get();
        List<Users> usersList = new ArrayList<>();
        if(users.getFriends().size() != 0){
            usersList = users.getFriends();
=======
    public UserSubscribeOkDTO subscribe(UsersSubscribeDTO usersSubscribeDTO) throws ExistingEmailException {
        if(usersSubscribeDTO.getEmail() != null){
            Users users = UsersSubscribeMApper.INSTANCE.convertUsersSubscribeDTOToUsers(usersSubscribeDTO);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            users.setEmail(encoder.encode(users.getEmail()));
            return UsersSubscribeOkMapper.INSTANCE.convertUsersToUserSubscribeOkDTO(usersRepository.save(users));
        }else{
            throw new ExistingEmailException();
>>>>>>> Stashed changes
        }
        usersList.add(userFriends);
        users.setFriends(usersList);
        return usersRepository.save(users);
    }


    @Override
<<<<<<< Updated upstream
    public Users removeFriends(Long userId, Long usersFriendId) {
        Users users = findById(userId).get();
        Users userFriends = findById(usersFriendId).get();
        List<Users> usersList = new ArrayList<>();
        if(users.getFriends().size() != 0){
            usersList=users.getFriends();
        }
        usersList.remove(userFriends);
        users.setFriends(usersList);
        return usersRepository.save(users);
    }

    @Override
    public Users sendMoneyToFriends(Long userId, Long usersFriendId, double amount) {
        Users users = findById(userId).get();
        Users userFriends = findById(usersFriendId).get();
        Double fee = amount * FeeRate.FEE_RATE;
        Double totalAmountTransfert = amount + fee;
        Boolean friends = users.getFriends().contains(userFriends);
        Boolean amountOk = users.getTotalAmount() >= totalAmountTransfert;
        if(friends && amountOk){
            users.setTotalAmount(users.getTotalAmount() - totalAmountTransfert);
            userFriends.setTotalAmount(userFriends.getTotalAmount() + amount);
            usersRepository.save(userFriends);
        }
        return usersRepository.save(users);
=======
    public UsersMinimalsInfoDTO addFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException, UserAllReadyExistException {
        Users users = findById(userId).get();
        if(usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).isPresent()){
            Users usersfriends = usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).get();
            if(!users.getFriends().contains(usersfriends)){
                users.getFriends().add(usersfriends);
                return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(users);
            }else{
                throw new UserAllReadyExistException();
            }
        }else{
            throw new UsersNotFoundException();
        }

>>>>>>> Stashed changes
    }

    @Override
    public UsersMinimalsInfoDTO removeFriends(Long userId, UsersMinimalsInfoDTO usersMinimalsInfoDTO) throws UsersNotFoundException {
        Users users = findById(userId).get();
        if(usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).isPresent()){
            Users usersfriends = usersRepository.findByEmail(usersMinimalsInfoDTO.getEmail()).get();
                users.getFriends().remove(usersfriends);
                return UsersMapper.INSTANCE.convertUsersToUsersFriendsDTO(users);
        }else{
            throw new UsersNotFoundException();
        }
    }

    @Override
    public Optional<Users> findByEmail(String s) {
        return Optional.empty();
    }

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
