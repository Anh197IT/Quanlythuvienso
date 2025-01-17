package com.example.demo.repository.Borrower;

import com.example.demo.entity.Borrower.Borrower;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

	List<Borrower> findByBookTitleContainingIgnoreCase(String keyword);
}
