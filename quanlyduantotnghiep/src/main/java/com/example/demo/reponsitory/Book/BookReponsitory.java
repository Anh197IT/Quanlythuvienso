package com.example.demo.reponsitory.Book;

import com.example.demo.entity.Book.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReponsitory extends JpaRepository<Book, Long> {

	List<Book> findByTitleContainingIgnoreCase(String keyword);

}
