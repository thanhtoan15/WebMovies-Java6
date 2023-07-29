package com.example.demo.DAO;

import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NhanVienDAO extends JpaRepository<NhanVien, String> {
    @Query("select o from  NhanVien o where  o.tendangnhap = :tendangnhap and  o.matkhau = :matkhau")
    Optional<NhanVien> findByNhanVien(String tendangnhap, String matkhau);
    @Query("select o from  NhanVien o where  o.tendangnhap = :tendangnhap")
    Optional<NhanVien> findByNhanVienByTenDangNhap(String tendangnhap);
    NhanVien save(NhanVien nhanvien);
    Optional<NhanVien> findByEmail(String email);
}
