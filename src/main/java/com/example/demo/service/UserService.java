package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserUpdate;
import com.example.demo.repo.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService senderService;
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
        if(userRepository.findUserByEmail(email) != null) throw new RuntimeException("Email already exists!");
        if(!Pattern.matches("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}", email)) throw new RuntimeException("Email is not valid!");
    }
    public void checkIfMailExists(String email) {
        if(userRepository.findUserByEmail(email) == null) throw new RuntimeException("User with this email doesn't exist!");
    }

    public void usernameValidation(String username) {
        if(!userRepository.findByUsername(username).isEmpty()) throw new RuntimeException("Username is taken!");
    }

    public void roleValidation(Integer role) {
        if(role != 1 && role != 2 && role != 3) throw new RuntimeException("This role doesn't exist!");
    }
    public void idValidation(Integer id) {
        if(userRepository.findById(id).isEmpty()) throw new RuntimeException("This user doesn't exist!");
    }
    public void addUser(User user) {//PROVJERITI AKO MAIL VEC POSTOJI, AKO JE ISPRAVAN MAIL, AKO ROLA POSTOJI
        emailValidation(user.getEmail());
        usernameValidation(user.getUsername());
        roleValidation(user.getRole());
        String pass = generatePassword();
        senderService.sendEmail(user.getEmail(), pass, "NEW PASSWORD");
        user.setPassword(pass);
        user.setActive(true);
        userRepository.save(user);
    }

    public List<User> getActiveUsers() {
        return userRepository.findAll().stream().filter(user -> user.isActive()).collect(Collectors.toList());
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user, Integer id) {
        idValidation(id);
        emailValidation(user.getEmail());
        roleValidation(user.getRole());
        usernameValidation(user.getUsername());
        user.setId(id);
        userRepository.save(user);
    }

    public void updateLoginUser(UserUpdate newUser, Integer id) {
        idValidation(id);
        emailValidation(newUser.getEmail());
        usernameValidation(newUser.getUsername());
         Optional<User> opt = userRepository.findById(id);
         if(opt.isPresent()) {
             opt.get().setEmail(newUser.getEmail());
             opt.get().setUsername(newUser.getUsername());
             opt.get().setPassword(newUser.getPassword());
             userRepository.save(opt.get());
         }
    }

    public void resetPassword(String email) {
        JSONObject jsonObj = new JSONObject(email);
        String stringEmail = jsonObj.getString("email");
        checkIfMailExists(stringEmail);
        User foundUser = userRepository.findUserByEmail(stringEmail);
        if(foundUser != null) {
            String newPassword = generatePassword();
            senderService.sendEmail(foundUser.getEmail(), newPassword, "NEW RESET PASSWORD");
            foundUser.setPassword(newPassword);
            userRepository.save(foundUser);
        }
    }
}
