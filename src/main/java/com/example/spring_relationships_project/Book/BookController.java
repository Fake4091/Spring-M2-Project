package com.example.spring_relationships_project.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) { this.bookService = bookService; }

  @GetMapping
  public List<Book> getAllBooks() { return bookService.getBooks(); }

  @GetMapping(path = "{bookId}")
  public Book getBook(@PathVariable("bookId") Long bookId) { return bookService.getBook(bookId); }

  @GetMapping(path = "borrowed-by/{borrowerId}")
  public List<Book> getBooks(@PathVariable("borrowerId") Long borrowerId) { return bookService.getBooksByBorrower(borrowerId); }

  @PostMapping
  public void addNewBook(@RequestBody Book book) {
    bookService.addNewBook(book);
  }

  @PutMapping(path = "borrow/{bookId}")
  public Book borrowBook(@PathVariable("bookId") Long bookId, @RequestBody Long borrowerId) {
    return bookService.borrowBook(bookId, borrowerId);
  }

  @PutMapping(path = "return/{bookId}")
  public Book returnBook(@PathVariable("bookId") Long bookId, @RequestBody Long borrowerId) {
    return bookService.returnBook(bookId, borrowerId);
  }

  @DeleteMapping(path = "{bookId}")
  public void deleteBook(@PathVariable("bookId") Long bookId) {
    bookService.deleteBook(bookId);
  }
}
