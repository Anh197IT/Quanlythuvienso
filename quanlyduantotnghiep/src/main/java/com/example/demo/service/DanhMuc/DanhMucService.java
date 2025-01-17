package com.example.demo.service.DanhMuc;

import com.example.demo.entity.DanhMuc.DanhMuc;
import com.example.demo.repository.DanhMuc.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    // Lấy tất cả danh mục
    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    // Thêm danh mục mới
    public void addDanhMuc(DanhMuc danhMuc) {
        danhMucRepository.save(danhMuc);
    }

    // Lấy danh mục theo ID
    public Optional<DanhMuc> getDanhMucById(Integer id) {
        return danhMucRepository.findById(id);
    }

    // Xóa danh mục theo ID
    public void deleteDanhMucById(Integer id) {
        danhMucRepository.deleteById(id);
    }

    // Cập nhật danh mục
    public DanhMuc updateDanhMuc(Integer id, DanhMuc danhMuc) {
        danhMuc.setId(id);
        return danhMucRepository.save(danhMuc);
    }
}
