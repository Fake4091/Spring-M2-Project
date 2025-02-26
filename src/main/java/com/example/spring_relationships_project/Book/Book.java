package com.example.spring_relationships_project.Book;

import com.example.spring_relationships_project.Author.Author;
import com.example.spring_relationships_project.LibUser.LibUser;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Book {
  @Id
  @SequenceGenerator(
      name = "book_sequence",
      sequenceName = "book_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "book_sequence"
  )
  private Long id;
  private String title;

  @ManyToOne
  @JoinColumn(name = "borrower_id", referencedColumnName = "id")
  private LibUser borrower;

  @ManyToMany(mappedBy = "booksWritten")
  private Set<Author> authors = new HashSet<>();

  public Book() {
  }

  public Book(Long id, String title, LibUser borrower, Set<Author> authors) {
    this.id = id;
    this.title = title;
    this.borrower = borrower;
    this.authors = authors;
  }

  public Book(String title, LibUser borrower, Set<Author> authors) {
    this.title = title;
    this.borrower = borrower;
    this.authors = authors;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Author> getAuthor() {
    return authors;
  }

  public void addAuthor(Author author) {
    this.authors.add(author);
  }

  public void removeAuthor(Author author) {
    this.authors.remove(author);
  }

  public LibUser getBorrower() {
    return borrower;
  }

  public void setBorrower(LibUser borrower) {
    this.borrower = borrower;
  }

  public Set<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<Author> authors) {
    this.authors = authors;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", borrower=" + borrower +
        ", authors=" + authors +
        '}';
  }
}
