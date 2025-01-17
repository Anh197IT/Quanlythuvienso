package com.example.demo.controller.Borrower;

import com.example.demo.entity.Borrower.Borrower;
import com.example.demo.service.Borrower.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    // Hiển thị tất cả người mượn
    @GetMapping("")
    public String getAllBorrowers(Model model) {
        List<Borrower> borrowers = borrowerService.getAllBorrowers();
        model.addAttribute("borrowers", borrowers);
        return "borrowers"; // Render trang borrowers.html
    }

    // API trả về tất cả người mượn dưới dạng JSON
    @GetMapping("/json")
    @ResponseBody
    public List<Borrower> getAllBorrowersJson() {
        return borrowerService.getAllBorrowers(); // Trả về người mượn dưới dạng JSON
    }

    // Hiển thị form thêm người mượn
    @GetMapping("/add")
    public String showAddBorrowerForm(Model model) {
        model.addAttribute("borrower", new Borrower());
        return "add-borrower"; // Render trang thêm người mượn
    }

    // Thêm người mượn mới
    @PostMapping("/add")
    public String addBorrower(@ModelAttribute Borrower borrower) {
        borrowerService.addBorrower(borrower); // Thêm người mượn vào cơ sở dữ liệu
        return "redirect:/borrowers"; // Redirect lại trang danh sách người mượn
    }

    // Lấy thông tin người mượn theo ID
    @GetMapping("/{id}")
    @ResponseBody
    public Borrower getBorrowerById(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id);
    }

    // Cập nhật thông tin người mượn
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBorrower(@PathVariable("id") Long id, @RequestBody Borrower borrower) {
        try {
            Borrower existingBorrower = borrowerService.getBorrowerById(id);

            // Cập nhật thông tin người mượn
            existingBorrower.setFullName(borrower.getFullName());
            existingBorrower.setEmail(borrower.getEmail());
            existingBorrower.setPhoneNumber(borrower.getPhoneNumber());
            existingBorrower.setBookTitle(borrower.getBookTitle());
            existingBorrower.setBorrowDate(borrower.getBorrowDate());
            existingBorrower.setReturnDate(borrower.getReturnDate());

            borrowerService.updateBorrower(existingBorrower); // Cập nhật thông tin vào cơ sở dữ liệu

            return ResponseEntity.ok("Cập nhật người mượn thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi khi cập nhật người mượn: " + e.getMessage());
        }
    }

    // Xóa người mượn theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable("id") Long id) {
        try {
            borrowerService.deleteBorrower(id);  // Xóa người mượn theo ID
            return ResponseEntity.noContent().build(); // Trả về HTTP 204 khi xóa thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build(); // Trả về lỗi nếu gặp sự cố trong quá trình xóa
        }
    }
}
