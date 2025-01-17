package com.example.demo.service.Book;

import com.example.demo.entity.Book.Book;
import com.example.demo.reponsitory.Book.BookReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookReponsitory bookRepository;

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Thêm sách mới
    public void addBook(Book book) {
        // Đảm bảo rằng published_date có kiểu dữ liệu LocalDate
        book.setCreatedAt(LocalDate.now());  // Ghi lại thời gian tạo sách
        bookRepository.save(book);
    }

    // Lấy sách theo ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách với ID: " + id));
    }
 // Xóa sách
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);  // Xóa sách theo ID từ repository
    }


    // Cập nhật sách
    public void updateBook(Book book) {
        Book existingBook = getBookById(book.getId());
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublishedDate(book.getPublishedDate());
        existingBook.setCreatedAt(book.getCreatedAt());  // Cập nhật thời gian tạo nếu cần
        bookRepository.save(existingBook);
    }
    public List<Book> searchBooks(String keyword) {
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
            // Ignore exception if keyword is not a valid number
        }

        if (id != null) {
            // Tìm kiếm theo ID
            Optional<Book> bookById = bookRepository.findById(id);
            if (bookById.isPresent()) {
                return List.of(bookById.get());
            }
        }
        // Tìm kiếm theo tiêu đề
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
