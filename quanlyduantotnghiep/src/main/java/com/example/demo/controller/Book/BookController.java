package com.example.demo.controller.Book;

import com.example.demo.entity.Book.Book;
import com.example.demo.service.Book.BookService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Hiển thị tất cả sách
    @GetMapping("")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books"; // Render trang books.html
    }

    // API trả về tất cả sách dưới dạng JSON
    @GetMapping("/json")
    @ResponseBody
    public List<Book> getAllBooksJson() {
        return bookService.getAllBooks(); // Trả về sách dưới dạng JSON
    }

    // Hiển thị form thêm sách
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // Render trang thêm sách
    }

    // Thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, HttpServletRequest request) {
        // Get the publishedDate directly from the book object
        LocalDate publishedDate = book.getPublishedDate();

        bookService.addBook(book);
        return "redirect:/books";
    }
 // Xóa sách
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);  // Gọi service để xóa sách
            return ResponseEntity.noContent().build(); // Trả về HTTP 204 khi xóa thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .build(); // Trả về HTTP 500 nếu có lỗi khi xóa
        }
    }


    // Lấy sách theo ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về HTTP 404 nếu không tìm thấy sách
        }
        return ResponseEntity.ok(book); // Trả về sách với HTTP 200 nếu tìm thấy
    }

    // Cập nhật thông tin sách
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        try {
            Book existingBook = bookService.getBookById(id);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Không tìm thấy sách với ID: " + id); // Trả về HTTP 404 nếu sách không tồn tại
            }

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());

            existingBook.setPublishedDate(book.getPublishedDate());
            existingBook.setIsbn(book.getIsbn());

            // Giữ nguyên ngày tạo
            existingBook.setCreatedAt(existingBook.getCreatedAt());

            bookService.updateBook(existingBook);
            return ResponseEntity.ok("Cập nhật sách thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Có lỗi khi cập nhật sách: " + e.getMessage());
        }
    }
}
