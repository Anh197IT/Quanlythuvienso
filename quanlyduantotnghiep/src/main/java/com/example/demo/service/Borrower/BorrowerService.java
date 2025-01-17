package com.example.demo.service.Borrower;

import com.example.demo.entity.Borrower.Borrower;
import com.example.demo.repository.Borrower.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    // Lấy tất cả người mượn
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    // Thêm người mượn mới
    public Borrower addBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    // Lấy người mượn theo ID
    public Borrower getBorrowerById(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người mượn sách với ID: " + id));
    }

    // Cập nhật thông tin người mượn
    public Borrower updateBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    // Xóa người mượn theo ID
    public void deleteBorrower(Long id) {
        Borrower borrower = getBorrowerById(id);
        borrowerRepository.delete(borrower); // Xóa người mượn
    }

 
    public List<Borrower> searchBorrowers(String keyword) {
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
            // Ignore exception if keyword is not a valid number
        }

        if (id != null) {
            // Tìm kiếm theo ID
            Optional<Borrower> borrowerById = borrowerRepository.findById(id);
            if (borrowerById.isPresent()) {
                return List.of(borrowerById.get());
            }
        }
        // Tìm kiếm theo tên sách
        return borrowerRepository.findByBookTitleContainingIgnoreCase(keyword);
    }
}
