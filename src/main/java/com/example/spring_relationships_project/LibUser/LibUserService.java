package com.example.spring_relationships_project.LibUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibUserService {
  private final LibUserRepository libUserRepository;

  @Autowired
  public LibUserService(LibUserRepository libUserRepository) {this.libUserRepository = libUserRepository;}

  public List<LibUser> getUsers() {
    return libUserRepository.findAll();
  }

  public LibUser getUser(Long userId) {
    Optional<LibUser> userOptional = libUserRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new IllegalStateException("User with id " + userId + " does not exist.");
    }
    return userOptional.get();
  }

  public void addNewUser(LibUser user) {
    Optional<LibUser> userOptional = libUserRepository.findUserByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      throw new IllegalStateException("User with email " + user.getEmail() + " already exists.");
    }
    libUserRepository.save(user);
  }

  public void updateUser(Long userId, String username, String oldPassword, String newPassword, String email) {
    Optional<LibUser> userOptional = libUserRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new IllegalStateException("User with id " + userId + " does not exist.");
    }
    LibUser user = userOptional.get();
    if (!user.getPassword().equals(oldPassword)) {
      throw new IllegalStateException("Incorrect username or password.");
    }
    if (username != null && !username.isEmpty() && !Objects.equals(user.getUsername(), username)) {
      user.setUsername(username);
    }
    if (newPassword != null && !newPassword.isEmpty() && !Objects.equals(user.getPassword(), newPassword)) {
      user.setPassword(newPassword);
    }
    if (email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)) {
      Optional<LibUser> userOptional2 = libUserRepository.findUserByEmail(email);
      if (userOptional2.isPresent()) {
        throw new IllegalStateException("The email \"" + email + "\" is already in use.");
      }
      user.setEmail(email);
    }
    libUserRepository.save(user);
  }

  public void deleteUser(Long userId) {
    boolean exists = libUserRepository.existsById(userId);
    if (!exists) {
      throw new IllegalStateException("User with id " + userId + " does not exist.");
    }
    libUserRepository.deleteById(userId);
  }
}
