package com.example.spring_relationships_project.Author;

import com.example.spring_relationships_project.Book.Book;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Author {
  @Id
  @SequenceGenerator(
      name = "author_sequence",
      sequenceName = "author_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "author_sequence"
  )
  private Long id;
  private String name;
  private LocalDate birthDate;

  @ManyToMany
  @JoinTable(
      name = "books_written",
      joinColumns = @JoinColumn(name = "author_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id")
  )
  private Set<Book> booksWritten = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Set<Book> getBooksWritten() {
    return booksWritten;
  }

  public void setBooksWritten(Set<Book> booksWritten) {
    this.booksWritten = booksWritten;
  }

  @Override
  public String toString() {
    return "Author{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        ", booksWritten=" + booksWritten +
        '}';
  }
}
