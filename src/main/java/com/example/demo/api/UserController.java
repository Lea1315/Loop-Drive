package com.example.demo.api;

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

    @PostMapping("/add")  //admin dodaje korisnike
    public void addUser(@RequestBody User users) {
        userService.addUser(users);
    }

    @GetMapping("/users")   //admin moze vidjeti sve aktivne korisnike
    public List<User> getActiveUsers() { return userService.getActiveUsers(); }

    @DeleteMapping("/users/{id}")   //pomocna moja ruta za upravljanje
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/edit/{id}")   //admin moze editovati korisnika (AKTIVAN I ROLU)
    public void updateUser(@PathVariable("id") Integer id, @RequestBody User userToUpdate) {
       userService.updateUser(userToUpdate, id);
    }

    //KORISNICI KOJI NISU ADMINI
    @GetMapping("/login")
    public void login(@RequestBody User user){
        System.out.println(user.getUsername());
    }
    @PutMapping("/login/{id}/edit")   //korisnik edituje svoj profil (USERNAME, EMAIL, PASSWORD) ne moze rolu i aktivnost
    public void updateLoginUser(@RequestBody UserUpdate newUser, @PathVariable Integer id) {
        userService.updateLoginUser(newUser, id);
    }

    @PostMapping("/login/resetPassword")
    public void userResetPassword(@RequestBody String email) {
        userService.resetPassword(email);
    }

}
