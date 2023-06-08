package com.example.demo.api;

import com.example.demo.model.*;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public  User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }

    @PostMapping("/users")  //admin dodaje korisnike
    public void addUser(@RequestBody AdminAddUser newUser) {
        userService.addUser(newUser);
    }

    @GetMapping("/users")   //admin moze vidjeti sve korisnike
    public List<UserData> getUsers() { return userService.getUsers(); }

    @DeleteMapping("/users")   //pomocna moja ruta za upravljanje
    public void deleteUserById(@RequestParam Integer userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/users")   //admin moze editovati korisnika (AKTIVAN I ROLU)
    public void updateUser(@RequestParam Integer userId, @RequestBody AdminUserUpdate userToUpdate) {
       userService.updateUser(userToUpdate, userId);
    }

    //KORISNICI KOJI NISU ADMINI
    @PutMapping("/profile")   //korisnik edituje svoj profil (EMAIL, PASSWORD) ne moze rolu i aktivnost
    public void updateLoginUser(@RequestBody UserUpdate newUser) {
        userService.updateLoginUser(newUser, getLoggedUser().getId()); //ovdje ce se id slati nakon logina u url kad bude autentifikacija
    }

    @PostMapping("/users/reset-password")
    public void userResetPassword(@RequestParam String email) {
        userService.resetPassword(email);
    }

    @GetMapping("/current-user")
    public AdminAddUser getCurrentUser() { return userService.getCurrentUser();}

}
