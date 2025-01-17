package com.example.demo.controller.DanhMuc;

import com.example.demo.entity.DanhMuc.DanhMuc;
import com.example.demo.service.DanhMuc.DanhMucService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/danhmucs")
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    // Hiển thị danh sách danh mục
    @GetMapping("")
    public String getAllDanhMuc(Model model) {
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMuc();
        model.addAttribute("danhMucs", danhMucs);
        return "danhmuc"; // Render trang danhmuc.html
    }

    // API trả về danh mục dưới dạng JSON
    @GetMapping("/json")
    @ResponseBody
    public List<DanhMuc> getAllDanhMucJson() {
        return danhMucService.getAllDanhMuc();
    }

    // Hiển thị form thêm danh mục
    @GetMapping("/add")
    public String showAddDanhMucForm(Model model) {
        model.addAttribute("danhMuc", new DanhMuc());
        return "add-danhmuc"; // Render trang thêm danh mục
    }

    // Thêm danh mục mới
    @PostMapping("/add")
    public String addDanhMuc(@ModelAttribute DanhMuc danhMuc) {
        danhMucService.addDanhMuc(danhMuc);
        return "redirect:/danhmucs";  // Chuyển hướng về trang danh sách danh mục
    }

    // Xóa danh mục
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable("id") Integer id) {
        try {
            danhMucService.getDanhMucById(id);
            danhMucService.deleteDanhMucById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DanhMuc> getDanhMucById(@PathVariable Integer id) {
        return danhMucService.getDanhMucById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Cập nhật danh mục
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDanhMuc(@PathVariable("id") Integer id, @RequestBody DanhMuc danhMuc) {
        try {
            DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, danhMuc);
            return ResponseEntity.ok("Cập nhật danh mục thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi cập nhật danh mục");
        }
    }
}
