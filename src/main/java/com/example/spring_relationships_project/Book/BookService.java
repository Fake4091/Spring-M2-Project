package com.example.spring_relationships_project.Book;

import com.example.spring_relationships_project.LibUser.LibUser;
import com.example.spring_relationships_project.LibUser.LibUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final LibUserService libUserService;

  @Autowired
  public BookService(BookRepository bookRepository, LibUserService libUserService) {
    this.bookRepository = bookRepository;
    this.libUserService = libUserService;
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
}
