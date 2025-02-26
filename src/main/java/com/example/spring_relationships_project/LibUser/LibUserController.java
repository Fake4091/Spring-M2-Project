package com.example.spring_relationships_project.LibUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class LibUserController {
  private final LibUserService libUserService;

  @Autowired
  public LibUserController(LibUserService libUserService) { this.libUserService = libUserService; }

  @GetMapping
  public List<LibUser> getAllUsers() { return libUserService.getUsers(); }

  @GetMapping(path = "{userId}")
  public LibUser getUser(@PathVariable("userId") Long userId) { return libUserService.getUser(userId); }

  @PostMapping
  public void registerNewUser(@RequestBody LibUser user) {
    libUserService.addNewUser(user);
  }

  @PutMapping(path = "{userId}")
  public void updateUser(@PathVariable("userId") Long userId, @RequestParam(required = false) String username, @RequestParam(required = true) String oldPassword, @RequestParam(required = false) String newPassword, @RequestParam(required = false) String email) {
    libUserService.updateUser(userId, username, oldPassword, newPassword, email);
  }

  @DeleteMapping(path = "{userId}")
  public void deleteUser(@PathVariable("userId") Long userId, @RequestParam(required = false) String password) {
    libUserService.deleteUser(userId);
  }

}