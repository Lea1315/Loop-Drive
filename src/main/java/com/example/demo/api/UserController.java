package com.example.demo.api;

import com.example.demo.model.AdminAddUser;
import com.example.demo.model.AdminUserUpdate;
import com.example.demo.model.User;
import com.example.demo.model.UserUpdate;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")  //admin dodaje korisnike
    public void addUser(@RequestBody AdminAddUser newUser) {
        userService.addUser(newUser);
    }

    @GetMapping("/users")   //admin moze vidjeti sve korisnike
    public List<User> getUsers() { return userService.getUsers(); }

    @DeleteMapping("/users/{id}")   //pomocna moja ruta za upravljanje
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")   //admin moze editovati korisnika (AKTIVAN I ROLU)
    public void updateUser(@PathVariable("id") Integer id, @RequestBody AdminUserUpdate userToUpdate) {
       userService.updateUser(userToUpdate, id);
    }

    //KORISNICI KOJI NISU ADMINI
    @PutMapping("/profile/{id}")   //korisnik edituje svoj profil (EMAIL, PASSWORD) ne moze rolu i aktivnost
    public void updateLoginUser(@RequestBody UserUpdate newUser, @PathVariable Integer id) {
        userService.updateLoginUser(newUser, id); //ovdje ce se id slati nakon logina u url kad bude autentifikacija
    }

    @PostMapping("/users/reset-password")
    public void userResetPassword(@RequestParam String email) {
        userService.resetPassword(email);
    }

}