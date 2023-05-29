package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repo.GroupRepository;
import com.example.demo.repo.UserGroupRepository;
import com.example.demo.repo.UserRepository;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String combination = upper + lower + num;
        int len = 8;
        String password = new String();
        Random r = new Random();
        for(int i = 0; i < len; i++) {
            password += combination.charAt(r.nextInt(combination.length()));
        }
        return password;
    }

    public void emailValidation(String email) {
        if(!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) throw new RuntimeException("Email is not valid!");
    }


    public void checkIfMailExists(String email) {
        if(userRepository.findUserByEmail(email) == null) throw new RuntimeException("User with this email doesn't exist!");
    }

    public void roleValidation(Integer role) {
        if(role != 1 && role != 2 && role != 3) throw new RuntimeException("This role doesn't exist!");
    }
    public void idValidation(Integer id) {
        if(userRepository.findById(id).isEmpty()) throw new RuntimeException("This user doesn't exist!");
    }
    public void addUser(AdminAddUser user) {//PROVJERITI AKO MAIL VEC POSTOJI, AKO JE ISPRAVAN MAIL, AKO ROLA POSTOJI
        emailValidation(user.getEmail());
        roleValidation(user.getRole());
        String pass = generatePassword();
        User newUser = new User(user.getUsername(), user.getRole(), user.getEmail());
        senderService.sendEmail(user.getEmail(), pass, "NEW PASSWORD");
        newUser.setPassword(bCryptPasswordEncoder.encode(pass));
        newUser.setActive(true);
        userRepository.save(newUser);
    }

    public List<UserData> getUsers() {
        ModelMapper modelMapper = new ModelMapper();

        var result = new ArrayList<UserData>();
        var users =  userRepository.findAll();
        List<UserData> userDataList = modelMapper.map(users, new TypeToken<List<UserData>>() {}.getType());

        return userDataList;
    }

    public void deleteUser(Integer id) {
        idValidation(id);
        userGroupRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    public void updateUser(AdminUserUpdate userUpdate, Integer id) {
        idValidation(id);
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isPresent()) {
            emailValidation(userUpdate.getEmail());
            roleValidation(userUpdate.getRole());
            foundUser.get().setEmail(userUpdate.getEmail());
            foundUser.get().setUsername(userUpdate.getUsername());
            foundUser.get().setPassword(userUpdate.getPassword());
            foundUser.get().setRole(userUpdate.getRole());
            foundUser.get().setActive(userUpdate.getActive());
            userRepository.save(foundUser.get());
        }
    }

    public void updateLoginUser(UserUpdate userUpdate, Integer id) {
         idValidation(id);
         emailValidation(userUpdate.getEmail());
         Optional<User> opt = userRepository.findById(id);
         if(opt.isPresent()) {
             opt.get().setEmail(userUpdate.getEmail());
             opt.get().setPassword(userUpdate.getPassword());
             userRepository.save(opt.get());
         }
    }

    public void resetPassword(String email) {
        checkIfMailExists(email);
        User foundUser = userRepository.findUserByEmail(email);
        if(foundUser != null) {
            String newPassword = generatePassword();
            senderService.sendEmail(foundUser.getEmail(), newPassword, "NEW RESET PASSWORD");
            foundUser.setPassword(newPassword);
            userRepository.save(foundUser);
        }
    }
}
