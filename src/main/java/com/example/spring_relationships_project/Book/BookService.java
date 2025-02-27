package com.example.spring_relationships_project.Book;

import com.example.spring_relationships_project.Author.Author;
import com.example.spring_relationships_project.Author.AuthorRepository;
import com.example.spring_relationships_project.LibUser.LibUser;
import com.example.spring_relationships_project.LibUser.LibUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final LibUserService libUserService;
  private final AuthorRepository authorRepository;

  @Autowired
  public BookService(BookRepository bookRepository, LibUserService libUserService, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.libUserService = libUserService;
    this.authorRepository = authorRepository;
  }

  public List<Book> getBooks() {
    return bookRepository.findAll();
  }

  public Book getBook(Long bookId) {
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isEmpty()) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    return bookOptional.get();
  }

  public void addNewBook(Book book) {
    if (!book.getAuthors().isEmpty()) {
      Set<Author> authors = new HashSet<>();
      for (Author author : book.getAuthors()) {
        Optional<Author> authorOptional = authorRepository.findById(author.getId());
        if (authorOptional.isEmpty()) {
          throw new IllegalStateException("The author with id \"" + author.getId() + "\" does not exist.");
        }
        authors.add(authorOptional.get());
        author.addBook(book);
        authorRepository.save(author);
      }
      book.setAuthors(authors);
    }
    bookRepository.save(book);
  }

  public Book borrowBook(Long bookId, Long borrowerId) {
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isEmpty()) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    Book book = bookOptional.get();
    if (book.getBorrower() != null) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" is already borrowed.");
    }
    LibUser borrower = libUserService.getUser(borrowerId);
    book.setBorrower(borrower);
    borrower.addBook(book);
    return bookRepository.save(book);
  }

  public Book returnBook(Long bookId, Long borrowerId) {
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isEmpty()) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    Book book = bookOptional.get();
    LibUser borrower = libUserService.getUser(borrowerId);
    if (book.getBorrower() != borrower) {
      throw new IllegalStateException("You have not borrowed the book with id \"" + bookId + "\".");
    }
    book.setBorrower(null);
    borrower.removeBook(book);
    return bookRepository.save(book);
  }

  public void deleteBook(Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    bookRepository.deleteById(bookId);
  }

  public List<Book> getBooksByBorrower(Long borrowerId) {
    return bookRepository.findBooksByBorrower_Id(borrowerId);
  }

  public void addAuthor(Long bookId, Long authorId) {
    Optional<Author> authorOptional = authorRepository.findById(authorId);
    if (authorOptional.isEmpty()) {
      throw new IllegalStateException("The author with id \"" + authorId + "\" does not exist.");
    }
    Author author = authorOptional.get();
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isEmpty()) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    Book book = bookOptional.get();
    
    book.addAuthor(author);
    author.addBook(book);
    bookRepository.save(book);
    authorRepository.save(author);
  }

  public void removeAuthor(Long bookId, Long authorId) {
    Optional<Author> authorOptional = authorRepository.findById(authorId);
    if (authorOptional.isEmpty()) {
      throw new IllegalStateException("The author with id \"" + authorId + "\" does not exist.");
    }
    Author author = authorOptional.get();
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isEmpty()) {
      throw new IllegalStateException("The book with id \"" + bookId + "\" does not exist.");
    }
    Book book = bookOptional.get();
    if (!book.getAuthors().contains(author)) {
      throw new IllegalStateException("The author with id \"" + authorId + "\" didn't write book with id \"" + bookId + "\".");
    }
    book.removeAuthor(author);
    author.removeBook(book);
    bookRepository.save(book);
    authorRepository.save(author);
  }
}
