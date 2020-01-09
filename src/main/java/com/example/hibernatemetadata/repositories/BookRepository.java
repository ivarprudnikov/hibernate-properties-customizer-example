package com.example.hibernatemetadata.repositories;

import com.example.hibernatemetadata.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
