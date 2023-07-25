package com.example.demo.DAO;

import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienDAO extends JpaRepository<NhanVien, Integer> {
}
