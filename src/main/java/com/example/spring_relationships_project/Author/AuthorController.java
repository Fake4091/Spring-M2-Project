package com.example.spring_relationships_project.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
  private final AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) { this.authorService = authorService; }

  @GetMapping
  public List<Author> getAllAuthors() { return authorService.getAllAuthors(); }

  @GetMapping(path="{authorId}")
  public Author getAuthor(@PathVariable Long authorId) { return authorService.getAuthor(authorId); }

  @PostMapping
  public Author createAuthor(@RequestBody Author author) { return authorService.createAuthor(author); }

  @DeleteMapping(path="{authorId}")
  public void deleteAuthor(@PathVariable Long authorId) { authorService.deleteAuthor(authorId); }
}
