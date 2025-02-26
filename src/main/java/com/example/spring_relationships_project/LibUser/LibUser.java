package com.example.spring_relationships_project.LibUser;

import com.example.spring_relationships_project.Book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class LibUser {
  @Id
  @SequenceGenerator(
      name = "lib_user_sequence",
      sequenceName = "lib_user_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "lib_user_sequence"
  )
  private Long id;
  private String username;
  private String password;
  private String email;

  @JsonIgnore
  @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
  private Set<Book> books_borrowed = new HashSet<>();

  public LibUser() {
  }

  public LibUser(Long id, String username, String password, String email, Set<Book> books_borrowed) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.books_borrowed = books_borrowed;
  }

  public LibUser(Long id, String username, String password, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public LibUser(String username, String password, String email, Set<Book> books_borrowed) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.books_borrowed = books_borrowed;
  }

  public LibUser(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Book> getBooks_borrowed() {
    return books_borrowed;
  }

  public void addBook(Book book) {
    this.books_borrowed.add(book);
  }

  public void removeBook(Book book) {
    this.books_borrowed.remove(book);
  }

  public void setBooks_borrowed(Set<Book> books_borrowed) {
    this.books_borrowed = books_borrowed;
  }

  @Override
  public String toString() {
    return "LibUser{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", books_borrowed=" + books_borrowed +
        '}';
  }

}
