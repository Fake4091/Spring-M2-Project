package com.example.spring_relationships_project.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) { this.authorRepository = authorRepository; }

  public List<Author> getAllAuthors() {
     return authorRepository.findAll();
  }

  public Author getAuthor(Long authorId) {
    Optional<Author> authorOptional = authorRepository.findById(authorId);
    if (authorOptional.isEmpty()) {
      throw new IllegalStateException("The author with id " + authorId + " does not exist.");
    }
    return authorOptional.get();
  }

  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  public void deleteAuthor(Long authorId) {
    Optional<Author> authorOptional = authorRepository.findById(authorId);
    if (authorOptional.isEmpty()) {
      throw new IllegalStateException("The author with id " + authorId + " does not exist.");
    }
    authorRepository.deleteById(authorId);
  }
}
